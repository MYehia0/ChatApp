package com.example.chatapp.domain.usecases.users

import com.example.chatapp.data.repositories.users.UsersRepository
import javax.inject.Inject

class CreateUserWithEmailAndPasswordInteractor @Inject constructor(private val usersRepository: UsersRepository) {
    suspend operator fun invoke(email: String, password:String) =
        usersRepository.createUserWithEmailAndPassword(email, password)
}