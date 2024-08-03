package com.example.chatapp.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.chatapp.R
import com.example.chatapp.data.datasources.models.Room
import com.example.chatapp.databinding.ActivityHomeBinding
import com.example.chatapp.ui.base.BaseActivity
import com.example.chatapp.ui.addRoom.AddRoomActivity
import com.example.chatapp.ui.chat.ChatActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : BaseActivity<ActivityHomeBinding, HomeViewModel>(),HomeNavigator {
    lateinit var adapter: RoomAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        binding.user = UserProvider.user
//        Log.e("user",binding.user?.uEmail.toString())
        binding.vm = viewModel
        binding.base = viewModel
        viewModel.navigator = this
        initailizeAdapter()
        subscribeToLiveData()
    }

    override fun onStart() {
        super.onStart()
        viewModel.getAllRooms()
    }

    fun initailizeAdapter(){
        adapter = RoomAdapter(null)
        binding.content.roomRecycler.adapter = adapter
        adapter.onItemClickListener = object: RoomAdapter.OnItemClickListener{
            override fun onItemClick(postion: Int, item: Room) {
                val intent = Intent(this@HomeActivity, ChatActivity::class.java)
                intent.putExtra("ROOM", item)
                startActivity(intent)
            }

        }
    }

    fun subscribeToLiveData(){
        viewModel.roomsList.observe(this){
            adapter.changeData(it)
        }
    }

    override fun getLayoutID(): Int {
        return R.layout.activity_home
    }


    override fun genViewModel(): HomeViewModel {
        return ViewModelProvider(this)[HomeViewModel::class.java]
    }

    override fun goToAddRoom() {
        binding.addRoomFloating.setOnClickListener{
            val intent = Intent(this, AddRoomActivity::class.java)
            startActivity(intent)
        }
    }
}