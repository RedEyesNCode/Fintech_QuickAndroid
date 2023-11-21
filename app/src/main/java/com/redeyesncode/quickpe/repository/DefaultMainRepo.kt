package com.redeyesncode.quickpe.repository

import com.google.gson.Gson
import com.redeyesncode.dateme.base.Resource
import com.redeyesncode.quickpe.data.BodyRegisterUser
import com.redeyesncode.quickpe.data.BodyUpdateKyc
import com.redeyesncode.quickpe.data.CommonStatusMessageResponse
import com.redeyesncode.quickpe.data.ResponseKycDetails
import com.redeyesncode.quickpe.data.ResponseLoginUser
import com.redeyesncode.quickpe.data.ResponseRegisterUser
import com.redeyesncode.quickpe.retrofit.RetrofitInstance
import com.redeyesncode.redbet.base.safeCall
import okhttp3.MultipartBody

class DefaultMainRepo :MainRepo {

    override suspend fun registerUser(bodyRegisterUser: BodyRegisterUser): Resource<ResponseRegisterUser> {
        return safeCall {
            safeCall {
                val response =
                    RetrofitInstance.api.registerUser(
                        bodyRegisterUser
                    )
                if(response.code()==400){
                    val errorResponse = Gson().fromJson(response.errorBody()?.string(), CommonStatusMessageResponse::class.java)
                    Resource.Error(errorResponse.message.toString())
                }else{
                    Resource.Success(response.body()!!)
                }

            }
        }


    }

    override suspend fun loginUser(loginMap: HashMap<String, String>): Resource<ResponseLoginUser> {

        return safeCall {
            safeCall {
                val response =
                    RetrofitInstance.api.loginUser(
                        loginMap
                    )
                if(response.code()==400){
                    val errorResponse = Gson().fromJson(response.errorBody()?.string(), CommonStatusMessageResponse::class.java)
                    Resource.Error(errorResponse.message.toString())
                }else{
                    Resource.Success(response.body()!!)
                }

            }
        }

    }

    override suspend fun submitKycRequest(bodyUpdateKyc: BodyUpdateKyc): Resource<ResponseLoginUser> {
        return safeCall {
            safeCall {
                val response =
                    RetrofitInstance.api.submitKycRequest(
                        bodyUpdateKyc
                    )
                if(response.code()==400){
                    val errorResponse = Gson().fromJson(response.errorBody()?.string(), CommonStatusMessageResponse::class.java)
                    Resource.Error(errorResponse.message.toString())
                }else{
                    Resource.Success(response.body()!!)
                }

            }
        }

    }

    override suspend fun getKycDetails(loginMap: HashMap<String, String>): Resource<ResponseKycDetails> {
        return safeCall {
            safeCall {
                val response =
                    RetrofitInstance.api.getKycDetails(
                        loginMap
                    )
                if(response.code()==400){
                    val errorResponse = Gson().fromJson(response.errorBody()?.string(), CommonStatusMessageResponse::class.java)
                    Resource.Error(errorResponse.message.toString())
                }else{
                    Resource.Success(response.body()!!)
                }

            }
        }

    }


    override suspend fun uploadFile(file: MultipartBody.Part): Resource<CommonStatusMessageResponse> {
        return safeCall {
            safeCall {
                val response =
                    RetrofitInstance.api.uploadFile(
                        file
                    )
                if(response.code()==400){
                    val errorResponse = Gson().fromJson(response.errorBody()?.string(), CommonStatusMessageResponse::class.java)
                    Resource.Error(errorResponse.message.toString())
                }else{
                    Resource.Success(response.body()!!)
                }

            }
        }

    }
}