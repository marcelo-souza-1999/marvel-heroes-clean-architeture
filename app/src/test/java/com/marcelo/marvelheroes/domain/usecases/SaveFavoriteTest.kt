package com.marcelo.marvelheroes.domain.usecases

import com.marcelo.marvelheroes.R
import com.marcelo.marvelheroes.domain.repository.FavoritesHeroRepository
import com.marcelo.marvelheroes.utils.SetupCoroutines
import com.marcelo.marvelheroes.utils.getHeroesFactory
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class SaveFavoriteTest {

    @get:Rule
    var setupCoroutineRule = SetupCoroutines()

    private lateinit var repository: FavoritesHeroRepository

    private lateinit var useCase: SaveFavorite

    @Before
    fun setup() {
        repository = mockk()
        useCase = SaveFavorite(repository)
    }

    @Test
    fun `should return FavoriteViewData with success icon when hero is saved successfully`() =
        runTest {

            coEvery { repository.saveFavorite(any()) } returns Unit

            val result = useCase(
                heroId = getHeroesFactory.id,
                nameHero = getHeroesFactory.name,
                imageUrl = getHeroesFactory.imageUrl
            ).toList().first()

            assertEquals(R.drawable.ic_favorite_hero, result.favoriteIcon)
        }

    @Test
    fun `should return FavoriteViewData with failure icon when an exception occurs during saving`() =
        runTest {
            coEvery { repository.saveFavorite(any()) } throws Exception("Save error")

            val result = useCase(
                heroId = getHeroesFactory.id,
                nameHero = getHeroesFactory.name,
                imageUrl = getHeroesFactory.imageUrl
            ).toList().first()

            assertEquals(R.drawable.ic_not_favorite_hero, result.favoriteIcon)
        }

}