package ru.bimlibik.pixabaygallery.data

interface PicturesDataSource {

    suspend fun getPictures(query: String, page: Int): Result<List<Picture>>
}