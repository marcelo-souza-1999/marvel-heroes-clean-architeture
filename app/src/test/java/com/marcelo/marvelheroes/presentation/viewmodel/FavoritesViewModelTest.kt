package com.marcelo.marvelheroes.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.marcelo.marvelheroes.domain.model.FavoriteItemData
import com.marcelo.marvelheroes.domain.usecases.GetHeroesFavorite
import com.marcelo.marvelheroes.presentation.viewmodel.viewstate.ErrorType
import com.marcelo.marvelheroes.presentation.viewmodel.viewstate.State
import com.marcelo.marvelheroes.utils.SetupCoroutines
import com.marcelo.marvelheroes.utils.getHeroesFactory
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import java.net.UnknownHostException

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class FavoritesViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var setupCoroutineRule = SetupCoroutines()

    private lateinit var getHeroesFavorite: GetHeroesFavorite

    private lateinit var viewModel: FavoritesViewModel

    @Before
    fun setup() {
        getHeroesFavorite = mockk()
        viewModel = FavoritesViewModel(
            getHeroesFavorite
        )
    }

    @Test
    fun `should notify viewStateFavorites with Success when get heroes favorites returns success`() =
        runTest {
            val expectedFavorites = listOf(
                FavoriteItemData(id = getHeroesFactory.id, name = getHeroesFactory.name, imageUrl = getHeroesFactory.imageUrl),
                FavoriteItemData(id = getHeroesFactory.id, name = getHeroesFactory.name, imageUrl = getHeroesFactory.imageUrl)
            )

            coEvery { getHeroesFavorite.invoke() } returns flowOf(expectedFavorites)

            viewModel.getHeroesFavorites()

            val expectedViewState = State.Success(expectedFavorites)

            assertEquals(expectedViewState, viewModel.viewStateFavorites.value)
        }

    @Test
    fun `should notify viewStateFavorites with Empty when get heroes favorites returns empty list`() =
        runTest {
            coEvery { getHeroesFavorite.invoke() } returns flowOf(emptyList())

            viewModel.getHeroesFavorites()

            val expectedViewState = State.Success(emptyList<FavoriteItemData>())

            assertEquals(expectedViewState, viewModel.viewStateFavorites.value)
        }

    @Test
    fun `should notify viewStateFavorites with Error when get heroes favorites returns exception`() =
        runTest {
            coEvery { getHeroesFavorite.invoke() } returns flow {
                throw Exception("Simulando uma exceção")
            }

            viewModel.getHeroesFavorites()

            val expectedViewState: State<List<FavoriteItemData>> =
                viewModel.viewStateFavorites.value

            assertTrue(expectedViewState is State.Error)
            assertEquals(ErrorType.GenericError, (expectedViewState as State.Error).errorType)
        }

    @Test
    fun `should notify viewStateFavorites with NetworkError when get heroes favorites returns network error`() =
        runTest {
            coEvery { getHeroesFavorite.invoke() } returns flow {
                throw UnknownHostException("Simulando um erro de rede")
            }

            viewModel.getHeroesFavorites()

            val expectedViewState: State<List<FavoriteItemData>> =
                viewModel.viewStateFavorites.value

            assertTrue(expectedViewState is State.Error)
            assertEquals(ErrorType.NetworkError, (expectedViewState as State.Error).errorType)
        }
}