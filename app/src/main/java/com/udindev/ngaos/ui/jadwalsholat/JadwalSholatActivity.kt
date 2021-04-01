package com.udindev.ngaos.ui.jadwalsholat

import android.os.Bundle
import android.util.Log
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import com.udindev.ngaos.databinding.ActivityJadwalSholatBinding
import com.udindev.ngaos.model.Sholat
import com.udindev.ngaos.utils.MyReceiver


class JadwalSholatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityJadwalSholatBinding
    private lateinit var alarmReceiver: MyReceiver

    companion object{
        const val EXTRA_SHOLAT = "extra_sholat"
        private const val TAG = "JadwalSholatActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJadwalSholatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        alarmReceiver = MyReceiver()

        binding.radioGroup1.setOnCheckedChangeListener { radioGroup, i ->
            if (i != -1) binding.radioGroup2.check(-1)
        }

        binding.radioGroup2.setOnCheckedChangeListener { radioGroup, i ->
            if (i != -1) binding.radioGroup1.check(-1)
        }

        val i = intent.extras
        var sholat = i?.getParcelable<Sholat>(EXTRA_SHOLAT)

        Log.d(TAG, "onCreate: $sholat")

        binding.txtWaktuSolat.text = sholat?.dzuhur
        binding.txtLocation.text = sholat?.lokasi

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}