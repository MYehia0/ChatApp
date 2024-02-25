package com.example.chatapp.database

import com.example.chatapp.database.models.User
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class FireStoreUtils {
    private val collectionName = "users"
    private fun createCollections(collectionName:String?):CollectionReference{
        val db = FirebaseFirestore.getInstance()
        return db.collection(collectionName!!)
    }
    fun insertUserToFireStore(user: User?): Task<Void> {
        val dbRef = createCollections(collectionName).document(user?.uid!!)
        return dbRef.set(user)
    }
    fun getUserFromFireStore(userID:String?):Task<DocumentSnapshot>{
        val dbRef = createCollections(collectionName).document(userID!!)
        return dbRef.get()
    }
}