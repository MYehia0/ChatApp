package com.example.chatapp.data.datasources.models

import com.example.chatapp.ui.constants.UserProvider
import com.google.firebase.Timestamp
import java.text.SimpleDateFormat

data class Message(
    var id:String?=null,
    val content:String?=null,
    val roomID:String?=null,
    val senderID:String?=null,
    val senderName:String?=null,
    val dateTime:Timestamp?=null,
    val received:Boolean?=false,
    val read:Boolean?=false,
){
    fun formatDate(): String? {
        val date = dateTime?.toDate()
        val dateFormatter = SimpleDateFormat("hh:mm")
        return date?.let { dateFormatter.format(it) }
    }
    fun checkSender(): Boolean {
        return (senderID.equals(UserProvider.user?.uid) && senderName.equals(UserProvider.user?.uName))
    }
}