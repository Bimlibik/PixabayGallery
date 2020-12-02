package ru.bimlibik.pixabaygallery.ui.pictures

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ru.bimlibik.pixabaygallery.R

object PicturesBindings {

    @BindingAdapter("app:pictures")
    @JvmStatic
    fun setPictures(recycler: RecyclerView, pictures: List<ItemType>?) {
        pictures?.let {
            (recycler.adapter as PicturesAdapter).submitList(pictures)
        }
    }

    @BindingAdapter("app:preview")
    @JvmStatic
    fun setPicture(imageView: ImageView, previewURL: String) {
        Picasso.with(imageView.context)
            .load(previewURL)
            .placeholder(R.drawable.ic_image)
            .resize(150, 150)
            .centerCrop()
            .into(imageView)
    }
}