package com.marcelo.marvelheroes.domain.usecases

import com.marcelo.marvelheroes.domain.model.HeroesViewData
import com.marcelo.marvelheroes.domain.repository.FavoritesHeroRepository
import com.marcelo.marvelheroes.utils.coroutines.CoroutinesDispatchers
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Single

@Single
class SaveFavoriteUseCase(
    private val repository: FavoritesHeroRepository,
    private val dispatcher: CoroutinesDispatchers
) {
    suspend operator fun invoke(
        heroId: Int,
        nameHero: String,
        imageUrl: String
    ) {
        withContext(dispatcher.io()) {
            repository.saveFavorite(
                HeroesViewData(
                    id = heroId,
                    name = nameHero,
                    imageUrl = imageUrl
                )
            )
        }
    }
}