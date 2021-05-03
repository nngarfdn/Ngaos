package com.udindev.ngaos

import android.R.id.toggle
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.udindev.ngaos.databinding.ActivityNotificationBinding
import com.udindev.ngaos.utils.MyReceiver


class NotificationActivity : AppCompatActivity() {

    private lateinit var alarmReceiver: MyReceiver
    private lateinit var binding : ActivityNotificationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        alarmReceiver = MyReceiver()





        val sharedPrefs = getSharedPreferences("com.udindev.ngaos", MODE_PRIVATE)
        binding.btnSwitch.setChecked(sharedPrefs.getBoolean("sholat", true))

        binding.btnSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {

                val editor = getSharedPreferences("com.udindev.ngaos", MODE_PRIVATE).edit()
                editor.putBoolean("sholat", true)
                editor.commit()

                val repeatTime = "11:07"
                val repeatMessage = "Yuk buka aplikasi"
                alarmReceiver.setRepeatingAlarm(
                    this, MyReceiver.TYPE_REPEATING,
                    repeatTime, repeatMessage
                )
            } else {
                val editor = getSharedPreferences("com.udindev.ngaos", MODE_PRIVATE).edit()
                editor.putBoolean("sholat", false)
                editor.commit()
                alarmReceiver.cancelAlarm(this, MyReceiver.TYPE_REPEATING)
            }
        }
    }


}