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
class DeleteFavoriteUseCaseTest {

    @get:Rule
    var setupCoroutineRule = SetupCoroutines()

    private lateinit var repository: FavoritesHeroRepository

    private lateinit var useCase: DeleteFavoriteUseCase

    @Before
    fun setup() {
        repository = mockk()
        useCase = DeleteFavoriteUseCase(repository)
    }

    @Test
    fun `should return true when hero is deleted successfully`() = runTest {
        coEvery { repository.deleteFavorite(getHeroesFactory.id) } returns Unit

        val result = useCase(getHeroesFactory.id).first()

        assertEquals(true, result)
    }

    @Test
    fun `should return false when an exception occurs during deletion`() = runTest {
        coEvery { repository.deleteFavorite(getHeroesFactory.id) } throws Exception("Deletion error")

        val result = useCase(getHeroesFactory.id).first()

        assertEquals(false, result)
    }
}