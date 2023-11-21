package com.redeyesncode.quickpe.ui.activity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.redeyesncode.dateme.base.Event
import com.redeyesncode.quickpe.R
import com.redeyesncode.quickpe.data.BodyRegisterUser
import com.redeyesncode.quickpe.databinding.ActivitySignupBinding
import com.redeyesncode.quickpe.repository.DefaultMainRepo
import com.redeyesncode.quickpe.repository.MainRepo
import com.redeyesncode.quickpe.retrofit.FintechApi
import com.redeyesncode.quickpe.retrofit.RetrofitInstance
import com.redeyesncode.quickpe.ui.dialogs.MultiSelectDialog
import com.redeyesncode.quickpe.ui.viewmodels.AuthViewModel
import com.redeyesncode.redbet.base.BaseActivity
import com.redeyesncode.redbet.session.AppSession
import com.redeyesncode.redbet.session.Constant

class SignupActivity : BaseActivity() {
    lateinit var binding:ActivitySignupBinding


    lateinit var viewModel: AuthViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivitySignupBinding.inflate(layoutInflater)

        initClicks()
        setupViewModel()
        attachObservers()

        setContentView(binding.root)
    }

    private fun attachObservers() {


        viewModel.responseRegisterUser.observe(this,Event.EventObserver(
            onLoading = {
                showLoadingDialog()
            },
            onError = {
                hideLoadingDialog()
                showToast(it)
            },
            onSuccess = {
                hideLoadingDialog()
                showToast(it.message.toString())
                Handler().postDelayed(Runnable { finish() },4000)
            }
        ))



    }

    private fun initClicks() {

        binding.toolBar.ivBack.setOnClickListener {
            finish()
        }
        binding.toolBar.tvTitle.text = "Create New Account"

        binding.edtGender.setOnClickListener {
        val options = arrayOf("Male","Female","Other")
            showOptionsDialog(this@SignupActivity,options)

        }
        binding.btnRegister.setOnClickListener {
            if(binding.edtName.text.toString().isEmpty()){
                binding.edtName.error = "Enter your name"
            }else if(binding.edtGender.text.toString().isEmpty()){
                showToast("Select gender")
            }else if(binding.edtMobileNumber.text.toString().isEmpty()){
                binding.edtMobileNumber.error = "Please enter number"
            }else if(binding.edtEmail.text.toString().isEmpty()){

                binding.edtEmail.error = "Please enter email address"

            }else if(binding.edtPassword.text.toString().isEmpty()){
                binding.edtPassword.error = "Please enter password"
            }else{
                val bodyRegisterUser = BodyRegisterUser()
                bodyRegisterUser.isActive = true.toString()
                bodyRegisterUser.email = binding.edtEmail.text.toString()
                bodyRegisterUser.password = binding.edtPassword.text.toString()
                bodyRegisterUser.name = binding.edtName.text.toString()
                bodyRegisterUser.gender = binding.edtGender.text.toString()
                bodyRegisterUser.number = binding.edtMobileNumber.text.toString()
                bodyRegisterUser.role = "USER"

                viewModel.registerUser(bodyRegisterUser)

            }


        }


    }
    private fun setupViewModel() {
        val mainRepos = DefaultMainRepo() as MainRepo
        val fintechApi: FintechApi = RetrofitInstance.api


        viewModel = AuthViewModel(application,mainRepos,fintechApi)



    }

    fun showOptionsDialog(context: Context, options: Array<String>) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Select an Option")
            .setItems(options) { _, which ->
                // Handle the selected option here
                val selectedOption = options[which]
                // Do something with the selected option
                // For example, you could pass it to another function or display it
                // Or perform any action based on the selected option
                binding.edtGender.setText(selectedOption)

            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
        val dialog = builder.create()
        dialog.show()
    }
}