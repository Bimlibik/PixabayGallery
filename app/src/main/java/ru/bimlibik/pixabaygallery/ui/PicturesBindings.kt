package ru.bimlibik.pixabaygallery.ui

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView

object PicturesBindings {

    @BindingAdapter("app:pictures")
    @JvmStatic
    fun setPictures(recycler: RecyclerView, pictures: List<ItemType>?) {
        pictures?.let {
            (recycler.adapter as PicturesAdapter).submitList(pictures)
        }
    }
}