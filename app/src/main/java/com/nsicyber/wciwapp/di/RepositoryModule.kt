package com.nsicyber.wciwapp.di

import com.google.firebase.database.FirebaseDatabase
import com.nsicyber.wciwapp.data.remote.ApiService
import com.nsicyber.wciwapp.data.repository.NetworkRepositoryImpl
import com.nsicyber.wciwapp.domain.repository.NetworkRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideNetworkRepository(
        api: ApiService,
    ): NetworkRepository = NetworkRepositoryImpl(api)

    @Provides
    @Singleton
    fun provideFirebaseDatabase(): FirebaseDatabase {
        return FirebaseDatabase.getInstance("https://wciwapp-default-rtdb.europe-west1.firebasedatabase.app")
    }
}
