package com.marcelo.marvelheroes.presentation.adapters.utils

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class GenericViewHolder<T>(
    private val binding: ViewBinding
) : RecyclerView.ViewHolder(binding.root) {

    abstract fun bind(data: T)

}