
# FINTECH KYC AUTH ANDROID APP

Android Front End App Made in Kotlin Using MVVM Artitecture Pattern.

This App Demonstrates the Flow of a Fintech Loan Taking application when the user signs up and applies for the _KYC APPLICATION IN THE MOST FINTECH APPS_



## Authors

- [@RedEyesNCode](https://github.com/RedEyesNCode)

## Android Modules

| Info             | Core                                                                |
| ----------------- | ------------------------------------------------------------------ |
| Implementation of Api Calls and Error H | ![Static Badge](https://img.shields.io/badge/livedata-_viewmodels-blue)  |
| Android Source Code Base | ![Static Badge](https://img.shields.io/badge/KOTLIN_-blue) |
| Android MVVM Artitecture in Kotlin | ![Static Badge](https://img.shields.io/badge/MVVM_-yellow) |
| Makes Use of the ViewSystem using ViewBinding and Data Binding | ![Static Badge](https://img.shields.io/badge/ViewBinding_-red) |
| _Contains Base ActivityFragments and Adapters to be used all over the app_ | Base Modules  |
| _Api Response and Body Input Data classes are stored here_ | Data Modules  |
| _Repository Handles the api call from retrofit and provides response to viewmodels_ | Repository  |
| _This Module contains two imp classes form retrofit Single Instance and the Api Interface_ | retrofit  |
| _Session Contains the Shared Preferences Storage of Android_ | Session  |
| _Contains Code for Activity Fragment, Adapters and Dialogs_ | Session  |

## Deployment

To deploy this project run

```
 Run on Android Studio. It will require the following depedencies
```
```
    //sdp font library
    implementation 'com.intuit.sdp:sdp-android:1.1.0'
    implementation 'com.intuit.ssp:ssp-android:1.1.0'

    //CIRCLE IMAGE VIEW
    implementation 'de.hdodenhof:circleimageview:3.1.0'
    implementation 'com.makeramen:roundedimageview:2.3.0'

    // Retrofit
    implementation 'com.squareup.retrofit2:retrofit:2.9.0'
    implementation 'com.squareup.retrofit2:converter-gson:2.9.0'
    implementation 'com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.2'

    //GLIDE_FOR_LOADING_IMAGES
    implementation 'com.github.bumptech.glide:glide:4.16.0'
    annotationProcessor 'com.github.bumptech.glide:compiler:4.16.0'

    // DISK_LRU_ANDROID_CACHING
    implementation 'com.jakewharton:disklrucache:2.0.2'

    // Lifecycle
    implementation 'androidx.lifecycle:lifecycle-runtime-ktx:2.6.2'
    implementation 'androidx.activity:activity-compose:1.8.0'
    
    // Navigation component
    implementation 'androidx.navigation:navigation-fragment:2.7.5'
    implementation "androidx.navigation:navigation-ui-ktx:2.7.5"

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.0")

    implementation 'com.google.android.gms:play-services-maps:18.2.0'

    implementation "androidx.camera:camera-core:1.3.0"
    implementation "androidx.camera:camera-camera2:1.3.0"
    implementation "androidx.camera:camera-lifecycle:1.3.0"

    implementation "androidx.swiperefreshlayout:swiperefreshlayout:1.1.0"
```


## Documentation

#### Android Calls the following set of API's (Customer Android App)

- _registerUser_
``` 
@POST("fintech/register-user")
suspend fun registerUser(@Body bodyRegisterUser: BodyRegisterUser): Response<ResponseRegisterUser>
```
- _loginUser_

```
 @POST("fintech/login-user")
    suspend fun loginUser(@Body loginMap:HashMap<String,String>):Response<ResponseLoginUser>
```
- _submitKycRequest_

```
 @POST("fintech/submit-kyc")
    suspend fun submitKycRequest(@Body bodyUpdateKyc: BodyUpdateKyc):Response<ResponseLoginUser>
```

- _getKycDetails_
```
@POST("fintech/get-kyc-details")
    suspend fun getKycDetails(@Body loginMap:HashMap<String,String>):Response<ResponseKycDetails>
```
- _upload-file_

```
 @Multipart
    @POST("fintech/upload-file")
    suspend fun uploadFile(@Part file:MultipartBody.Part):Response<CommonStatusMessageResponse>

```


## Optimizations & Features

- The Photo Picker using camera might not work in some devices. works fine with gallery image Picker
- No User Profile Screen is displayed need to binding session data there.

- _SWIPE REFRESH FEATURE IS THERE TO VIEW THE CURRENT KYC STATUS (DASHBOARD ACTIVITY)_ 

- Manage Navigation of the Side Bar


