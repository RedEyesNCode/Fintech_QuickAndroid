package com.redeyesncode.quickpe.ui.activity

import android.content.Intent
import android.os.Bundle
import com.redeyesncode.dateme.base.Event
import com.redeyesncode.quickpe.databinding.ActivityLoginBinding
import com.redeyesncode.quickpe.repository.DefaultMainRepo
import com.redeyesncode.quickpe.repository.MainRepo
import com.redeyesncode.quickpe.retrofit.FintechApi
import com.redeyesncode.quickpe.retrofit.RetrofitInstance
import com.redeyesncode.quickpe.ui.viewmodels.AuthViewModel
import com.redeyesncode.redbet.base.BaseActivity
import com.redeyesncode.redbet.session.AppSession
import com.redeyesncode.redbet.session.Constant

class LoginActivity : BaseActivity() {

    lateinit var binding:ActivityLoginBinding
    lateinit var viewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)

        initClicks()
        setupViewModel()
        attachObservers()
        setContentView(binding.root)
    }

    private fun attachObservers() {
        viewModel.userLoginResponse.observe(this,Event.EventObserver(

            onLoading = {
                showLoadingDialog()
            }, onError = {
                hideLoadingDialog()
                showToast(it)
            },
            onSuccess = {
                hideLoadingDialog()
                AppSession(this@LoginActivity).putObject(Constant.USER_LOGIN,it)
                val intentDashboard = Intent(this@LoginActivity,DashboardActivity::class.java)
                intentDashboard.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intentDashboard)



            }

        ))


    }

    private fun initClicks() {
        binding.btnRegister.setOnClickListener {
            val intentSignup = Intent(this@LoginActivity, SignupActivity::class.java)
            startActivity(intentSignup)

        }
        binding.ivMain.setOnClickListener {
            val intentDashboard = Intent(this@LoginActivity,DashboardActivity::class.java)
            intentDashboard.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intentDashboard)
        }

        binding.btnLogin.setOnClickListener {
            if(binding.edtMobileNumber.text.toString().isEmpty()){
                showMessageDialog("Please enter mobile number","Info")
            }else if(binding.edtPassword.text.toString().isEmpty()){
                showMessageDialog("Please enter password","Info")
            }else{
                val map = hashMapOf<String,String>()
                map.put("number",binding.edtMobileNumber.text.toString())
                map.put("password",binding.edtPassword.text.toString())

                viewModel.loginUser(map)
            }


        }





    }
    private fun setupViewModel() {
        val mainRepos = DefaultMainRepo() as MainRepo
        val fintechApi: FintechApi = RetrofitInstance.api


        viewModel = AuthViewModel(application,mainRepos,fintechApi)



    }
}