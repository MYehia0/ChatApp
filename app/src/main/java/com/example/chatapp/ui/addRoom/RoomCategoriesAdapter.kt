package com.example.chatapp.ui.addRoom

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.databinding.DataBindingUtil
import com.example.chatapp.R
import com.example.chatapp.databinding.ItemRoomCategoriesBinding

class RoomCategoriesAdapter(val categories : List<RoomCategories>): BaseAdapter() {
    override fun getCount(): Int = categories.size

    override fun getItem(position: Int): RoomCategories {
        return categories[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, view: View?, parent: ViewGroup?): View {
        var currentView = view
        var viewHolder:CategoriesViewHolder
        if(currentView == null ){
            val binding:ItemRoomCategoriesBinding =
                DataBindingUtil.inflate(LayoutInflater.from(parent?.context),
                R.layout.item_room_categories,
                parent,false)
            viewHolder = CategoriesViewHolder(binding)
            currentView = viewHolder.binding.root
            currentView.tag = viewHolder
        }else{
            viewHolder = currentView.tag as CategoriesViewHolder
        }
        viewHolder.binding.item = categories[position]
        viewHolder.binding.invalidateAll()
        return currentView
    }

    class CategoriesViewHolder(val binding: ItemRoomCategoriesBinding)

}