package com.redeyesncode.quickpe.repository

import com.redeyesncode.dateme.base.Resource
import com.redeyesncode.quickpe.data.BodyRegisterUser
import com.redeyesncode.quickpe.data.BodyUpdateKyc
import com.redeyesncode.quickpe.data.CommonStatusMessageResponse
import com.redeyesncode.quickpe.data.ResponseKycDetails
import com.redeyesncode.quickpe.data.ResponseLoginUser
import com.redeyesncode.quickpe.data.ResponseRegisterUser
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body

interface MainRepo {

    suspend fun registerUser( bodyRegisterUser: BodyRegisterUser): Resource<ResponseRegisterUser>
    suspend fun loginUser( loginMap:HashMap<String,String>):Resource<ResponseLoginUser>

    suspend fun submitKycRequest( bodyUpdateKyc: BodyUpdateKyc):Resource<ResponseLoginUser>
    suspend fun getKycDetails( loginMap:HashMap<String,String>):Resource<ResponseKycDetails>

    suspend fun uploadFile( file:MultipartBody.Part):Resource<CommonStatusMessageResponse>

}