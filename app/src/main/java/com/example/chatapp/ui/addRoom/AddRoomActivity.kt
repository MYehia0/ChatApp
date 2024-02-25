package com.example.chatapp.ui.addRoom

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.chatapp.R
import com.example.chatapp.base.BaseActivity
import com.example.chatapp.databinding.ActivityAddRoomBinding

class AddRoomActivity : BaseActivity<ActivityAddRoomBinding, AddRoomViewModel>(),AddRoomNavigator {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = viewModel
        binding.base = viewModel
        viewModel.navigator=this
    }

    override fun getLayoutID(): Int {
        return R.layout.activity_add_room
    }

    override fun genViewModel(): AddRoomViewModel {
        return ViewModelProvider(this)[AddRoomViewModel::class.java]
    }
}