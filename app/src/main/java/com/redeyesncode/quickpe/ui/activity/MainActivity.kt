package com.redeyesncode.quickpe.ui.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.redeyesncode.quickpe.R
import com.redeyesncode.redbet.base.BaseActivity

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Handler().postDelayed(Runnable {
                                       val intentLogin = Intent(this@MainActivity, LoginActivity::class.java)
            intentLogin.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intentLogin)


        },3000)

        setContentView(R.layout.activity_main)
    }
}