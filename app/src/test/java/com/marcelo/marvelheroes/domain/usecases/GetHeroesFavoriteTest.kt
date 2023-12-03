package com.marcelo.marvelheroes.domain.usecases

import com.marcelo.marvelheroes.domain.model.FavoriteItemData
import com.marcelo.marvelheroes.domain.model.HeroesViewData
import com.marcelo.marvelheroes.domain.repository.FavoritesHeroRepository
import com.marcelo.marvelheroes.utils.SetupCoroutines
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class GetHeroesFavoriteTest {

    @get:Rule
    var setupCoroutineRule = SetupCoroutines()

    private lateinit var repository: FavoritesHeroRepository

    private lateinit var useCase: GetHeroesFavorite

    @Before
    fun setup() {
        repository = mockk()
        useCase = GetHeroesFavorite(repository)
    }

    @Test
    fun `should return list of FavoriteItemData`() = runTest {

        val favoriteHeroes = listOf(
            HeroesViewData(1, "Iron Man", "ironman.jpg"),
            HeroesViewData(2, "Spider-Man", "spiderman.jpg")
        )

        coEvery { repository.getAllFavorites() } returns flowOf(favoriteHeroes)

        val result = useCase().toList().flatten()

        val mappedFavoriteItems = favoriteHeroes.map {
            FavoriteItemData(it.id, it.name, it.imageUrl)
        }

        assertEquals(mappedFavoriteItems, result)
    }
}