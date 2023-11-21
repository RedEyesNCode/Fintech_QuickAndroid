package com.redeyesncode.quickpe.ui.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.redeyesncode.quickpe.ui.fragments.FragmentKycStepFour
import com.redeyesncode.quickpe.ui.fragments.FragmentKycStepOne
import com.redeyesncode.quickpe.ui.fragments.FragmentKycStepThree
import com.redeyesncode.quickpe.ui.fragments.FragmentKycStepTwo

class KycPagerAdapter(fragmentManager: FragmentActivity) :
    FragmentStateAdapter(fragmentManager) {

    override fun getItemCount(): Int {
        return 4 // Number of fragments
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> FragmentKycStepOne()
            1 -> FragmentKycStepTwo()
            2 -> FragmentKycStepThree()
            3 -> FragmentKycStepFour()
            else -> throw IndexOutOfBoundsException("Invalid position")
        }
    }
}