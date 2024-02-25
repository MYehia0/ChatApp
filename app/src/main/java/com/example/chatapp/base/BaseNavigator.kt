package com.example.chatapp.base

interface BaseNavigator {
    fun showLoading(message:String)
    fun showMessage(messageOK: String , message: String)
    fun hideLoading()
    fun onBack()
}