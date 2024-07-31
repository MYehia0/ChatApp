package com.example.chatapp.ui.addRoom

import androidx.databinding.ObservableField
import com.example.chatapp.base.BaseViewModel
import com.example.chatapp.database.FireStoreUtils
import com.example.chatapp.database.models.Room
import com.example.chatapp.ui.constants.UserProvider

class AddRoomViewModel: BaseViewModel<AddRoomNavigator>() {
    var roomName = ObservableField<String>()
    var roomDescription = ObservableField<String>()
    var roomNameError = ObservableField<String?>()
    var roomDescriptionError = ObservableField<String?>()

    var roomCategory:RoomCategories? = RoomCategories.getRoomCategoriesList()[0]
    private var isValid:Boolean = true


    fun createRoom(){
        if(!validateForm()){
            return
        }
        val room = Room(
            rName = roomName.get(),
            rCategoryID = roomCategory?.id,
            rDesc = roomDescription.get(),
            createdBy = UserProvider.user?.uid,
        )
        insertRoomToDatebase(room)
    }

    private fun insertRoomToDatebase(room:Room){
        navigator?.showLoading("Loading...")
        FireStoreUtils()
            .insertRoomToFireStore(room)
            ?.addOnCompleteListener { task->
                navigator?.hideLoading()
                if(task.isSuccessful){
                    // massege with firebase
                    navigator?.showMessage("Room Created Successfully.","Show Rooms")
                }else{
                    // message error
                    navigator?.showMessage(task.exception?.localizedMessage!!,"")
                }

            }
    }

    private fun validateForm(): Boolean {
        if(roomName.get()?.trim().isNullOrBlank()){
            isValid = false
            roomNameError.set("please enter room name.")
        }
        else{
            isValid = true
            roomNameError.set(null)
        }
        if(roomDescription.get()?.trim().isNullOrBlank()){
            isValid = false
            roomDescriptionError.set("please enter room description.")
        }
        else{
            isValid = true
            roomDescriptionError.set(null)
        }
        return isValid
    }

}