package com.redeyesncode.quickpe.data

import com.google.gson.annotations.SerializedName

data class BodyUpdateKyc (
    @SerializedName("userId"     ) var userId     : Int?        = null,
    @SerializedName("kycRequest" ) var kycRequest : KycRequest? = KycRequest()
){

    data class KycRequest (

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
        @SerializedName("kycStatus"         ) var kycStatus         : String? = null

    )

}