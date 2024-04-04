package com.marcelo.marvelheroes.domain.usecases

import com.marcelo.marvelheroes.R
import com.marcelo.marvelheroes.domain.model.FavoriteViewData
import com.marcelo.marvelheroes.domain.model.HeroesViewData
import com.marcelo.marvelheroes.domain.repository.FavoritesHeroRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.core.annotation.Single

@Single
class SaveFavorite(
    private val repository: FavoritesHeroRepository
) {
    suspend operator fun invoke(
        heroId: Int, nameHero: String, imageUrl: String
    ): Flow<FavoriteViewData> = flow {
        val success = try {
            repository.saveFavorite(
                HeroesViewData(
                    id = heroId, name = nameHero, imageUrl = imageUrl
                )
            )
            true
        } catch (e: Exception) {
            false
        }

        val favoriteIcon =
            if (success) R.drawable.ic_favorite_hero else R.drawable.ic_not_favorite_hero

        emit(
            FavoriteViewData(
                favoriteIcon = favoriteIcon
            )
        )
    }
}
