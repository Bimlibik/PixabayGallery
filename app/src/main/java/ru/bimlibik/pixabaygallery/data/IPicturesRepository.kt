package ru.bimlibik.pixabaygallery.data

interface IPicturesRepository {

    suspend fun refreshCurrencies(query: String): Result<List<Picture>>
}