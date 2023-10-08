package com.marcelo.marvelheroes.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.marcelo.marvelheroes.domain.model.DetailChildViewData
import com.marcelo.marvelheroes.domain.model.DetailParentViewData
import com.marcelo.marvelheroes.domain.usecases.GetComicsEventsUseCase
import com.marcelo.marvelheroes.domain.usecases.SaveFavoriteUseCase
import com.marcelo.marvelheroes.utils.SetupCoroutines
import com.marcelo.marvelheroes.utils.getComicsFactory
import com.marcelo.marvelheroes.utils.getEventsFactory
import com.marcelo.marvelheroes.utils.getHeroesFactory
import com.marcelo.marvelheroes.utils.states.ResultStatus
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class DetailsViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var setupCoroutineRule = SetupCoroutines()

    private lateinit var getComicsEventsUseCase: GetComicsEventsUseCase

    private lateinit var saveFavoriteUseCase: SaveFavoriteUseCase

    private lateinit var viewModel: DetailsViewModel

    @Before
    fun setup() {
        getComicsEventsUseCase = mockk()
        saveFavoriteUseCase = mockk()
        viewModel = DetailsViewModel(getComicsEventsUseCase, saveFavoriteUseCase)
    }

    @Test
    fun `should notify viewState with Success from viewState when get heroes details returns success`() =
        runTest {

            val expectedListSuccess = listOf(
                DetailParentViewData(
                    categories = COMICS,
                    detailChildList = listOf(
                        DetailChildViewData(getComicsFactory.id, getComicsFactory.imageUrl)
                    )
                ),
                DetailParentViewData(
                    categories = EVENTS,
                    detailChildList = listOf(
                        DetailChildViewData(getEventsFactory.id, getEventsFactory.imageUrl)
                    )
                )
            )

            coEvery { getComicsEventsUseCase.invoke(any()) } returns flowOf(
                ResultStatus.Success(
                    expectedListSuccess
                )
            )

            viewModel.getHeroesDetails(getHeroesFactory.id)

            val expectedViewStateSuccess =
                DetailsViewModel.Companion.DetailsViewState(success = expectedListSuccess)

            assertEquals(
                TWO,
                expectedListSuccess.size
            )

            assertEquals(
                expectedViewStateSuccess,
                viewModel.viewState.value
            )

            assertEquals(
                COMICS,
                expectedListSuccess[0].categories
            )

            assertEquals(
                EVENTS,
                expectedListSuccess[1].categories
            )
        }

    @Test
    fun `should notify viewState with Success from viewState when get heroes details returns comics at success`() =
        runTest {

            val expectedListSuccess = listOf(
                DetailParentViewData(
                    categories = COMICS,
                    detailChildList = listOf(
                        DetailChildViewData(getComicsFactory.id, getComicsFactory.imageUrl)
                    )
                )
            )

            coEvery { getComicsEventsUseCase.invoke(any()) } returns flowOf(
                ResultStatus.Success(expectedListSuccess)
            )

            viewModel.getHeroesDetails(getHeroesFactory.id)

            val expectedViewStateSuccess =
                DetailsViewModel.Companion.DetailsViewState(success = expectedListSuccess)

            assertEquals(
                ONE,
                expectedListSuccess.size
            )

            assertEquals(
                expectedViewStateSuccess,
                viewModel.viewState.value
            )

            assertEquals(
                COMICS,
                expectedListSuccess[0].categories
            )
        }

    @Test
    fun `should notify viewState with Success from viewState when get heroes details returns events at success`() =
        runTest {

            val expectedListSuccess = listOf(
                DetailParentViewData(
                    categories = EVENTS,
                    detailChildList = listOf(
                        DetailChildViewData(getEventsFactory.id, getEventsFactory.imageUrl)
                    )
                )
            )

            coEvery { getComicsEventsUseCase.invoke(any()) } returns flowOf(
                ResultStatus.Success(
                    expectedListSuccess
                )
            )


            viewModel.getHeroesDetails(getHeroesFactory.id)

            val expectedViewStateSuccess =
                DetailsViewModel.Companion.DetailsViewState(success = expectedListSuccess)

            assertEquals(
                ONE,
                expectedListSuccess.size
            )

            assertEquals(
                expectedViewStateSuccess,
                viewModel.viewState.value
            )

            assertEquals(
                EVENTS,
                expectedListSuccess[0].categories
            )
        }

    @Test
    fun `should notify viewState with Empty from viewState when get heroes details returns empty list`() =
        runTest {

            coEvery { getComicsEventsUseCase.invoke(any()) } returns flowOf(
                ResultStatus.Success(emptyList())
            )

            viewModel.getHeroesDetails(getHeroesFactory.id)

            val expectedViewStateEmpty = DetailsViewModel.Companion.DetailsViewState(empty = true)

            assertEquals(
                expectedViewStateEmpty,
                viewModel.viewState.value
            )
        }

    @Test
    fun `should notify viewState with Error from viewState when get heroes details returns exception`() =
        runTest {

            coEvery { getComicsEventsUseCase.invoke(any()) } returns flowOf(
                ResultStatus.Error(
                    Throwable()
                )
            )

            viewModel.getHeroesDetails(getHeroesFactory.id)

            val expectedViewStateError = DetailsViewModel.Companion.DetailsViewState(error = true)

            assertEquals(
                expectedViewStateError,
                viewModel.viewState.value
            )
        }


    private companion object {
        const val COMICS = "Comics"
        const val EVENTS = "Events"
        const val ONE = 1
        const val TWO = 2
    }
}