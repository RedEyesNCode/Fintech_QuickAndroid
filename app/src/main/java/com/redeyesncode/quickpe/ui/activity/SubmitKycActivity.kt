package com.redeyesncode.quickpe.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.redeyesncode.quickpe.R
import com.redeyesncode.quickpe.data.BodyUpdateKyc
import com.redeyesncode.quickpe.databinding.ActivitySubmitKycBinding
import com.redeyesncode.quickpe.ui.adapters.KycPagerAdapter
import com.redeyesncode.redbet.base.BaseActivity
import com.redeyesncode.redbet.session.AppSession
import com.redeyesncode.redbet.session.Constant

class SubmitKycActivity : BaseActivity() {
    lateinit var binding:ActivitySubmitKycBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySubmitKycBinding.inflate(layoutInflater)
        initClicks()
        showMessageDialog("Swipe to Move to Next Step","Info")
        setContentView(binding.root)
    }

    override fun onDestroy() {
        super.onDestroy()
        AppSession(this@SubmitKycActivity).putObject(Constant.BODY_UPDATE_KYC, BodyUpdateKyc())

    }

    private fun initClicks() {

        val adapter = KycPagerAdapter(this)
        binding.toolBar.tvTitle.text = "Submit KYC Request"
        binding.toolBar.ivBack.setOnClickListener {
            finish()
        }
        binding.viewPager.adapter = adapter

        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                // Handle page selection
                when(position){

                    0-> binding.progressFragment.progress = 25
                    1-> binding.progressFragment.progress = 50
                    2 -> binding.progressFragment.progress = 75
                    3 -> binding.progressFragment.progress = 100
                    else -> binding.progressFragment.progress = 0

                }
            }
        })



    }
}