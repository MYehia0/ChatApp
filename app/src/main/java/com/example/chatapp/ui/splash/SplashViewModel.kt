package com.example.chatapp.ui.splash

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.viewModelScope
import com.example.chatapp.ui.base.BaseViewModel
import com.example.chatapp.data.datasources.models.User
import com.example.chatapp.domain.usecases.users.GetUserInteractor
import com.example.chatapp.ui.constants.UserProvider
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getUserInteractor : GetUserInteractor,
    private val auth : FirebaseAuth): BaseViewModel<SplashNavigator>() {
    fun checkUser(){
        Handler(Looper.getMainLooper()).postDelayed({
            if(auth.currentUser==null){
                navigator?.goToLogin()
            }
            else{
                viewModelScope.launch {
                    auth.currentUser?.uid?.let {
                        getUserInteractor(it).addOnCompleteListener{
                            if(it.isSuccessful){
                                val user = User(
                                    uid = it.result.data?.get("uid").toString(),
                                    uName = it.result.data?.get("uname").toString(),
                                    uEmail = it.result.data?.get("uemail").toString()
                                )
                //                            val user = it.result.toObject(User::class.java)
                                UserProvider.user = user
                                navigator?.goToHome()
                            }else{
                                navigator?.goToLogin()
                                navigator?.showMessage(it.exception?.localizedMessage!!,"")
                            }
                        }
                    }
                }
            }
        },2000)
    }
}