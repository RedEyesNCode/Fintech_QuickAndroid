package com.redeyesncode.redbet.base

import android.util.Log
import com.google.gson.Gson
import com.jakewharton.disklrucache.DiskLruCache
import com.redeyesncode.dateme.base.Resource
import java.io.IOException

inline fun <T> safeCall(action: () -> Resource<T>): Resource<T> {
    return try {
        action()
    } catch (e: Exception) {
        Log.i("RETROFIT_BUMBLE",e.message.toString())
        Log.i("RETROFIT_BUMBLE",e.message.toString())
        Log.i("RETROFIT_BUMBLE",e.message.toString())
        Log.i("RETROFIT_BUMBLE",e.message.toString())
        Log.i("RETROFIT_BUMBLE",e.message.toString())
        Resource.Error(e.message ?: "An unknown error occured")
    }
}
inline fun <reified T> DiskLruCache.getNetworkResponse(cacheKey: String): T? {
    val snapshot = this.get(cacheKey)
    return snapshot?.use {
        val json = it.getString(0)
        Gson().fromJson(json, T::class.java)
    }
}

fun DiskLruCache.putNetworkResponse(cacheKey: String, response: Any) {
    val editor = this.edit(cacheKey)
    editor?.let {
        try {
            val json = Gson().toJson(response)
            it.set(0, json)
            it.commit()
        } catch (e: IOException) {
            e.printStackTrace()
            it.abort()
        }
    }
}
