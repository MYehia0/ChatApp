package com.example.chatapp.data.repositories

import com.example.chatapp.data.datasources.FireStoreUtils
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DiRepository {

    @Provides
    @Singleton
    fun provideFireStore(): FireStoreUtils {
        return FireStoreUtils()
    }

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }
}

