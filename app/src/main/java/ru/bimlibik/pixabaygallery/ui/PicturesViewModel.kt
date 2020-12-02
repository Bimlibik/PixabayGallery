package ru.bimlibik.pixabaygallery.ui

import androidx.lifecycle.*

class PicturesViewModel : ViewModel() {

    private val _forceUpdate = MutableLiveData(false)
    private val _category = MutableLiveData<String>()

    private val _triggers =
        MediatorLiveData<Pair<Boolean?, String?>>().apply {
            addSource(_forceUpdate) { value = Pair(it, _category.value) }
            addSource(_category) { value = Pair(_forceUpdate.value, it) }
        }

    private val _pictures: LiveData<List<ItemType>> = _triggers.switchMap { pair ->
        if (pair.first == true) {
            TODO("Load pictures from remote data source.")
        }
        MutableLiveData()
    }

    val pictures: LiveData<List<ItemType>> = _pictures

    val empty = Transformations.map(_pictures) {
        it == null || it.isEmpty()
    }



}