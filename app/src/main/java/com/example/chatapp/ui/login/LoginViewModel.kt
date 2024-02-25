package com.example.chatapp.ui.login

import android.util.Log
import androidx.databinding.ObservableField
import com.example.chatapp.base.BaseViewModel
import com.example.chatapp.ui.constants.UserProvider
import com.example.chatapp.database.FireStoreUtils
import com.example.chatapp.database.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LoginViewModel: BaseViewModel<LoginNavigator>() {
    var email = ObservableField<String>()
    var password = ObservableField<String>()
    var emailError = ObservableField<String?>()
    var passwordError = ObservableField<String?>()

    val auth = FirebaseAuth.getInstance()
    fun login(){
        if(!validateForm()){
            return
        }
        navigator?.showLoading("Loading...")
        auth.signInWithEmailAndPassword(email.get()!!,password.get()!!)
            .addOnCompleteListener {task->
                if(task.isSuccessful){
                    // massege with firebase
                    getUserFromDatabase(task.result.user)
                }
                else{
                    // message error
                    navigator?.hideLoading()
                    navigator?.showMessage(task.exception?.localizedMessage!!,"")
                }
            }
    }

    private var isValid = true
    fun validateForm(): Boolean {
        if(email.get()?.trim().isNullOrBlank()){
            isValid = false
            emailError.set("please enter email.")
        }
        else{
            isValid = true
            emailError.set(null)
        }
        if(password.get().isNullOrBlank()){
            isValid = false
            passwordError.set("please enter password.")
        }
        else{
            isValid = true
            passwordError.set(null)
        }
        return isValid
    }

    fun getUserFromDatabase(user: FirebaseUser?){
        FireStoreUtils()
            .getUserFromFireStore(user?.uid)
            .addOnCompleteListener {task->
                navigator?.hideLoading()
                if (task.isSuccessful){
//                    val userM = task.result.toObject(User::class.java)
//                    UserProvider.user = userM
//                    Log.e("userlogin",userM?.uEmail.toString())
//                    Log.e("userloginP",UserProvider.user?.uEmail.toString())
                    navigator?.goToHome()
                }
                else{
                    // message error
                    navigator?.showMessage(task.exception?.localizedMessage!!,"")
                }
            }
    }

    fun goToRegister(){
        navigator?.goToRegister()
    }

}