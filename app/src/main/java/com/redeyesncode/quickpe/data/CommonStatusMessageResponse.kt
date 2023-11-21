package com.redeyesncode.quickpe.data

import com.google.gson.annotations.SerializedName

data class CommonStatusMessageResponse(@SerializedName("status"  ) var status  : Boolean? = null,
                                       @SerializedName("message" ) var message : String?  = null)
