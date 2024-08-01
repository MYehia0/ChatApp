package com.example.chatapp.database

import com.example.chatapp.database.models.Message
import com.example.chatapp.database.models.Room
import com.example.chatapp.database.models.User
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot

class FireStoreUtils {
    private val userCollectionName = "users"
    private val roomCollectionName = "rooms"

    private fun createCollections(collectionName:String?):CollectionReference{
        val db = FirebaseFirestore.getInstance()
        return db.collection(collectionName!!)
    }

    fun insertUserToFireStore(user: User?): Task<Void> {
        val dbRef = createCollections(userCollectionName).document(user?.uid!!)
        return dbRef.set(user)
    }

    fun getUserFromFireStore(userID:String?):Task<DocumentSnapshot>{
        val dbRef = createCollections(userCollectionName).document(userID!!)
        return dbRef.get()
    }

    fun insertRoomToFireStore(room: Room?): Task<Void>? {
        val dbRef = createCollections(roomCollectionName).document()
        room?.rID = dbRef.id
        return room?.let{ dbRef.set(it) }
    }

    fun getRoomFromFireStore():Task<QuerySnapshot> {
        return createCollections(roomCollectionName).get()
    }

    fun sendMessage(message: Message): Task<Void>?{
        val roomRef = message.roomID?.let { createCollections(roomCollectionName).document(it) }
        val messages = roomRef?.collection("messages")
        val messageDoc = messages?.document()
        message.id = messageDoc?.id
        return messageDoc?.set(message)
    }

    fun getAllMessage(roomID: String?): CollectionReference? {
        val roomRef = roomID?.let { createCollections(roomCollectionName).document(it) }
        return roomRef?.collection("messages")
    }
}