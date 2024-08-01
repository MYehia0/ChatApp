package com.example.chatapp.ui.home

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.chatapp.R
import com.example.chatapp.database.models.Room
import com.example.chatapp.databinding.ItemRoomBinding

class RoomAdapter(var items:List<Room>?):Adapter<RoomAdapter.RoomViewHolder>() {
    var onItemClickListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(postion: Int, item: Room)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomViewHolder {
        val binding:ItemRoomBinding = DataBindingUtil
            .inflate(LayoutInflater.from(parent.context),
            R.layout.item_room,parent,false)
        return RoomViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RoomViewHolder, position: Int) {
        val item = items?.get(position)
        holder.bind(item!!)
        holder.binding.apply {
            root.setOnClickListener {
                onItemClickListener?.let {
                    it.onItemClick(position, item)
                }
            }
        }
    }

    override fun getItemCount(): Int = items?.size?:0

    fun changeData(newItems:List<Room>?){
        items = newItems
        notifyDataSetChanged()
    }

    class RoomViewHolder(val binding: ItemRoomBinding):ViewHolder(binding.root){
        fun bind(room: Room){
            binding.item = room
            Log.e("", (room.rName?:0).toString())
            Log.e("", (room.rDesc?:0).toString())
            Log.e("", (room.rID?:0).toString())
            Log.e("", (room.rCategoryID?:0).toString())
            Log.e("", (room.createdBy?:0).toString())
        }
    }
}