package com.marcelo.marvelheroes.domain.usecases

import com.marcelo.marvelheroes.data.repository.interfaces.HeroesRepository
import com.marcelo.marvelheroes.domain.model.ComicsViewData
import com.marcelo.marvelheroes.domain.usecases.GetComicsUseCaseImpl.Companion.GetComicsParams
import com.marcelo.marvelheroes.domain.usecases.interfaces.GetComicsUseCase
import com.marcelo.marvelheroes.utils.states.ResultStatus
import com.marcelo.marvelheroes.utils.usecase.BaseUseCase
import org.koin.core.annotation.Single

@Single
class GetComicsUseCaseImpl(
    private val repository: HeroesRepository
) : BaseUseCase<GetComicsParams, List<ComicsViewData>>(), GetComicsUseCase {

    override suspend fun runUseCase(params: GetComicsParams): ResultStatus<List<ComicsViewData>> {
        val comics = repository.getComics(params.heroeId)
        return ResultStatus.Success(comics)
    }

    companion object {
        data class GetComicsParams(val heroeId: Int)
    }
}