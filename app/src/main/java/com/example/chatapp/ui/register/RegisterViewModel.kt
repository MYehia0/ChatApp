package com.example.chatapp.ui.register

import android.database.Observable
import androidx.core.text.trimmedLength
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.example.chatapp.base.BaseViewModel
import com.example.chatapp.database.FireStoreUtils
import com.example.chatapp.database.models.User
import com.google.firebase.auth.FirebaseAuth

class RegisterViewModel: BaseViewModel<RegisterNavigator>() {
    var userName = ObservableField<String>()
    var email = ObservableField<String>()
    var password = ObservableField<String>()
    var passwordConform = ObservableField<String>()
    var userNameError = ObservableField<String?>()
    var emailError = ObservableField<String?>()
    var passwordError = ObservableField<String?>()
    var passwordConformError = ObservableField<String?>()

    val auth = FirebaseAuth.getInstance()
    private var isValid:Boolean = true

    fun register(){
        if(!validateForm()){
            return
        }
        navigator?.showLoading("Loading...")
        auth.createUserWithEmailAndPassword(email.get()!!,password.get()!!)
            .addOnCompleteListener {task->
                if(task.isSuccessful){
                    // massege with firebase
                    insertUserToDatebase(task.result.user?.uid)
                }
                else{
                    // message error
                    navigator?.hideLoading()
                    navigator?.showMessage(task.exception?.localizedMessage!!,"")
                }
            }

    }
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
    fun insertUserToDatebase(userID:String?){
        val user = User(
            uid = userID,
            uName = userName.get(),
            uEmail = email.get()
        )
        FireStoreUtils()
            .insertUserToFireStore(user)
            .addOnCompleteListener { task->
                navigator?.hideLoading()
                if(task.isSuccessful){
                    navigator?.showMessage("Successful Registration.","Login")
//                    onBack()
                }else{
                    navigator?.showMessage(task.exception?.localizedMessage!!,"")
                }

            }
    }


}