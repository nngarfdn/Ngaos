package com.udindev.ngaos.ui.jadwalsholat

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.udindev.ngaos.R
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

        val sharedPrefs = getSharedPreferences("com.udindev.ngaos", MODE_PRIVATE)
        binding.swtSubuh.isChecked = sharedPrefs.getBoolean("subuh", false)
        binding.swtDzuhur.isChecked = sharedPrefs.getBoolean("dzuhur", false)
        binding.swtAshar.isChecked = sharedPrefs.getBoolean("ashar", false)
        binding.swtMagrib.isChecked = sharedPrefs.getBoolean("magrib", false)
        binding.swtIsya.isChecked = sharedPrefs.getBoolean("isya", false)

        val i = intent.extras
        val sholat = i?.getParcelable<Sholat>(EXTRA_SHOLAT)
        Log.d(TAG, "onCreate: $sholat")

        setupJadwalShola(sholat)

        binding.txtWaktuSolat.text = sholat?.dzuhur
        binding.txtLocation.text = sholat?.lokasi

        setupNotification()

        binding.swtSubuh.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {

                val editor = getSharedPreferences("com.udindev.ngaos", MODE_PRIVATE).edit()
                editor.putBoolean("subuh", true)
                editor.apply()

                val repeatTime = sholat?.subuh
                val repeatMessage = "Yuk sholat subuh"
                if (repeatTime != null) {
                    alarmReceiver.setRepeatingAlarm(
                            this, MyReceiver.TYPE_REPEATING,
                            repeatTime, repeatMessage
                    )
                }
            } else {
                val editor = getSharedPreferences("com.udindev.ngaos", MODE_PRIVATE).edit()
                editor.putBoolean("subuh", false)
                editor.apply()
                alarmReceiver.cancelAlarm(this, MyReceiver.TYPE_REPEATING)
            }
        }
        binding.swtDzuhur.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {

                val editor = getSharedPreferences("com.udindev.ngaos", MODE_PRIVATE).edit()
                editor.putBoolean("dzuhur", true)
                editor.apply()

                val repeatTime = sholat?.dzuhur
                val repeatMessage = "Yuk sholat dzuhur"
                if (repeatTime != null) {
                    alarmReceiver.setRepeatingAlarm(
                            this, MyReceiver.TYPE_REPEATING,
                            repeatTime, repeatMessage
                    )
                }
            } else {
                val editor = getSharedPreferences("com.udindev.ngaos", MODE_PRIVATE).edit()
                editor.putBoolean("dzuhur", false)
                editor.apply()
                alarmReceiver.cancelAlarm(this, MyReceiver.TYPE_REPEATING)
            }
        }
        binding.swtAshar.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {

                val editor = getSharedPreferences("com.udindev.ngaos", MODE_PRIVATE).edit()
                editor.putBoolean("ashar", true)
                editor.apply()

                val repeatTime = sholat?.ashar
                val repeatMessage = "Yuk sholat ashar"
                if (repeatTime != null) {
                    alarmReceiver.setRepeatingAlarm(
                            this, MyReceiver.TYPE_REPEATING,
                            repeatTime, repeatMessage
                    )
                }
            } else {
                val editor = getSharedPreferences("com.udindev.ngaos", MODE_PRIVATE).edit()
                editor.putBoolean("ashar", false)
                editor.apply()
                alarmReceiver.cancelAlarm(this, MyReceiver.TYPE_REPEATING)
            }
        }
        binding.swtMagrib.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {

                val editor = getSharedPreferences("com.udindev.ngaos", MODE_PRIVATE).edit()
                editor.putBoolean("magrib", true)
                editor.apply()

                val repeatTime = sholat?.magrib
                val repeatMessage = "Yuk sholat magrib"
                if (repeatTime != null) {
                    alarmReceiver.setRepeatingAlarm(
                            this, MyReceiver.TYPE_REPEATING,
                            repeatTime, repeatMessage
                    )
                }
            } else {
                val editor = getSharedPreferences("com.udindev.ngaos", MODE_PRIVATE).edit()
                editor.putBoolean("magrib", false)
                editor.apply()
                alarmReceiver.cancelAlarm(this, MyReceiver.TYPE_REPEATING)
            }
        }
        binding.swtIsya.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {

                val editor = getSharedPreferences("com.udindev.ngaos", MODE_PRIVATE).edit()
                editor.putBoolean("isya", true)
                editor.apply()

                val repeatTime = sholat?.isya
                val repeatMessage = "Yuk sholat isya"
                if (repeatTime != null) {
                    alarmReceiver.setRepeatingAlarm(
                            this, MyReceiver.TYPE_REPEATING,
                            repeatTime, repeatMessage
                    )
                }
            } else {
                val editor = getSharedPreferences("com.udindev.ngaos", MODE_PRIVATE).edit()
                editor.putBoolean("isya", false)
                editor.apply()
                alarmReceiver.cancelAlarm(this, MyReceiver.TYPE_REPEATING)
            }
        }

    }

    private fun setupJadwalShola(sholat: Sholat?) {
        binding.radioGroup1.setOnCheckedChangeListener { _, id ->
            if (id != -1) binding.radioGroup2.check(-1)
            if (id == R.id.radioSubuh) binding.txtWaktuSolat.text = sholat?.subuh
            if (id == R.id.radioDzuhur) binding.txtWaktuSolat.text = sholat?.dzuhur
            if (id == R.id.radioAshar) binding.txtWaktuSolat.text = sholat?.ashar
        }

        binding.radioGroup2.setOnCheckedChangeListener { _, id ->
            if (id != -1) binding.radioGroup1.check(-1)
            if (id == R.id.radioMagrib) binding.txtWaktuSolat.text = sholat?.magrib
            if (id == R.id.radioIsya) binding.txtWaktuSolat.text = sholat?.isya
        }
    }

    private fun setupNotification() {

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}