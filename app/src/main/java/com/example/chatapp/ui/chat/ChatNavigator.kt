package com.example.chatapp.ui.chat

import com.example.chatapp.ui.base.BaseNavigator

interface ChatNavigator: BaseNavigator {
    fun tryAgain(message:String)
}