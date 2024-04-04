package com.marcelo.marvelheroes.data.local.repository

import com.marcelo.marvelheroes.domain.datasource.LocalStorageDataSource
import com.marcelo.marvelheroes.domain.repository.LocalStorageRepository
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Single

@Single
class LocalStorageRepositoryImpl(
    private val localStorageDataSource: LocalStorageDataSource
) : LocalStorageRepository {

    override val sort: Flow<String> get() = localStorageDataSource.sort

    override suspend fun saveSort(sorting: String) = localStorageDataSource.saveSort(sorting)
}