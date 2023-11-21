package com.redeyesncode.quickpe.data

import com.google.gson.annotations.SerializedName

data class BodyRegisterUser(@SerializedName("name"     ) var name     : String? = null,
                            @SerializedName("gender"   ) var gender   : String? = null,
                            @SerializedName("number"   ) var number   : String? = null,
                            @SerializedName("email"    ) var email    : String? = null,
                            @SerializedName("password" ) var password : String? = null,
                            @SerializedName("isActive" ) var isActive : String? = null,
                            @SerializedName("role"     ) var role     : String? = null
)
