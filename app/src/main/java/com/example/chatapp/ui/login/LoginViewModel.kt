package com.example.chatapp.ui.login

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.viewModelScope
import com.example.chatapp.data.datasources.models.User
import com.example.chatapp.ui.base.BaseViewModel
import com.example.chatapp.domain.usecases.users.GetUserInteractor
import com.example.chatapp.domain.usecases.users.SignInWithEmailAndPasswordInteractor
import com.example.chatapp.ui.constants.UserProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val getUserInteractor : GetUserInteractor,
    private val signInWithEmailAndPasswordInteractor : SignInWithEmailAndPasswordInteractor): BaseViewModel<LoginNavigator>() {

    var email = ObservableField<String>()
    var password = ObservableField<String>()
    var emailError = ObservableField<String?>()
    var passwordError = ObservableField<String?>()

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

    fun login(){
        if(!validateForm()){
            return
        }
        navigator?.showLoading("Loading...")
        viewModelScope.launch {
            signInWithEmailAndPasswordInteractor(email.get()!!,password.get()!!)
                .addOnCompleteListener {task->
                    if(task.isSuccessful){
                        // massege with firebase
                        viewModelScope.launch {
                            getUserFromDatabase(task.result.user)
                        }
                    }
                    else{
                        // message error
                        navigator?.hideLoading()
                        navigator?.showMessage(task.exception?.localizedMessage!!,"")
                    }
                }
        }
    }

    suspend fun getUserFromDatabase(user: FirebaseUser?){
        user?.uid?.let {
            getUserInteractor(it).addOnCompleteListener { task->
                navigator?.hideLoading()
                if (task.isSuccessful){
                    val userM = task.result.toObject(User::class.java)
                    UserProvider.user = User(
                        uName = userM?.uName,
                        uEmail = userM?.uEmail,
                        uid = userM?.uid,
                        )
                    Log.e("userlogin",userM?.uEmail.toString())
                    Log.e("userloginP",UserProvider.user?.uEmail.toString())
                    navigator?.goToHome()
                } else {
                    // message error
                    navigator?.showMessage(task.exception?.localizedMessage!!,"")
                }
            }
        }
    }

    fun goToRegister(){
        navigator?.goToRegister()
    }
}