package ru.bimlibik.pixabaygallery.ui.pictures

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import ru.bimlibik.pixabaygallery.R
import ru.bimlibik.pixabaygallery.databinding.ItemLoadBinding
import ru.bimlibik.pixabaygallery.databinding.ItemPictureBinding
import ru.bimlibik.pixabaygallery.ui.pictures.ItemType.*

class PicturesAdapter(
    private val viewModel: PicturesViewModel
) : ListAdapter<ItemType, GenericViewHolder>(PictureDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenericViewHolder {
        val binding: ViewDataBinding =
            DataBindingUtil.inflate(LayoutInflater.from(parent.context), viewType, parent, false)

        return when (viewType) {
            R.layout.item_picture -> PictureViewHolder(binding as ItemPictureBinding)
            else -> LoadViewHolder(binding as ItemLoadBinding)
        }
    }

    override fun onBindViewHolder(holder: GenericViewHolder, position: Int) {
        if (position >= itemCount - 1) {
            viewModel.loadMore()
        }
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
            val item = getItem(position)
            binding.item = (item as PictureType).picture
            binding.viewModel = viewModel
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