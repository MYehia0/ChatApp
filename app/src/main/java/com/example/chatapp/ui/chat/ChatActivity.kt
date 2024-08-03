package com.example.chatapp.ui.chat

import android.app.AlertDialog
import android.os.Build
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chatapp.R
import com.example.chatapp.data.datasources.models.Room
import com.example.chatapp.ui.base.BaseActivity
import com.example.chatapp.databinding.ActivityChatBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChatActivity : BaseActivity<ActivityChatBinding, ChatViewModel>(), ChatNavigator {

    private lateinit var adapter: MessageAdapter
    private lateinit var layoutManager: LinearLayoutManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.base = viewModel
        binding.vm = viewModel
        viewModel.navigator = this
        initializeRoom()
        initailizeAdapter()
        subscribeToLiveData()
        viewModel.getAllMessage()
    }

    private fun subscribeToLiveData(){
        viewModel.messagesList.observe(this){
            if (it != null) {
                adapter.changeAllData(it)
            }
            binding.content.recyclerMessages.scrollToPosition(adapter.itemCount - 1 )
        }
    }

    private fun initializeRoom(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            viewModel.room = intent.getParcelableExtra("ROOM", Room::class.java)!!
        } else {
            viewModel.room = intent.getParcelableExtra("ROOM")!!
        }
    }

    private fun initailizeAdapter(){
        layoutManager = LinearLayoutManager(this)
        layoutManager.stackFromEnd = true
        adapter = MessageAdapter(null)
        binding.content.recyclerMessages.adapter = adapter
        binding.content.recyclerMessages.layoutManager = layoutManager
    }

    override fun getLayoutID(): Int = R.layout.activity_chat

    override fun genViewModel(): ChatViewModel {
        return ViewModelProvider(this)[ChatViewModel::class.java]
    }

    override fun tryAgain(message: String) {
        alertDialog = AlertDialog.Builder(this)
            .setMessage(message)
            .setPositiveButton("try again") { dialog, _ ->
                viewModel.sendMessage()
                dialog?.dismiss()
            }.show()
        return
    }

}