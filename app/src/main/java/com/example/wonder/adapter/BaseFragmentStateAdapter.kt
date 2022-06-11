package com.example.wonder.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

/**
 * ViewPager2 通用适配器
 */
class BaseFragmentStateAdapter(
    fragmentStateAdapter: FragmentActivity,
    private val fragmentList: List<Fragment>
) : FragmentStateAdapter(fragmentStateAdapter) {
    override fun getItemCount(): Int {
        return fragmentList.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragmentList[position]
    }
}