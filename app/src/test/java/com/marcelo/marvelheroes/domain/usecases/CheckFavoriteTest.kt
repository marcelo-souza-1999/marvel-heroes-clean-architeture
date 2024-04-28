package com.marcelo.marvelheroes.domain.usecases

import com.marcelo.marvelheroes.domain.repository.FavoritesHeroRepository
import com.marcelo.marvelheroes.utils.SetupCoroutines
import com.marcelo.marvelheroes.utils.getHeroesFactory
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class CheckFavoriteTest {

    @get:Rule
    var setupCoroutineRule = SetupCoroutines()

    private lateinit var repository: FavoritesHeroRepository

    private lateinit var useCase: CheckFavorite

    @Before
    fun setup() {
        repository = mockk()
        useCase = CheckFavorite(repository)
    }

    @Test
    fun `should return true when hero is favorite`() = runTest {

        coEvery { repository.isFavorite(getHeroesFactory.id) } returns true

        val result = useCase(getHeroesFactory.id).first()

        assertEquals(true, result)
    }

    @Test
    fun `should return false when hero is not favorite`() = runTest {

        coEvery { repository.isFavorite(getHeroesFactory.id) } returns false

        val result = useCase(getHeroesFactory.id).first()

        assertEquals(false, result)
    }

}