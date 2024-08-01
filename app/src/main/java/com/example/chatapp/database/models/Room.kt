package com.example.chatapp.database.models

import android.os.Parcelable
import com.example.chatapp.ui.addRoom.RoomCategories
import kotlinx.parcelize.Parcelize

@Parcelize
data class Room(
    var rID:String?=null,
    val rName:String?=null,
    val rCategoryID:String?=null,
    val rDesc:String?=null,
    val createdBy:String?=null,
) : Parcelable {
    fun getRoomCategories(): RoomCategories? {
        return RoomCategories.getRoomCategoryByID(rCategoryID)
    }
}
