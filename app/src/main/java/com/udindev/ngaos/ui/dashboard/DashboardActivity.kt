@file:Suppress("DEPRECATION")

package com.udindev.ngaos.ui.dashboard


import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.location.Geocoder
import android.location.Location
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.nanang.berberita.data.api.RetrofitBuilder
import com.nanang.lokasi.MySimpleLocation
import com.nanang.retrocoro.ui.base.SholatViewModelFactory
import com.nanang.retrocoro.ui.main.viewmodel.SholatViewModel
import com.nanangarifudin.moviecatalogue.ui.home.SectionPagerAdapter
import com.schibstedspain.leku.LocationPickerActivity
import com.udindev.ngaos.api.ApiHelper
import com.udindev.ngaos.databinding.ActivityDashboardBinding
import com.udindev.ngaos.model.Datetime
import com.udindev.ngaos.model.Sholat
import com.udindev.ngaos.model.Times
import com.udindev.ngaos.ui.jadwalsholat.JadwalSholatActivity
import com.udindev.ngaos.utils.Status
import java.text.SimpleDateFormat
import java.util.*

@Suppress("DEPRECATION")
class DashboardActivity : AppCompatActivity(),MySimpleLocation.MySimpleLocationCallBack{

    private lateinit var binding :ActivityDashboardBinding
    private lateinit var mySimpleLocation: MySimpleLocation
    private lateinit var sholatViewModel: SholatViewModel


    companion object{
        private const val TAG = "Sholat Activity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sectionsPagerAdapter = SectionPagerAdapter(this, supportFragmentManager)
        binding.viewPager.adapter = sectionsPagerAdapter
        binding.tabs.setupWithViewPager(binding.viewPager)
        supportActionBar?.elevation = 0f

        checkMyLocationPermission()
        setupViewModel()

    }

    private fun setupObservers(latitude: String, longitude: String, date: String, location: String) {
        sholatViewModel.getTimeFromFatimah(latitude, longitude, date).observe(this, {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        resource.data?.let { times ->
                            Log.d(TAG, "setupObservers: ${times.results.datetime}")
                            val datetime: MutableList<Datetime>? = times.results.datetime
                            if (datetime != null) {
                                for (datess in datetime) {
                                    val waktu: Times = datess.times
                                    setWaktuSholat(waktu, location)
                                }
                            }

//                            retrieveList(users)
                        }
                    }
                    Status.ERROR -> {
//                        progressbar.visibility = View.INVISIBLE
//                        rv_news.visibility = View.VISIBLE
                        Log.d(TAG, "setupObservers: ${it.message}")
                        Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                    }

                    Status.LOADING -> {
//                        progressbar.visibility = View.VISIBLE
//                        rv_news.visibility = View.INVISIBLE
//                        progressbar.playAnimation()
                    }
                }
            }
        })
    }

    private fun setupViewModel() {
        sholatViewModel = ViewModelProviders.of(
                this, SholatViewModelFactory(ApiHelper(RetrofitBuilder.apiService))
        ).get(SholatViewModel::class.java)
    }

    private fun checkMyLocationPermission() {
        Dexter.withActivity(this)
            .withPermissions(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
            )
            .withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                    when {
                        report!!.areAllPermissionsGranted() -> {
                            /** Do Something when all permission was granted
                             *
                             * */
                            Log.d("PERMISSIONCHECK", "ALL PERMISSION GRANTED")

//                            binding.tvMyLocation.visibility = View.GONE
//                            binding.tvSearchLocation.visibility = View.VISIBLE

                            mySimpleLocation = MySimpleLocation(this@DashboardActivity, this@DashboardActivity)
                            mySimpleLocation.checkLocationSetting(this@DashboardActivity)
                        }
                        report.isAnyPermissionPermanentlyDenied -> {
                            /** Do Something when any permission permanently blocked
                             *
                             * */
                            Log.d("PERMISSIONCHECK", "ANY PERMISSION PERMANENTLY DENIED")
                        }
                        else -> {
                            /** Do something when any permission denied
                             *
                             * */
                            Log.d("PERMISSIONCHECK", "ANY PERMISSION DENIED")
                        }
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                        p0: MutableList<com.karumi.dexter.listener.PermissionRequest>?,
                        token: PermissionToken?
                ) {
                    Log.d("PERMISSIONCHECK", "onPermissionRationaleShouldBeShown")
                    token?.continuePermissionRequest()
                }

            })
            .check()
    }

    @SuppressLint("SimpleDateFormat")
    override fun getLocation(location: Location?) {
        val latitude = location?.latitude
        val longitude = location?.longitude
        Log.d("MAINACTIVITY", "$latitude $longitude")

        AsyncTask.execute{
            this.runOnUiThread {
                val geoCoder = Geocoder(this, Locale.getDefault())
                try {
                    val addresses = geoCoder.getFromLocation(latitude!!, longitude!!, 1)


//                    if (addresses != null && addresses.size != 0) {
//                        val fullAddress = addresses[0].getAddressLine(0)
//                        fullAddress
//                    } else {
//                        "Alamat Tidak Diketahui"
//                    }
//                    binding.tvSearchLocation.visibility = View.GONE

                    val lokasi = addresses[0].locality ?: "Alamat Tidak Diketahui"
                    binding.txtLocation.visibility = View.VISIBLE
                    binding.txtLocation.text = lokasi

                    val calendar = Calendar.getInstance()
                    val formatter = SimpleDateFormat("yyyy-MM-dd")
                    val dateNow = formatter.format(calendar.time)
                    setupObservers(latitude.toString(), longitude.toString(), dateNow, lokasi)

                    //If you want to stop get your location on first result
                    mySimpleLocation.stopGetLocation()
                } catch (e: Exception) {
                    Log.e("MAINACTIVITY", e.message.toString())
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    fun setWaktuSholat(times: Times, location: String){

        binding.cardView.setOnClickListener{
            val shol = Sholat(location, times.fajr, times.dhuhr, times.asr, times.maghrib, times.isha)
            val i = Intent(this, JadwalSholatActivity::class.java)
            i.putExtra(JadwalSholatActivity.EXTRA_SHOLAT, shol)
            startActivity(i)
        }

        val calendar = Calendar.getInstance()
        val h = calendar.get(Calendar.HOUR_OF_DAY)
        val m = calendar.get(Calendar.MINUTE)
        val result = "$h:$m"

        //TODO : logicnya cek dengan waktu sekarang

        // set waktu dzuhur
        if (result > times.fajr && result < times.dhuhr ) {
            binding.txtSholat.text = "Dzuhur"
            binding.txtWaktuSolat.text = times.dhuhr
        }

        if (result > times.dhuhr && result < times.asr ) {
            binding.txtSholat.text = "Ashar"
            binding.txtWaktuSolat.text = times.asr
        }

        if (result > times.asr && result < times.maghrib ) {
            binding.txtSholat.text = "Magrib"
            binding.txtWaktuSolat.text = times.maghrib
        }

        if (result > times.maghrib && result < times.isha ) {
            binding.txtSholat.text = "Isya"
            binding.txtWaktuSolat.text = times.isha
        }

        if (result > times.isha && result < "23:59" ) {
            binding.txtSholat.text = "Subuh"
            binding.txtWaktuSolat.text = times.fajr
        }

        if (result < times.fajr && result > "00:01") {
            binding.txtSholat.text = "Subuh"
            binding.txtWaktuSolat.text = times.fajr
        }
    }


}