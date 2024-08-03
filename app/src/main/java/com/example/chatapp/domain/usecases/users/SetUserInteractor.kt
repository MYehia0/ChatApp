package com.example.chatapp.domain.usecases.users

import com.example.chatapp.data.datasources.models.User
import com.example.chatapp.data.repositories.users.UsersRepository
import javax.inject.Inject

class SetUserInteractor@Inject constructor(private val usersRepository: UsersRepository) {
    suspend operator fun invoke(user: User) = usersRepository.insertUserToFireStore(user)
}