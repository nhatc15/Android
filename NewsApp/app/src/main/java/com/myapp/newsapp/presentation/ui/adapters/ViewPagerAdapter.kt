package com.myapp.newsapp.presentation.ui.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.myapp.newsapp.presentation.ui.fragment.viewpager.*

class ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int = 7

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> BusinessFragment()
            1 -> EntertainmentFragment()
            2 -> GeneralFragment()
            3 -> HealthFragment()
            4 -> ScienceFragment()
            5 -> SportFragment()
            6 -> TechnologyFragment()
            else -> TechnologyFragment()
        }
    }
}
