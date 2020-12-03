package ru.bimlibik.pixabaygallery.data

interface IPicturesRepository {

    suspend fun refreshPictures(query: String, page: Int): Result<List<Picture>>
}