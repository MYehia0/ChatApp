package com.example.chatapp.data.datasources

import com.example.chatapp.data.datasources.models.Message
import com.example.chatapp.data.datasources.models.Room
import com.example.chatapp.data.datasources.models.User
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FireStoreUtils {
    private val userCollectionName = "users"
    private val roomCollectionName = "rooms"

    private fun createCollections(collectionName:String?):CollectionReference{
        val db = FirebaseFirestore.getInstance()
        return db.collection(collectionName!!)
    }

    suspend fun insertUserToFireStore(user: User?): Task<Void> {
        return withContext(Dispatchers.IO) {
            val dbRef = createCollections(userCollectionName).document(user?.uid!!)
            dbRef.set(user)
        }
    }

    suspend fun getUserFromFireStore(userID:String?):Task<DocumentSnapshot> {
        return withContext(Dispatchers.IO) {
            val dbRef = createCollections(userCollectionName).document(userID!!)
            dbRef.get()
        }
    }

    suspend fun insertRoomToFireStore(room: Room?): Task<Void>? {
        return withContext(Dispatchers.IO) {
            val dbRef = createCollections(roomCollectionName).document()
            room?.rID = dbRef.id
            room?.let { dbRef.set(it) }
        }
    }

    suspend fun getRoomFromFireStore():Task<QuerySnapshot> {
        return withContext(Dispatchers.IO){ createCollections(roomCollectionName).get() }
    }

    suspend fun sendMessage(message: Message): Task<Void>?{
        return withContext(Dispatchers.IO){
            val roomRef = message.roomID?.let { createCollections(roomCollectionName).document(it) }
            val messages = roomRef?.collection("messages")
            val messageDoc = messages?.document()
            message.id = messageDoc?.id
            messageDoc?.set(message)
        }
    }

    suspend fun getAllMessage(roomID: String?): com.google.firebase.firestore.Query? {
        return withContext(Dispatchers.IO){
            val roomRef = roomID?.let { createCollections(roomCollectionName).document(it) }
            roomRef?.collection("messages")?.orderBy("dateTime")
        }
    }
}