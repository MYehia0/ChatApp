package com.example.chatapp.domain.usecases.messages

import com.example.chatapp.data.repositories.messages.MessagesRepository
import javax.inject.Inject

class GetMessageInteractor @Inject constructor(private val messagesRepository:MessagesRepository) {
    suspend operator fun invoke(roomID: String) = messagesRepository.getAllMessage(roomID)
}