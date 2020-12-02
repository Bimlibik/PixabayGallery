package ru.bimlibik.pixabaygallery.data

import com.google.gson.annotations.SerializedName

data class Picture(

    @SerializedName("id")
    val id: Int,

    @SerializedName("previewURL")
    val previewURL: String,

    @SerializedName("largeImageURL")
    val largeImageURL: String
)