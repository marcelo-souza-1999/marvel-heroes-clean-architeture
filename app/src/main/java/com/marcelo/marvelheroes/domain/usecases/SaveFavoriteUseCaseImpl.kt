package com.marcelo.marvelheroes.domain.usecases

import com.marcelo.marvelheroes.domain.model.HeroesViewData
import com.marcelo.marvelheroes.domain.repository.FavoritesHeroRepository
import com.marcelo.marvelheroes.domain.usecases.SaveFavoriteUseCaseImpl.Companion.GetFavoritesParams
import com.marcelo.marvelheroes.domain.usecases.interfaces.SaveFavoriteUseCase
import com.marcelo.marvelheroes.utils.coroutines.CoroutinesDispatchers
import com.marcelo.marvelheroes.utils.states.ResultStatus
import com.marcelo.marvelheroes.utils.states.ResultStatus.Success
import com.marcelo.marvelheroes.utils.usecase.BaseUseCase
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Single

@Single
class SaveFavoriteUseCaseImpl(
    private val repository: FavoritesHeroRepository,
    private val dispatcher: CoroutinesDispatchers
) : BaseUseCase<GetFavoritesParams, Unit>(), SaveFavoriteUseCase {

    override suspend fun runUseCase(params: GetFavoritesParams): ResultStatus<Unit> {
        return withContext(dispatcher.io()) {
            repository.saveFavorite(
                heroes = HeroesViewData(
                    id = params.heroId,
                    name = params.nameHero,
                    imageUrl = params.imageUrl
                )
            )
            Success(Unit)
        }
    }

    companion object {
        data class GetFavoritesParams(
            val heroId: Int,
            val nameHero: String,
            val imageUrl: String
        )
    }
}