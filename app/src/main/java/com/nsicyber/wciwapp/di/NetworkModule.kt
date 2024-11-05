package com.nsicyber.wciwapp.di

import com.nsicyber.wciwapp.BuildConfig
import com.nsicyber.wciwapp.data.remote.ApiInterceptor
import com.nsicyber.wciwapp.data.remote.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {
    @Provides
    @Singleton
    fun provideOkHttpClient(apiInterceptor: ApiInterceptor): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(
            HttpLoggingInterceptor().apply {
                level = (HttpLoggingInterceptor.Level.BODY)
            },
        )
            .addInterceptor(apiInterceptor)
            .addInterceptor(
                Interceptor { chain ->
                    val request =
                        chain.request().newBuilder()
                            .build()
                    chain.proceed(request)
                },
            ).build()
    }

    @Provides
    @Singleton
    fun provideApiService(okHttpClient: OkHttpClient): ApiService =
        Retrofit.Builder().baseUrl(BuildConfig.API_URL)
            .addConverterFactory(GsonConverterFactory.create()).client(okHttpClient).build()
            .create(ApiService::class.java)


    @Singleton
    @Provides
    fun provideApiInterceptor(): ApiInterceptor {
        return ApiInterceptor()
    }


}