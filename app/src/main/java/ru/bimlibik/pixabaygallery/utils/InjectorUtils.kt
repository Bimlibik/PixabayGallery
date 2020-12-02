package ru.bimlibik.pixabaygallery.utils

import androidx.savedstate.SavedStateRegistryOwner
import ru.bimlibik.pixabaygallery.ViewModelFactory
import ru.bimlibik.pixabaygallery.data.IPicturesRepository
import ru.bimlibik.pixabaygallery.data.PicturesDataSource
import ru.bimlibik.pixabaygallery.data.PicturesRepository
import ru.bimlibik.pixabaygallery.data.remote.PicturesRemoteDataSource

object InjectorUtils {

    fun provideViewModel(owner: SavedStateRegistryOwner) = ViewModelFactory(
        getPicturesRepository(),
        owner
    )

    private fun getPicturesRepository(): IPicturesRepository =
        PicturesRepository.getInstance(createRemoteDataSource())

    private fun createRemoteDataSource(): PicturesDataSource = PicturesRemoteDataSource()
}