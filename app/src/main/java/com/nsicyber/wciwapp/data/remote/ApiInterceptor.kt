package com.nsicyber.wciwapp.data.remote

import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import okhttp3.Interceptor
import okhttp3.Response


class ApiInterceptor : Interceptor {

    private val apiKey: String
        get() {
            val remoteConfig = Firebase.remoteConfig
            return remoteConfig.getString("api_key")
        }

    init {
        val remoteConfig = Firebase.remoteConfig
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 3600
        }
        remoteConfig.setConfigSettingsAsync(configSettings)
        remoteConfig.setDefaultsAsync(mapOf("api_key" to ""))
        fetchRemoteConfig()
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        synchronized(this) {
            val newRequest = chain.request().newBuilder()
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .addHeader("Authorization", "Bearer $apiKey")
                .build()

            return chain.proceed(newRequest)
        }
    }

    private fun fetchRemoteConfig() {
        Firebase.remoteConfig.fetchAndActivate()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Yeni konfigürasyonlar başarıyla alındı ve etkinleştirildi.
                } else {
                    // Hata durumunda varsayılan API anahtarı kullanılacak
                }
            }
    }
}

