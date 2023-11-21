package com.redeyesncode.quickpe.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.redeyesncode.dateme.base.Event
import com.redeyesncode.dateme.base.Resource
import com.redeyesncode.quickpe.data.BodyRegisterUser
import com.redeyesncode.quickpe.data.ResponseLoginUser
import com.redeyesncode.quickpe.data.ResponseRegisterUser
import com.redeyesncode.quickpe.repository.MainRepo
import com.redeyesncode.quickpe.retrofit.FintechApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AuthViewModel(val app: Application,
                    private val repos: MainRepo,
                    val fintechApi: FintechApi
) : AndroidViewModel(app) {
    private val _userLoginResponse = MutableLiveData<Event<Resource<ResponseLoginUser>>>()
    val userLoginResponse : LiveData<Event<Resource<ResponseLoginUser>>> = _userLoginResponse

    private val _responseRegisterUser = MutableLiveData<Event<Resource<ResponseRegisterUser>>>()
    val responseRegisterUser : LiveData<Event<Resource<ResponseRegisterUser>>> = _responseRegisterUser



    fun registerUser(registerUser: BodyRegisterUser){
        _responseRegisterUser.postValue(Event(Resource.Loading()))
        viewModelScope.launch(Dispatchers.Main) {
            val result = repos.registerUser(registerUser)
            _responseRegisterUser.postValue(Event(result))
        }
    }


    fun loginUser(userMap: HashMap<String,String>){

        _userLoginResponse.postValue(Event(Resource.Loading()))
        viewModelScope.launch(Dispatchers.Main) {
            val result = repos.loginUser(userMap)
            _userLoginResponse.postValue(Event(result))
        }

    }

}