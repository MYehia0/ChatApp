package com.example.chatapp.ui

import android.graphics.Color
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.databinding.BindingAdapter
import com.example.chatapp.R.drawable.receive_message_background
import com.example.chatapp.R.drawable.sent_message_background
import com.google.android.material.textfield.TextInputLayout

@BindingAdapter("errorText")
fun bindErrorOnTextInput(textInputLayout:TextInputLayout,error: String?){
    textInputLayout.error = error
}

@BindingAdapter("textID")
fun bindTextByID(textView: TextView,textID: Int?){
    textView.setText(textID!!)
}

@BindingAdapter("imageID")
fun bindImageByID(imageView: ImageView,imageID: Int?){
    imageView.setImageResource(imageID!!)
}

@BindingAdapter("visible")
fun visible(view: View,visible: Boolean){
    view.visibility = if (visible) View.VISIBLE else View.GONE
}

@BindingAdapter("direction")
fun layoutDirection(view: View,visible: Boolean){
    view.layoutDirection = if (visible) View.LAYOUT_DIRECTION_RTL else View.LAYOUT_DIRECTION_LTR
}

@BindingAdapter("changeBackground")
fun changeBackground(view: View,visible: Boolean){
    view.background = if (visible) AppCompatResources.getDrawable(view.context,sent_message_background) 
    else AppCompatResources.getDrawable(view.context,receive_message_background)
}

@BindingAdapter("changeTextColor")
fun changeTextColor(textView: TextView,visible: Boolean){
    if (visible){
        textView.setTextColor(Color.WHITE)
    } else {
        textView.setTextColor(Color.BLACK)
    }
}



