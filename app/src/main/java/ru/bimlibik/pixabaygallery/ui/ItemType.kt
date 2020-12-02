package ru.bimlibik.pixabaygallery.ui

import ru.bimlibik.pixabaygallery.data.Picture

sealed class ItemType {

    data class PictureType(val picture: Picture) : ItemType()
    object LoadType : ItemType()
}