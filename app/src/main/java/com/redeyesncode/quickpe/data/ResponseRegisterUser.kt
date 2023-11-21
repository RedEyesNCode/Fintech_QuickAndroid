package com.redeyesncode.quickpe.data

import com.google.gson.annotations.SerializedName

data class ResponseRegisterUser(@SerializedName("status"  ) var status  : String? = null,
                                @SerializedName("code"    ) var code    : Int?    = null,
                                @SerializedName("message" ) var message : String? = null,
                                @SerializedName("data"    ) var data    : Data?   = Data()){
    data class Data (

        @SerializedName("userId"    ) var userId    : Int?     = null,
        @SerializedName("name"      ) var name      : String?  = null,
        @SerializedName("gender"    ) var gender    : String?  = null,
        @SerializedName("number"    ) var number    : String?  = null,
        @SerializedName("email"     ) var email     : String?  = null,
        @SerializedName("password"  ) var password  : String?  = null,
        @SerializedName("createdAt" ) var createdAt : String?  = null,
        @SerializedName("updatedAt" ) var updatedAt : String?  = null,
        @SerializedName("role"      ) var role      : String?  = null,
        @SerializedName("active"    ) var active    : Boolean? = null

    )
}
