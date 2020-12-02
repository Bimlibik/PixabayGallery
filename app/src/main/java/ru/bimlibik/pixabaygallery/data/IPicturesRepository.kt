package ru.bimlibik.pixabaygallery.data

interface IPicturesRepository {

    suspend fun refreshPictures(query: String): Result<List<Picture>>
}