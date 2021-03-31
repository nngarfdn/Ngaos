package com.nanangarifudin.moviecatalogue.ui.home

import android.content.Context
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.udindev.ngaos.R
import com.udindev.ngaos.ui.dashboard.DaftarKelasFragment
import com.udindev.ngaos.ui.dashboard.KelasDiikutiFragment

class SectionPagerAdapter (private val mContext: Context, fm: FragmentManager) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(R.string.kelas, R.string.diikuti)
    }

    override fun getItem(position: Int): Fragment =
            when (position) {
                0 -> DaftarKelasFragment()
                1 -> KelasDiikutiFragment()
                else -> Fragment()
            }

    override fun getPageTitle(position: Int): CharSequence? = mContext.resources.getString(TAB_TITLES[position])

    override fun getCount(): Int = 2

}