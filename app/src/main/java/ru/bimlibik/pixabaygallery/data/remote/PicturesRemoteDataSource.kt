package ru.bimlibik.pixabaygallery.data.remote

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.bimlibik.pixabaygallery.data.Picture
import ru.bimlibik.pixabaygallery.data.PicturesDataSource
import ru.bimlibik.pixabaygallery.data.Result
import ru.bimlibik.pixabaygallery.data.Result.*

class PicturesRemoteDataSource(
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : PicturesDataSource {

    override suspend fun getPictures(query: String, page: Int): Result<List<Picture>> =
        withContext(ioDispatcher) {
            return@withContext try {
                Success(searchPictures(query, page))
            } catch (e: Exception) {
                Error(e)
            }
        }

    private suspend fun searchPictures(query: String, page: Int): List<Picture> {
        val apiClient = ApiClient.client.create(PictureApiInterface::class.java)
        return apiClient.getPictures(query = query, page = page).pictures.toList()
    }
}