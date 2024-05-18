package com.marcelo.marvelheroes.domain.repository

import kotlinx.coroutines.flow.Flow

interface LocalStorageRepository {

    val sort: Flow<String>

    suspend fun saveSort(sorting: String)
}