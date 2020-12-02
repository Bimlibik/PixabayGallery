package ru.bimlibik.pixabaygallery.data

class PicturesRepository(
    private val picturesRemoteDataSource: PicturesDataSource
) : IPicturesRepository {

    override suspend fun refreshPictures(query: String): Result<List<Picture>> =
        updatePicturesFromNetwork(query)

    private suspend fun updatePicturesFromNetwork(query: String) =
        picturesRemoteDataSource.getPictures(query)

    companion object {
        private var instance: PicturesRepository? = null

        fun getInstance(picturesRemoteDataSource: PicturesDataSource) =
            instance ?: synchronized(this) {
                instance ?: PicturesRepository(picturesRemoteDataSource)
            }
    }
}