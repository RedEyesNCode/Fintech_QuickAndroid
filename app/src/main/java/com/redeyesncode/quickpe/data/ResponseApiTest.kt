package com.redeyesncode.quickpe.data

import com.google.gson.annotations.SerializedName

data class ResponseApiTest(  @SerializedName("category"   ) var category  : ArrayList<Category> = arrayListOf(),
                             @SerializedName("result"     ) var result    : String?             = null,
                             @SerializedName("api_status" ) var apiStatus : String?             = null) {


    data class Category (

        @SerializedName("id"          ) var id          : String?           = null,
        @SerializedName("name"        ) var name        : String?           = null,
        @SerializedName("subcategory" ) var subcategory : ArrayList<String> = arrayListOf()

    )
}