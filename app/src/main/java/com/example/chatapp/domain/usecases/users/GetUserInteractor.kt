package com.example.chatapp.domain.usecases.users

import com.example.chatapp.data.repositories.users.UsersRepository
import javax.inject.Inject

class GetUserInteractor@Inject constructor(private val usersRepository: UsersRepository) {
    suspend operator fun invoke(userID: String) = usersRepository.getUserFromFireStore(userID)
}