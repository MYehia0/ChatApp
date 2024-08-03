package com.example.chatapp.ui.chat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.chatapp.R
import com.example.chatapp.data.datasources.models.Message
import com.example.chatapp.databinding.ItemMessageBinding

class MessageAdapter(var items:MutableList<Message>?):Adapter<MessageAdapter.MessageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val binding:ItemMessageBinding = DataBindingUtil
            .inflate(LayoutInflater.from(parent.context),
            R.layout.item_message,parent,false)
        return MessageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val item = items?.get(position)
        holder.bind(item!!)
    }

    override fun getItemCount(): Int = items?.size?:0

    fun changeData(message:Message?){
        if (message != null) {
            items?.add(message)
        }
        items?.let { notifyItemInserted(it.size) }
    }

    fun changeAllData(newItems: MutableList<Message>){
        items = newItems
        notifyDataSetChanged()
    }

    class MessageViewHolder(val binding: ItemMessageBinding):ViewHolder(binding.root){
        fun bind(message: Message){
            binding.item = message
        }
    }
}