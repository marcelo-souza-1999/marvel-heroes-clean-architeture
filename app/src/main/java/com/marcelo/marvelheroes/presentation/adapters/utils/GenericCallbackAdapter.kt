package com.marcelo.marvelheroes.presentation.adapters.utils

import androidx.recyclerview.widget.DiffUtil

class GenericCallbackAdapter<T : ListItem> : DiffUtil.ItemCallback<T>() {

    override fun areItemsTheSame(oldItem: T, newItem: T) = oldItem.areItemsTheSame(newItem)


    override fun areContentsTheSame(oldItem: T, newItem: T) = oldItem.areContentsTheSame(newItem)
}