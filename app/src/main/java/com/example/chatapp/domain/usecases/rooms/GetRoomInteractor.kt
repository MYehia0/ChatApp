package com.example.chatapp.domain.usecases.rooms

import com.example.chatapp.data.repositories.rooms.RoomsRepository
import javax.inject.Inject

class GetRoomInteractor@Inject constructor(private val roomsRepository: RoomsRepository) {
    suspend operator fun invoke() = roomsRepository.getRoomFromFireStore()
}