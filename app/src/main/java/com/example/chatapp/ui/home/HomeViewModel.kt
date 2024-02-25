package com.example.chatapp.ui.home

import com.example.chatapp.base.BaseViewModel

class HomeViewModel: BaseViewModel<HomeNavigator>() {

    fun addRoomFloating(){
        navigator?.goToAddRoom()
    }
}