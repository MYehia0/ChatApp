package com.example.chatapp.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.example.chatapp.R
import com.example.chatapp.databinding.ActivityHomeBinding
import com.example.chatapp.base.BaseActivity
import com.example.chatapp.ui.addRoom.AddRoomActivity
import com.example.chatapp.ui.constants.UserProvider
import com.example.chatapp.ui.register.RegisterActivity

class HomeActivity : BaseActivity<ActivityHomeBinding, HomeViewModel>(),HomeNavigator {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        binding.user = UserProvider.user
//        Log.e("user",binding.user?.uEmail.toString())
        binding.vm = viewModel
        binding.base = viewModel
        viewModel.navigator=this
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