package ru.bimlibik.pixabaygallery

import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import ru.bimlibik.pixabaygallery.data.IPicturesRepository
import ru.bimlibik.pixabaygallery.ui.pictures.PicturesViewModel

@Suppress("UNCHECKED_CAST")
class ViewModelFactory constructor(
    private val picturesRepository: IPicturesRepository,
    owner: SavedStateRegistryOwner,
    defaultArgs: Bundle? = null
) : AbstractSavedStateViewModelFactory(owner, defaultArgs) {

    override fun <T : ViewModel?> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ) = with(modelClass) {
        when {
            isAssignableFrom(PicturesViewModel::class.java) ->
                PicturesViewModel(picturesRepository)

            else ->
                throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    } as T


}