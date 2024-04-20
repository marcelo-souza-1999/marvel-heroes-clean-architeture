package com.marcelo.marvelheroes.domain.datasource

import kotlinx.coroutines.flow.Flow

interface LocalStorageDataSource {

    val sort: Flow<String>

    suspend fun saveSort(sorting: String)
}