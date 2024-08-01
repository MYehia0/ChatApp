package com.example.chatapp.ui.chat

import android.app.AlertDialog
import com.example.chatapp.base.BaseNavigator

interface ChatNavigator: BaseNavigator {
    fun tryAgain(message:String)
}