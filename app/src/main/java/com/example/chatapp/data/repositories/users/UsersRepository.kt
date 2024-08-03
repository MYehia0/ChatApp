package com.example.chatapp.data.repositories.users

import com.example.chatapp.data.datasources.FireStoreUtils
import com.example.chatapp.data.datasources.models.User
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UsersRepository@Inject constructor(
    private val fb : FireStoreUtils, private val auth : FirebaseAuth) {
    suspend fun insertUserToFireStore(user: User) = fb.insertUserToFireStore(user)
    suspend fun getUserFromFireStore(userID: String) = fb.getUserFromFireStore(userID)
    suspend fun createUserWithEmailAndPassword(email: String, password:String) =
        withContext(Dispatchers.IO){ auth.createUserWithEmailAndPassword(email,password) }
    suspend fun signInWithEmailAndPassword(email: String, password:String) =
        withContext(Dispatchers.IO){ auth.signInWithEmailAndPassword(email,password) }
}