package com.example.chatapp.ui.addRoom

import com.example.chatapp.R
import com.google.android.gms.common.util.CollectionUtils.listOf

data class RoomCategories(
    val id:String,
    val nameId:Int,
    val imageId:Int){
    companion object{
        fun getRoomCategoryByID(id:String?): RoomCategories? {
            return when (id) {
                "sports" -> {
                    getRoomCategoriesList()[0]
                }
                "movies" -> {
                    getRoomCategoriesList()[1]
                }
                "music" -> {
                    getRoomCategoriesList()[2]
                }
                else -> {
                    null
                }
            }
        }
        fun getRoomCategoriesList() = listOf (
            RoomCategories("sports", R.string.sports,R.drawable.sports),
            RoomCategories("movies",R.string.movies,R.drawable.movies),
            RoomCategories("music",R.string.music,R.drawable.music)
        )
    }
}

