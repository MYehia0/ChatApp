package com.example.chatapp.ui

import android.view.View
import android.widget.ImageButton
import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout

@BindingAdapter("errorText")
fun bindErrorOnTextInput(textInputLayout:TextInputLayout,error: String?){
    textInputLayout.error = error
}

@BindingAdapter("visible")
fun visible(view: View,visible: Boolean){
    view.visibility = if (visible) View.VISIBLE else View.GONE
}