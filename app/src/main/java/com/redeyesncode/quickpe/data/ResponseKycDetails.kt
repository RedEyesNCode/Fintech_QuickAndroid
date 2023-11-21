package com.redeyesncode.quickpe.data

import com.google.gson.annotations.SerializedName

data class ResponseKycDetails( @SerializedName("status"  ) var status  : String? = null,
                               @SerializedName("code"    ) var code    : Int?    = null,
                               @SerializedName("message" ) var message : String? = null,
                               @SerializedName("data"    ) var data    : Data?   = Data()
){
    data class Data (

        @SerializedName("userKycRequestId"  ) var userKycRequestId  : Int?    = null,
        @SerializedName("dateOfBirth"       ) var dateOfBirth       : String? = null,
        @SerializedName("monthlySalary"     ) var monthlySalary     : String? = null,
        @SerializedName("permanentAddress"  ) var permanentAddress  : String? = null,
        @SerializedName("currentAddress"    ) var currentAddress    : String? = null,
        @SerializedName("relativeName"      ) var relativeName      : String? = null,
        @SerializedName("relativeNumber"    ) var relativeNumber    : String? = null,
        @SerializedName("panCard"           ) var panCard           : String? = null,
        @SerializedName("panCardImage"      ) var panCardImage      : String? = null,
        @SerializedName("aadharNumber"      ) var aadharNumber      : String? = null,
        @SerializedName("aadharImageFront"  ) var aadharImageFront  : String? = null,
        @SerializedName("aadharImageBack"   ) var aadharImageBack   : String? = null,
        @SerializedName("signatureImage"    ) var signatureImage    : String? = null,
        @SerializedName("userSelfie"        ) var userSelfie        : String? = null,
        @SerializedName("bankStatementFile" ) var bankStatementFile : String? = null,
        @SerializedName("kycStatus"         ) var kycStatus         : String? = null,
        @SerializedName("deniedReason"      ) var deniedReason      : String? = null,
        @SerializedName("createdAt"         ) var createdAt         : String? = null,
        @SerializedName("updatedAt"         ) var updatedAt         : String? = null

    )
}
