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

    private val loadedPictures = mutableListOf<Picture>()

    private val _forceUpdate = MutableLiveData(false)
    private val _category = MutableLiveData<String>()
    private val _page = MutableLiveData<Int>()

    private val _triggers =
        MediatorLiveData<Triple<Boolean?, String?, Int?>>().apply {
            addSource(_forceUpdate) { value = Triple(it, _category.value, _page.value) }
            addSource(_category) { value = Triple(_forceUpdate.value, it, _page.value) }
            addSource(_page) { value = Triple(_forceUpdate.value, _category.value, it) }
        }

    private val _pictures: LiveData<List<ItemType>> = _triggers.switchMap { triple ->
        val forceUpdate: Boolean = triple.first ?: false
        val category: String = triple.second ?: DEFAULT_CATEGORY
        val page: Int = triple.third ?: DEFAULT_PAGE
        loadPictures(category, page)
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
        _page.value = null
        _category.value = category
    }

    fun loadMore() {
        var page = DEFAULT_PAGE
        _page.value?.let {
            page = it
            page++
        }

        if (page > MAX_PAGE) {
            _snackbarText.value = Event(R.string.snackbar_end_of_list)
            return
        }
        _page.value = page
    }

    fun setPictureAsWallpaper(largeImageURL: String) {
        _wallpaperEvent.value = Event(largeImageURL)
        _showScrim.value = true
    }

    fun showInfo(info: WorkInfo.State) {
        when (info) {
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
        for (picture in pictures) {
            newItems.add(PictureType(picture))
        }
        return newItems
    }

    private fun addToLoaded(pictures: List<Picture>) {
        if (loadedPictures.isEmpty()) {
            loadedPictures.addAll(pictures)
            return
        }

        for (picture in pictures) {
            if (loadedPictures.contains(picture)) continue
            loadedPictures.add(picture)
        }
    }

    private fun loadPictures(query: String, page: Int): LiveData<List<ItemType>> {
        val result = MutableLiveData<List<ItemType>>()
        viewModelScope.launch {
            val remoteResult = picturesRepository.refreshPictures(query, page)
            if (remoteResult is Success) {
                if (page == DEFAULT_PAGE) loadedPictures.clear()
                addToLoaded(remoteResult.data)
                result.value = convertToItemType(loadedPictures)
            } else {
                result.value = emptyList()
            }
        }
        return result
    }
}

private const val DEFAULT_CATEGORY = ""
private const val DEFAULT_PAGE = 1
private const val MAX_PAGE = 25