package com.udindev.ngaos.ui.dashboard


import android.Manifest
import android.app.AlertDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import com.agrawalsuneet.dotsloader.loaders.ZeeLoader
import com.nanangarifudin.moviecatalogue.ui.home.SectionPagerAdapter
import com.schibstedspain.leku.LATITUDE
import com.schibstedspain.leku.LOCATION_ADDRESS
import com.schibstedspain.leku.LONGITUDE
import com.schibstedspain.leku.LocationPickerActivity
import com.udindev.ngaos.NotificationActivity
import com.udindev.ngaos.WaktuSholatActivity
import com.udindev.ngaos.api.ApiInterface
import com.udindev.ngaos.api.ApiIslamicPrayerTimes
import com.udindev.ngaos.databinding.ActivityDashboardBinding
import com.udindev.ngaos.model.ResponsePrayerTime
import com.udindev.ngaos.model.Sholat
import com.udindev.ngaos.model.Times
import com.udindev.ngaos.ui.jadwalsholat.JadwalSholatActivity
import com.udindev.ngaos.utils.GlobalConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class DashboardActivity : AppCompatActivity() {

    private lateinit var binding :ActivityDashboardBinding;

    private val TAG = "Sholat Activity"
    private var locationManager: LocationManager? = null
    private lateinit var latitude : String
    private lateinit var longitude: String
    private var mlatitude: String? = null
    private var mlongitude: String? = null
    private var geocoder: Geocoder? = null
    private var addresses: List<Address>? = null
    private val MAP_BUTTON_REQUEST_CODE = 1
    private val toolbar: Toolbar? = null
    private val btn_sholat: Button? = null
    private val timePickerDialog: TimePickerDialog? = null
    private var alamatku : String = ""
    private var sholat = Sholat()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sectionsPagerAdapter = SectionPagerAdapter(this, supportFragmentManager)
        binding.viewPager.adapter = sectionsPagerAdapter
        binding.tabs.setupWithViewPager(binding.viewPager)
        supportActionBar?.elevation = 0f

        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            GlobalConfig.REQUEST_CODE_LOCATION_PERMISSION
        )

        locationManager = this.getSystemService(LOCATION_SERVICE) as LocationManager?

        openMaps()
        if (!locationManager!!.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            OnGPS()
        } else {
            try {
                getLocation()
            } catch (e: IOException) {
                Log.e(TAG, "Error Boss!!! $e")
            }
        }
        getTimes()
    }

    fun time(times: Times) {
        binding.progressBar.visibility = View.GONE
        try {
            val lat = latitude.toDouble()
            val lang = longitude.toDouble()
            geocoder = Geocoder(this, Locale.getDefault())
            addresses = geocoder!!.getFromLocation(lat, lang, 1)


            var returnedAddress = (addresses as MutableList<Address>?)?.get(0)?.locality

            alamatku = returnedAddress.toString()


            binding.cardView.setOnClickListener{
                var shol = Sholat(returnedAddress, times.fajr, times.dhuhr, times.asr, times.maghrib, times.isha)
                val i = Intent(this, JadwalSholatActivity::class.java)
                i.putExtra(JadwalSholatActivity.EXTRA_SHOLAT, shol)
                startActivity(i)
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
        val calendar = Calendar.getInstance()
        val h = calendar.get(Calendar.HOUR_OF_DAY)
        val m = calendar.get(Calendar.MINUTE)
        val result = h.toString() + ":" + m.toString()

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


    fun getTimes() {
        val apiInterface = ApiIslamicPrayerTimes.getClient().create(
            ApiInterface::class.java
        )
        //get current date
        val calendar = Calendar.getInstance()
        val formatter = SimpleDateFormat("yyyy-MM-dd")
        val dateNow = formatter.format(calendar.time)
        Log.d("Coba", "latitude: $mlatitude longtude : $mlongitude date Now : $dateNow")
        //get data
        val responsePrayerTimeCall = apiInterface.getTimeFromFatimah(mlatitude, mlongitude, dateNow)
        responsePrayerTimeCall.enqueue(object : Callback<ResponsePrayerTime> {
            override fun onResponse(
                call: Call<ResponsePrayerTime>,
                response: Response<ResponsePrayerTime>
            ) {
                val times = response.body()?.results?.datetime?.get(0)?.times
                if (times == null) {
                    binding.progressBar.visibility = View.VISIBLE
                }
                binding.progressBar.visibility = View.GONE
                try {
                    times?.let {
                        time(it)

                    }
                } catch (e: java.lang.Exception) {
                    e.printStackTrace()
                }
            }

            override fun onFailure(call: Call<ResponsePrayerTime>, t: Throwable) {
                binding.progressBar.visibility = View.GONE
            }
        })
    }

    @Throws(IOException::class)
    private fun getLocation() {

        //Check Permissions again
        latitude = "0.0"
        longitude = "0.0"
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                GlobalConfig.REQUEST_CODE_LOCATION_PERMISSION
            )
        } else {
            val LocationGps = locationManager!!.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            val LocationNetwork =
                locationManager!!.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
            val LocationPassive =
                locationManager!!.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER)
            if (LocationGps != null) {
                val lat = LocationGps.latitude
                val longi = LocationGps.longitude
                latitude = lat.toString()
                longitude = longi.toString()
            } else if (LocationNetwork != null) {
                val lat = LocationNetwork.latitude
                val longi = LocationNetwork.longitude
                latitude = lat.toString()
                longitude = longi.toString()
            } else if (LocationPassive != null) {
                val lat = LocationPassive.latitude
                val longi = LocationPassive.longitude
                latitude = lat.toString()
                longitude = longi.toString()
            } else {
                Toast.makeText(
                    this.getApplicationContext(),
                    "Tidak Mendapatkan Lokasi Anda",
                    Toast.LENGTH_SHORT
                ).show()
            }

            if (latitude.equals("0.0") && longitude.equals("0.0")) {
                mlatitude = latitude
                mlongitude = longitude
            } else {
                Toast.makeText(this, "", Toast.LENGTH_SHORT).show()
            }

//            mlatitude = latitude
//            mlongitude = longitude

            //getData
            try {
                val lat = latitude.toDouble()
                val lang = longitude.toDouble()
                geocoder = Geocoder(this, Locale.getDefault())
                addresses = geocoder!!.getFromLocation(lat, lang, 1)


                var returnedAddress = (addresses as MutableList<Address>?)?.get(0)?.locality

                alamatku = returnedAddress.toString()

                if (returnedAddress != null) {
                    binding.txtLocation.setText(returnedAddress)
                    sholat.lokasi = returnedAddress
                } else {
                    binding.txtLocation.setText("Kecamatan tidak ada")
                    sholat.lokasi = "Kecamatan tidak ada"
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun OnGPS() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Enable GPS").setCancelable(false).setPositiveButton(
            "YES"
        ) { dialog, which -> startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)) }
            .setNegativeButton(
                "NO"
            ) { dialog, which -> dialog.cancel() }
        val alertDialog = builder.create()
        alertDialog.show()
    }

    fun openMaps() {
        binding.txtLocation.setOnClickListener(View.OnClickListener {
            val intent: Intent = LocationPickerActivity.Builder()
                .withUnnamedRoadHidden()
                .withLegacyLayout()
                .build(this)
            intent.putExtra("test", "this is test")
            startActivityForResult(intent, MAP_BUTTON_REQUEST_CODE)
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && data != null) {
            if (requestCode == 1) {
                val latitude = data.getDoubleExtra(LATITUDE, 0.0)
                val longitude = data.getDoubleExtra(LONGITUDE, 0.0)
                mlatitude = latitude.toString()
                mlongitude = longitude.toString()
                val alamat = data.getStringExtra(LOCATION_ADDRESS)

                val lat = latitude.toDouble()
                val lang = longitude.toDouble()
                geocoder = Geocoder(this, Locale.getDefault())
                addresses = geocoder!!.getFromLocation(lat, lang, 1)


                var returnedAddress = (addresses as MutableList<Address>?)?.get(0)?.locality

                alamatku = returnedAddress.toString()

                if (returnedAddress != null) {
                    binding.txtLocation.setText(returnedAddress)
                    sholat.lokasi = returnedAddress
                } else {
                    binding.txtLocation.setText("Kecamatan tidak ada")
                    sholat.lokasi = "Kecamatan tidak ada"
                }
//                binding.txtLocation.setText(alamat)
                getTimes()
            }
        }
        if (resultCode == RESULT_CANCELED) {
            Log.d("RESULT****", "CANCELLED")
        }
    }

    override fun onStart() {
        super.onStart()
        getTimes()
    }

    override fun onResume() {
        super.onResume()
        getTimes()
    }

}