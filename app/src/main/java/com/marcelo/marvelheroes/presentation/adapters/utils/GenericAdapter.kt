package com.marcelo.marvelheroes.presentation.adapters.utils

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter

inline fun <T : ListItem, ViewHolder : GenericViewHolder<T>> getGenericAdapterOf(
    crossinline createViewHolder: (ViewGroup) -> ViewHolder
): ListAdapter<T, ViewHolder> {

    val callback = GenericCallbackAdapter<T>()

    return object : ListAdapter<T, ViewHolder>(callback) {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            createViewHolder(parent)

        override fun onBindViewHolder(holder: ViewHolder, position: Int) =
            holder.bind(getItem(position))
    }
}