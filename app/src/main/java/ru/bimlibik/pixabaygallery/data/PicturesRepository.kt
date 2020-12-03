package ru.bimlibik.pixabaygallery.data

class PicturesRepository(
    private val picturesRemoteDataSource: PicturesDataSource
) : IPicturesRepository {

    override suspend fun refreshPictures(query: String, page: Int): Result<List<Picture>> =
        updatePicturesFromNetwork(query, page)

    private suspend fun updatePicturesFromNetwork(query: String, page: Int) =
        picturesRemoteDataSource.getPictures(query, page)

    companion object {
        private var instance: PicturesRepository? = null

        fun getInstance(picturesRemoteDataSource: PicturesDataSource) =
            instance ?: synchronized(this) {
                instance ?: PicturesRepository(picturesRemoteDataSource)
            }
    }
}