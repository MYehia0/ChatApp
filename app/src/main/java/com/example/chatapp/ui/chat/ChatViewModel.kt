package com.example.chatapp.ui.chat

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.example.chatapp.base.BaseViewModel
import com.example.chatapp.database.FireStoreUtils
import com.example.chatapp.database.models.Message
import com.example.chatapp.database.models.Room
import com.example.chatapp.ui.constants.UserProvider
import com.google.firebase.Timestamp
import com.google.firebase.firestore.EventListener

class ChatViewModel: BaseViewModel<ChatNavigator>() {
    var room:Room? = null
    val messageField = ObservableField<String>()
    val messagesList = MutableLiveData<MutableList<Message>>()
    var mList = mutableListOf<Message> ()

    fun sendMessage(){
        if (messageField.get().isNullOrBlank())
            return
        val message = Message(
            content = messageField.get(),
            roomID = room?.rID,
            senderID = UserProvider.user?.uid,
            senderName = UserProvider.user?.uName,
            dateTime = Timestamp.now(),
        )
        FireStoreUtils()
            .sendMessage(message)
            ?.addOnCompleteListener {
                if (it.isSuccessful){
                    messageField.set("")
                    return@addOnCompleteListener
                }
                navigator?.tryAgain("Failed send your message")
            }
    }

    fun getAllMessage(){
//        navigator?.showLoading("Loading...")
        Log.e("getAllMessage","getAllMessage")
        FireStoreUtils()
            .getAllMessage(room?.rID)
            ?.addSnapshotListener(
                EventListener { value, error ->
//                    navigator?.hideLoading()
                    if (error != null) {
                        // message error
                        error.localizedMessage?.let { navigator?.showMessage(it, "") }
                        return@EventListener
                    }
                    value?.documentChanges?.forEach {
                        val message = it.document.toObject(Message::class.java)
                        Log.e("getAllMessage",message.content.toString())
                        mList.add(message)
                        messagesList.value = mList
                        Log.e("mList",mList.toString())
                        Log.e("messagesList",messagesList.value.toString())
                    }
//                        .also {
//                        messagesList.value = mList
//                        Log.e("messagesList",messagesList.value.toString())
//                    }
                }
            )
    }

}