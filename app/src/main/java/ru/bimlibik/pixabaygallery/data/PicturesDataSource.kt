package ru.bimlibik.pixabaygallery.data

interface PicturesDataSource {

    suspend fun getPictures(query: String): Result<List<Picture>>
}