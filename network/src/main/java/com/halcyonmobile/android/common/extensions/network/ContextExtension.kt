package com.halcyonmobile.android.common.extensions.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.annotation.RequiresApi

/**
 * Checks if the device's active Network has internet capability.
 *
 * @return true if Device has Active Internet Available Network, false otherwise.
 */
val Context.isNetworkActiveCompat: Boolean
    get() {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            connectivityManager.isNetworkActive
        } else {
            connectivityManager.isNetworkActiveDeprecated
        }
    }

@get:RequiresApi(Build.VERSION_CODES.M)
val ConnectivityManager.isNetworkActive: Boolean
    get() {
        val activeNetwork = activeNetwork ?: return false
        val networkCapabilities = getNetworkCapabilities(activeNetwork) ?: return false

        return networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
                networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
    }

@get:Suppress("DEPRECATION")
private val ConnectivityManager.isNetworkActiveDeprecated: Boolean
    get() = activeNetworkInfo?.isConnected == true
