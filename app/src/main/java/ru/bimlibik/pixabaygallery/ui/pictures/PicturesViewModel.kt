package ru.bimlibik.pixabaygallery.ui.pictures

import androidx.lifecycle.*
import androidx.work.WorkInfo
import kotlinx.coroutines.launch
import ru.bimlibik.pixabaygallery.R
import ru.bimlibik.pixabaygallery.data.IPicturesRepository
import ru.bimlibik.pixabaygallery.data.Picture
import ru.bimlibik.pixabaygallery.data.Result.Success
import ru.bimlibik.pixabaygallery.ui.pictures.ItemType.PictureType
import ru.bimlibik.pixabaygallery.utils.Event

class PicturesViewModel(
    private val picturesRepository: IPicturesRepository
) : ViewModel() {

    private val _forceUpdate = MutableLiveData(false)
    private val _category = MutableLiveData<String>()

    private val _triggers =
        MediatorLiveData<Pair<Boolean?, String?>>().apply {
            addSource(_forceUpdate) { value = Pair(it, _category.value) }
            addSource(_category) { value = Pair(_forceUpdate.value, it) }
        }

    private val _pictures: LiveData<List<ItemType>> = _triggers.switchMap { pair ->
        loadPictures(pair.second)
    }

    val pictures: LiveData<List<ItemType>> = _pictures

    val empty: LiveData<Boolean> = Transformations.map(_pictures) {
        it == null || it.isEmpty()
    }

    private val _showScrim = MutableLiveData(false)
    val showScrim: LiveData<Boolean> = _showScrim

    private val _snackbarText = MutableLiveData<Event<Int>>()
    val snackbarText: LiveData<Event<Int>> = _snackbarText

    private val _wallpaperEvent = MutableLiveData<Event<String>>()
    val wallpaperEvent: LiveData<Event<String>> = _wallpaperEvent

    fun start() {
        _forceUpdate.value = true
    }

    fun searchByCategory(category: String) {
        _category.value = category
    }

    fun setPictureAsWallpaper(largeImageURL: String) {
        _wallpaperEvent.value = Event(largeImageURL)
        _showScrim.value = true
    }

    fun showInfo(info: WorkInfo.State) {
        when(info) {
            WorkInfo.State.SUCCEEDED -> {
                _showScrim.value = false
                _snackbarText.value = Event(R.string.snackbar_success)
            }
            WorkInfo.State.FAILED -> {
                _showScrim.value = false
                _snackbarText.value = Event(R.string.snackbar_error)
            }
            else -> return
        }
    }

    private fun convertToItemType(pictures: List<Picture>): List<ItemType> {
        val newItems = mutableListOf<ItemType>()
        pictures.forEach { picture ->
            newItems.add(PictureType(picture))
        }
        return newItems
    }

    private fun loadPictures(query: String?): LiveData<List<ItemType>> {
        val result = MutableLiveData<List<ItemType>>()

        viewModelScope.launch {
            val remoteResult = picturesRepository.refreshPictures(query ?: "")
            if (remoteResult is Success) {
                result.value = convertToItemType(remoteResult.data)
            } else {
                result.value = emptyList()
            }
        }
        return result
    }
}