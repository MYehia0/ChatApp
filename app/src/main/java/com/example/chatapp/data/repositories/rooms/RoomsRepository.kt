package com.example.chatapp.data.repositories.rooms

import com.example.chatapp.data.datasources.FireStoreUtils
import com.example.chatapp.data.datasources.models.Room
import javax.inject.Inject

class RoomsRepository @Inject constructor(private val fb : FireStoreUtils) {
    suspend fun insertRoomToFireStore(room: Room) = fb.insertRoomToFireStore(room)
    suspend fun getRoomFromFireStore() = fb.getRoomFromFireStore()
}