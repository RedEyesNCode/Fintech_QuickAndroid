package com.redeyesncode.quickpe.retrofit

import com.google.android.gms.common.internal.service.Common
import com.redeyesncode.quickpe.data.BodyRegisterUser
import com.redeyesncode.quickpe.data.BodyUpdateKyc
import com.redeyesncode.quickpe.data.CommonStatusMessageResponse
import com.redeyesncode.quickpe.data.ResponseApiTest
import com.redeyesncode.quickpe.data.ResponseKycDetails
import com.redeyesncode.quickpe.data.ResponseLoginUser
import com.redeyesncode.quickpe.data.ResponseRegisterUser
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface FintechApi {

    @POST("fintech/register-user")
    suspend fun registerUser(@Body bodyRegisterUser: BodyRegisterUser): Response<ResponseRegisterUser>


    @POST("fintech/login-user")
    suspend fun loginUser(@Body loginMap:HashMap<String,String>):Response<ResponseLoginUser>

    @POST("fintech/submit-kyc")
    suspend fun submitKycRequest(@Body bodyUpdateKyc: BodyUpdateKyc):Response<ResponseLoginUser>


    @POST("fintech/get-kyc-details")
    suspend fun getKycDetails(@Body loginMap:HashMap<String,String>):Response<ResponseKycDetails>


    @Multipart
    @POST("fintech/upload-file")
    suspend fun uploadFile(@Part file:MultipartBody.Part):Response<CommonStatusMessageResponse>



    @POST("book_indian_talents/api/process.php?action=cat_subcat")
    suspend fun apiTest():Response<ResponseApiTest>


}