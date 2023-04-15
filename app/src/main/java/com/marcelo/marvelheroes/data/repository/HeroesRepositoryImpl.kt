package com.marcelo.marvelheroes.data.repository

import com.marcelo.marvelheroes.data.repository.interfaces.HeroesRemoteDataSource
import com.marcelo.marvelheroes.data.repository.interfaces.HeroesRepository
import com.marcelo.marvelheroes.presentation.pagination.HeroesPagingSource
import org.koin.core.annotation.Single

@Single
class HeroesRepositoryImpl(
    private val remoteDataSource: HeroesRemoteDataSource
) : HeroesRepository{

    override fun getHeroes(query: String) = HeroesPagingSource(remoteDataSource, query)
}