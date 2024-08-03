package com.example.chatapp.ui.addRoom

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.lifecycle.ViewModelProvider
import com.example.chatapp.R
import com.example.chatapp.ui.base.BaseActivity
import com.example.chatapp.databinding.ActivityAddRoomBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddRoomActivity : BaseActivity<ActivityAddRoomBinding, AddRoomViewModel>(),AddRoomNavigator {
    lateinit var adapter: RoomCategoriesAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = viewModel
        binding.base = viewModel
        viewModel.navigator = this
        initializeAdapter()
        adapter = RoomCategoriesAdapter(RoomCategories.getRoomCategoriesList())
        binding.content.categorySpinner.adapter = adapter
        binding.content.categorySpinner.onItemSelectedListener =
            object :AdapterView.OnItemSelectedListener{
                override fun onNothingSelected(p0: AdapterView<*>?) {

                }
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, itemId: Long) {
                    viewModel.roomCategory = adapter.getItem(position) as RoomCategories
//                    viewModel.roomCategory = RoomCategories.getRoomCategoriesList()[position]
                }
            }
    }
    
    fun initializeAdapter(){

    }

    override fun getLayoutID(): Int {
        return R.layout.activity_add_room
    }

    override fun genViewModel(): AddRoomViewModel {
        return ViewModelProvider(this)[AddRoomViewModel::class.java]
    }
}