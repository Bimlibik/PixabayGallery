package ru.bimlibik.pixabaygallery.data.remote

import retrofit2.http.GET
import retrofit2.http.Query
import ru.bimlibik.pixabaygallery.BuildConfig


interface PictureApiInterface {

    @GET("?")
    suspend fun getPictures(
        @Query("key") apiKey: String = BuildConfig.PIXABAY_API_KEY,
        @Query("q") query: String,
        @Query("image_type") imageType: String = "photo"
    ): PictureResponse
}