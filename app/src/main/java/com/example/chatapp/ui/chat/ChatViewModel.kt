package com.example.chatapp.ui.chat

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.chatapp.ui.base.BaseViewModel
import com.example.chatapp.data.datasources.models.Message
import com.example.chatapp.data.datasources.models.Room
import com.example.chatapp.domain.usecases.messages.GetMessageInteractor
import com.example.chatapp.domain.usecases.messages.SetMessageInteractor
import com.example.chatapp.ui.constants.UserProvider
import com.google.firebase.Timestamp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val getMessageInteractor : GetMessageInteractor,
    private val setMessageInteractor : SetMessageInteractor): BaseViewModel<ChatNavigator>(){

    var room: Room? = null
    val messageField = ObservableField<String>()
    val messagesList = MutableLiveData<MutableList<Message>?>()
    var mList = mutableListOf<Message>()

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
        viewModelScope.launch {
            setMessageInteractor(message)?.addOnCompleteListener {
                if (it.isSuccessful){
                    messageField.set("")
                } else {
                    navigator?.tryAgain("Failed send your message")
                }
            }
        }
    }

    fun getAllMessage() {
        viewModelScope.launch {
            room?.rID?.let { getMessageInteractor(it) }!!.addSnapshotListener{ value, error ->
                if (error != null) {
                    // message error
                    navigator?.showMessage(error.localizedMessage!!, "")
                }
                value?.documentChanges?.forEach {
                    val message = it.document.toObject(Message::class.java)
                    mList.add(message)
                    messagesList.value = mList
                }
            }
        }
    }
}