package com.example.chatapp.ui.splash

import android.os.Handler
import android.os.Looper
import com.example.chatapp.base.BaseViewModel
import com.example.chatapp.database.FireStoreUtils
import com.example.chatapp.database.models.User
import com.example.chatapp.ui.constants.UserProvider
import com.google.firebase.auth.FirebaseAuth

class SplashViewModel:BaseViewModel<SplashNavigator>() {
    private val auth = FirebaseAuth.getInstance()

    fun checkUser(){

        Handler(Looper.getMainLooper()).postDelayed({
            if(auth.currentUser==null){
                navigator?.goToLogin()
            }
            else{
                FireStoreUtils()
                    .getUserFromFireStore(auth.currentUser?.uid)
                    .addOnCompleteListener{
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
        },2000)
    }


}