package com.redeyesncode.quickpe.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.redeyesncode.dateme.base.Event
import com.redeyesncode.dateme.base.Resource
import com.redeyesncode.quickpe.data.BodyRegisterUser
import com.redeyesncode.quickpe.data.BodyUpdateKyc
import com.redeyesncode.quickpe.data.CommonStatusMessageResponse
import com.redeyesncode.quickpe.data.ResponseApiTest
import com.redeyesncode.quickpe.data.ResponseKycDetails
import com.redeyesncode.quickpe.data.ResponseLoginUser
import com.redeyesncode.quickpe.data.ResponseRegisterUser
import com.redeyesncode.quickpe.repository.MainRepo
import com.redeyesncode.quickpe.retrofit.FintechApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MultipartBody

class MainViewModel(val app: Application,
                    private val repos: MainRepo,
                    val fintechApi: FintechApi
) : AndroidViewModel(app)  {

    private val _getKycDetailsResponse = MutableLiveData<Event<Resource<ResponseKycDetails>>>()
    val getKycDetailsResponse : LiveData<Event<Resource<ResponseKycDetails>>> = _getKycDetailsResponse

    private val _submitKycRequestResponse = MutableLiveData<Event<Resource<ResponseLoginUser>>>()
    val submitKycRequestResponse : LiveData<Event<Resource<ResponseLoginUser>>> = _submitKycRequestResponse


    private val _uploadImageResponse = MutableLiveData<Event<Resource<CommonStatusMessageResponse>>>()
    val uploadImageResponse : LiveData<Event<Resource<CommonStatusMessageResponse>>> = _uploadImageResponse


    private val _apiTestResponse = MutableLiveData<Event<Resource<ResponseApiTest>>>()
    val apiTestResponse : LiveData<Event<Resource<ResponseApiTest>>> = _apiTestResponse

    fun apiTest(){
        _uploadImageResponse.postValue(Event(Resource.Loading()))
        viewModelScope.launch(Dispatchers.Main) {
            val result = repos.apiTest()
            _apiTestResponse.postValue(Event(result))
        }


    }

    fun uploadFile(file:MultipartBody.Part){
        _uploadImageResponse.postValue(Event(Resource.Loading()))
        viewModelScope.launch(Dispatchers.Main) {
            val result = repos.uploadFile(file)
            _uploadImageResponse.postValue(Event(result))
        }


    }

    fun submitKycRequest(registerUser: BodyUpdateKyc){
        _submitKycRequestResponse.postValue(Event(Resource.Loading()))
        viewModelScope.launch(Dispatchers.Main) {
            val result = repos.submitKycRequest(registerUser)
            _submitKycRequestResponse.postValue(Event(result))
        }
    }


    fun getKycDetails(userMap: HashMap<String,String>){

        _getKycDetailsResponse.postValue(Event(Resource.Loading()))
        viewModelScope.launch(Dispatchers.Main) {
            val result = repos.getKycDetails(userMap)
            _getKycDetailsResponse.postValue(Event(result))
        }

    }
}