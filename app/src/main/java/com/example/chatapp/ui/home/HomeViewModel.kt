package com.example.chatapp.ui.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.chatapp.base.BaseViewModel
import com.example.chatapp.database.FireStoreUtils
import com.example.chatapp.database.models.Room

class HomeViewModel: BaseViewModel<HomeNavigator>() {
    val roomsList = MutableLiveData<List<Room>>()

    fun addRoomFloating(){
        navigator?.goToAddRoom()
    }

    fun getAllRooms(){
        navigator?.showLoading("Loading...")
        FireStoreUtils()
            .getRoomFromFireStore()
            .addOnCompleteListener { task->
                navigator?.hideLoading()
                if(task.isSuccessful){
                    val rooms = mutableListOf<Room>()
                    task.result.documents.map {
                        val room = Room(
                            rID = it.data?.get("rid").toString(),
                            rName = it.data?.get("rname").toString(),
                            rDesc = it.data?.get("rdesc").toString(),
                            rCategoryID = it.data?.get("rcategoryID").toString(),
                            createdBy = it.data?.get("createdBy").toString(),
                        )
                        rooms.add(room)
                    }
                    roomsList.value = rooms
                }else{
                    // message error
                    navigator?.showMessage(task.exception?.localizedMessage!!,"")
                }
            }
    }
}