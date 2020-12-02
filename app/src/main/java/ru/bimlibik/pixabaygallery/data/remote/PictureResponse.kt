package ru.bimlibik.pixabaygallery.data.remote

import com.google.gson.annotations.SerializedName
import ru.bimlibik.pixabaygallery.data.Picture

data class PictureResponse(

    @SerializedName("hits")
    val pictures: List<Picture>
)