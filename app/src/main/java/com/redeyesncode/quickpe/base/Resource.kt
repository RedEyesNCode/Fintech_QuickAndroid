package com.redeyesncode.dateme.base

sealed class Resource<T>(
    //in sealed class we can make n number of instance of its subclass
    //but cannot instantiate Parent sealed class
    val data :T?=null, //success data
    val message:String?=null //response message mainly error
) {
    /*
    only these classes are allowed to inherit from Resource.kt class
     */
    class Success<T>(data: T): Resource<T>(data)
    class Error<T>(message: String,data: T?=null): Resource<T>(data,message)
    class Loading<T>: Resource<T>()


}
