package com.example.chatapp.domain.usecases.rooms

import com.example.chatapp.data.datasources.models.Room
import com.example.chatapp.data.repositories.rooms.RoomsRepository
import javax.inject.Inject

class SetRoomInteractor@Inject constructor(private val roomsRepository: RoomsRepository) {
    suspend operator fun invoke(room: Room) = roomsRepository.insertRoomToFireStore(room)
}