package com.gosty.githubuserapp.data.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.gosty.githubuserapp.ui.tab.TabFragment

class SectionsPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    var username: String? = null

    override fun createFragment(position: Int): Fragment {
        val fragment = TabFragment()
        val bundle = Bundle()
        bundle.putString(TabFragment.ARG_USERNAME, username)
        if (position == 0) {
            bundle.putString(TabFragment.ARG_TAB, TabFragment.TAB_FOLLOWERS)
        } else {
            bundle.putString(TabFragment.ARG_TAB, TabFragment.TAB_FOLLOWING)
        }
        fragment.arguments = bundle
        return fragment
    }

    override fun getItemCount(): Int = 2
}