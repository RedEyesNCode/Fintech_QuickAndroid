package com.redeyesncode.quickpe.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.redeyesncode.dateme.base.Event
import com.redeyesncode.quickpe.R
import com.redeyesncode.quickpe.databinding.ActivityApiTestBinding
import com.redeyesncode.quickpe.repository.DefaultMainRepo
import com.redeyesncode.quickpe.repository.MainRepo
import com.redeyesncode.quickpe.retrofit.FintechApi
import com.redeyesncode.quickpe.retrofit.RetrofitInstance
import com.redeyesncode.quickpe.ui.adapters.ApiTestAdapter
import com.redeyesncode.quickpe.ui.viewmodels.MainViewModel
import com.redeyesncode.redbet.base.BaseActivity

class ApiTestActivity : BaseActivity() {
    lateinit var binding:ActivityApiTestBinding
    lateinit var mainViewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityApiTestBinding.inflate(layoutInflater)
        setupViewModel()
        attachObservers()
        initClicks()
        setContentView(binding.root)
    }

    private fun initClicks() {
        binding.btnApiTest.setOnClickListener {
            mainViewModel.apiTest()
        }


    }

    private fun setupViewModel() {
        val mainRepos = DefaultMainRepo() as MainRepo
        val fintechApi: FintechApi = RetrofitInstance.api


        mainViewModel = MainViewModel(application,mainRepos,fintechApi)



    }

    private fun attachObservers(){

        mainViewModel.apiTestResponse.observe(this,Event.EventObserver(
            onLoading = {
                showLoadingDialog()
            },
            onError = {
                hideLoadingDialog()
                showSnackbar(it)
            },
            onSuccess = {
                hideLoadingDialog()
                showSnackbar("API RESPONSE ${it.apiStatus.toString()}")
                binding.recvCategory.adapter = ApiTestAdapter(this@ApiTestActivity,it.category)
                binding.recvCategory.layoutManager = LinearLayoutManager(this@ApiTestActivity,LinearLayoutManager.VERTICAL,false)
            }


        ))
    }
}