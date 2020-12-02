package ru.bimlibik.pixabaygallery.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import ru.bimlibik.pixabaygallery.R
import ru.bimlibik.pixabaygallery.databinding.ItemLoadBinding
import ru.bimlibik.pixabaygallery.databinding.ItemPictureBinding
import ru.bimlibik.pixabaygallery.ui.ItemType.*

class PicturesAdapter : ListAdapter<ItemType, GenericViewHolder>(PictureDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenericViewHolder {
        val binding: ViewDataBinding =
            DataBindingUtil.inflate(LayoutInflater.from(parent.context), viewType, parent, false)

        return when (viewType) {
            R.layout.item_picture -> PictureViewHolder(binding as ItemPictureBinding)
            else -> LoadViewHolder(binding as ItemLoadBinding)
        }
    }

    override fun onBindViewHolder(holder: GenericViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemViewType(position: Int): Int = when (getItem(position)) {
        is PictureType -> R.layout.item_picture
        is LoadType -> R.layout.item_load
        else -> super.getItemViewType(position)
    }


    /**
     * ViewHolder for picture
     */
    inner class PictureViewHolder(private val binding: ItemPictureBinding) :
        GenericViewHolder(binding.root) {

        override fun bind(position: Int) {

        }
    }


    /**
     * ViewHolder for progress bar
     */
    inner class LoadViewHolder(private val binding: ItemLoadBinding) :
        GenericViewHolder(binding.root) {

        override fun bind(position: Int) {

        }
    }
}


/**
 * DiffUtil
 */
class PictureDiffCallback : DiffUtil.ItemCallback<ItemType>() {
    override fun areItemsTheSame(oldItem: ItemType, newItem: ItemType): Boolean {
        return (oldItem is PictureType
                && newItem is PictureType
                && oldItem.picture.id == newItem.picture.id)
    }

    override fun areContentsTheSame(oldItem: ItemType, newItem: ItemType): Boolean {
        return oldItem == newItem
    }
}