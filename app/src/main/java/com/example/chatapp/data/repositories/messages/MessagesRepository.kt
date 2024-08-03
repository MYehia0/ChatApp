package com.example.chatapp.data.repositories.messages

import com.example.chatapp.data.datasources.FireStoreUtils
import com.example.chatapp.data.datasources.models.Message
import javax.inject.Inject

class MessagesRepository @Inject constructor(private val fb : FireStoreUtils) {
    suspend fun sendMessage(message: Message) = fb.sendMessage(message)
    suspend fun getAllMessage(roomID: String) = fb.getAllMessage(roomID)
}