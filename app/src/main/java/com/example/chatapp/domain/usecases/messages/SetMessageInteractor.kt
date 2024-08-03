package com.example.chatapp.domain.usecases.messages

import com.example.chatapp.data.datasources.models.Message
import com.example.chatapp.data.repositories.messages.MessagesRepository
import javax.inject.Inject

class SetMessageInteractor @Inject constructor(private val messagesRepository:MessagesRepository) {
    suspend operator fun invoke(message: Message) = messagesRepository.sendMessage(message)
}