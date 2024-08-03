package com.example.chatapp.ui.register

import androidx.databinding.ObservableField
import androidx.lifecycle.viewModelScope
import com.example.chatapp.ui.base.BaseViewModel
import com.example.chatapp.data.datasources.models.User
import com.example.chatapp.domain.usecases.users.CreateUserWithEmailAndPasswordInteractor
import com.example.chatapp.domain.usecases.users.SetUserInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val setUserInteractor : SetUserInteractor,
    private val createUserWithEmailAndPasswordInteractor : CreateUserWithEmailAndPasswordInteractor): BaseViewModel<RegisterNavigator>() {

    var userName = ObservableField<String>()
    var email = ObservableField<String>()
    var password = ObservableField<String>()
    var passwordConform = ObservableField<String>()
    var userNameError = ObservableField<String?>()
    var emailError = ObservableField<String?>()
    var passwordError = ObservableField<String?>()
    var passwordConformError = ObservableField<String?>()

    private var isValid:Boolean = true
    private fun validateForm(): Boolean {
        if(userName.get()?.trim().isNullOrBlank()){
            isValid = false
            userNameError.set("please enter username.")
        }
        else{
            isValid = true
            userNameError.set(null)
        }
        if(email.get()?.trim().isNullOrBlank()){
            isValid = false
            emailError.set("please enter email.")
        }
//        else if(email.get()?.let { android.util.Patterns.EMAIL_ADDRESS.matcher(it).matches() } == true){
//            isValid = true
//            emailError.set("please enter valid email.")
//        }
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
        if(passwordConform.get().isNullOrBlank()){
            isValid = false
            passwordConformError.set("please enter password confirm.")
        }
        else if(!password.get().equals(passwordConform.get())){
            isValid = false
            passwordConformError.set("doesn't match")
        }
        else{
            isValid = true
            passwordConformError.set(null)
        }
        return isValid
    }
    fun register(){
        if(!validateForm()){
            return
        }
        navigator?.showLoading("Loading...")
        viewModelScope.launch {
            createUserWithEmailAndPasswordInteractor(email.get()!!, password.get()!!)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // massege with firebase
                        launch {
                            insertUserToDatebase(task.result.user?.uid)
                        }
                    } else {
                        // message error
                        navigator?.hideLoading()
                        navigator?.showMessage(task.exception?.localizedMessage!!, "")
                    }
                }
        }

    }
    suspend fun insertUserToDatebase(userID:String?){
        val user = User(
            uid = userID,
            uName = userName.get(),
            uEmail = email.get()
        )
        setUserInteractor(user).addOnCompleteListener { task->
            navigator?.hideLoading()
            if(task.isSuccessful){
                navigator?.showMessage("Successful Registration.","Login")
            } else {
                navigator?.showMessage(task.exception?.localizedMessage!!,"")
            }
        }
    }
}