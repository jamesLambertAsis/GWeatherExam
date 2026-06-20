package com.example.gweather

import android.util.Base64

fun String.encodeToBase64(): String {
    android.util.Log.d("Base64Check", "Original: $this -> Encoded: "+Base64.encodeToString(this.toByteArray(), Base64.NO_WRAP))
    return Base64.encodeToString(this.toByteArray(), Base64.NO_WRAP)
}

fun String.decodeFromBase64(): String {
    val decodedBytes = Base64.decode(this, Base64.NO_WRAP)
    return String(decodedBytes)
}