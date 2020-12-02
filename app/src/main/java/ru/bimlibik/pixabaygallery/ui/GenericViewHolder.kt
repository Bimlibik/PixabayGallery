package ru.bimlibik.pixabaygallery.ui

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class GenericViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    abstract fun bind(position: Int)
}