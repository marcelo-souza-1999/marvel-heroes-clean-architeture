package com.marcelo.marvelheroes.presentation.adapters.utils

interface ListItem {

    val key: Long

    fun areItemsTheSame(other: ListItem) = this.key == other.key

    fun areContentsTheSame(other: ListItem) = this == other
}