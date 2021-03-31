package com.udindev.ngaos.ui.dashboard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.nanangarifudin.moviecatalogue.ui.home.SectionPagerAdapter
import com.udindev.ngaos.R
import com.udindev.ngaos.databinding.ActivityDashboardBinding

class DashboardActivity : AppCompatActivity() {

    private lateinit var binding :ActivityDashboardBinding;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sectionsPagerAdapter = SectionPagerAdapter(this, supportFragmentManager)
        binding.viewPager.adapter = sectionsPagerAdapter
        binding.tabs.setupWithViewPager(binding.viewPager)
        supportActionBar?.elevation = 0f
    }
}