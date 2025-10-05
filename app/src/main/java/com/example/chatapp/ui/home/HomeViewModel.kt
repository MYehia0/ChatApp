package com.example.chatapp.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.chatapp.ui.base.BaseViewModel
import com.example.chatapp.data.datasources.models.Room
import com.example.chatapp.data.datasources.models.User
import com.example.chatapp.domain.usecases.rooms.GetRoomInteractor
import com.example.chatapp.ui.constants.UserProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getRoomInteractor : GetRoomInteractor): BaseViewModel<HomeNavigator>() {

    val roomsList = MutableLiveData<List<Room>>()

    fun addRoomFloating(){
        navigator?.goToAddRoom()
    }

    fun logout(){
        UserProvider.user = User()
        navigator?.logout()
    }

    fun getAllRooms(){
        viewModelScope.launch {
            navigator?.showLoading("Loading...")
            getRoomInteractor().addOnCompleteListener { task->
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
}