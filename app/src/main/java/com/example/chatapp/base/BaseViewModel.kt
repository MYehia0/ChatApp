package com.example.chatapp.base

import androidx.lifecycle.ViewModel

open class BaseViewModel<N: BaseNavigator>:ViewModel() {
    var navigator: N?=null
    fun onBack(){
        navigator?.onBack()
    }
}