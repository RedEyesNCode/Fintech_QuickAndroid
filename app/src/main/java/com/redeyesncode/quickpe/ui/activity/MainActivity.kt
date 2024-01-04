package com.redeyesncode.quickpe.ui.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.redeyesncode.quickpe.R
import com.redeyesncode.quickpe.databinding.ActivityMainBinding
import com.redeyesncode.redbet.base.BaseActivity

class MainActivity : BaseActivity() {

    lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        binding.btnApiTest.setOnClickListener {
            val intentLogin = Intent(this@MainActivity, ApiTestActivity::class.java)
            intentLogin.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intentLogin)

        }
        setContentView(binding.root)
    }
}