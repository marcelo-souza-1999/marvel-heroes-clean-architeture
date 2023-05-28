package com.marcelo.marvelheroes.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.marcelo.marvelheroes.domain.model.ComicsViewData
import com.marcelo.marvelheroes.domain.model.DetailChildViewData
import com.marcelo.marvelheroes.domain.model.DetailParentViewData
import com.marcelo.marvelheroes.domain.usecases.interfaces.GetComicsEventsUseCase
import com.marcelo.marvelheroes.presentation.viewmodel.DetailsViewModel.Companion.DetailsViewModelState.Empty
import com.marcelo.marvelheroes.presentation.viewmodel.DetailsViewModel.Companion.DetailsViewModelState.Error
import com.marcelo.marvelheroes.presentation.viewmodel.DetailsViewModel.Companion.DetailsViewModelState.Success
import com.marcelo.marvelheroes.utils.SetupCoroutines
import com.marcelo.marvelheroes.utils.getComicsFactory
import com.marcelo.marvelheroes.utils.getComicsFactoryList
import com.marcelo.marvelheroes.utils.getEventsFactory
import com.marcelo.marvelheroes.utils.getEventsFactoryList
import com.marcelo.marvelheroes.utils.getHeroesFactory
import com.marcelo.marvelheroes.utils.states.ResultStatus
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class DetailsViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var setupCoroutineRule = SetupCoroutines()

    @Mock
    lateinit var getComicsEventsUseCase: GetComicsEventsUseCase

    private lateinit var viewModel: DetailsViewModel

    @Before
    fun setup() {
        viewModel = DetailsViewModel(getComicsEventsUseCase)
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

            whenever(getComicsEventsUseCase.invoke(any()))
                .thenReturn(
                    flowOf(
                        ResultStatus.Success(
                            getComicsFactoryList to getEventsFactoryList
                        )
                    )
                )

            viewModel.getHeroesDetails(getHeroesFactory.id)

            assertEquals(
                TWO,
                expectedListSuccess.size
            )

            assertEquals(
                Success(expectedListSuccess),
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

            whenever(getComicsEventsUseCase.invoke(any()))
                .thenReturn(
                    flowOf(
                        ResultStatus.Success(
                            getComicsFactoryList to emptyList()
                        )
                    )
                )

            viewModel.getHeroesDetails(getHeroesFactory.id)

            assertEquals(
                ONE,
                expectedListSuccess.size
            )

            assertEquals(
                Success(expectedListSuccess),
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

            whenever(getComicsEventsUseCase.invoke(any()))
                .thenReturn(
                    flowOf(
                        ResultStatus.Success(
                            emptyList<ComicsViewData>() to getEventsFactoryList
                        )
                    )
                )

            viewModel.getHeroesDetails(getHeroesFactory.id)

            assertEquals(
                ONE,
                expectedListSuccess.size
            )

            assertEquals(
                Success(expectedListSuccess),
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

            whenever(getComicsEventsUseCase.invoke(any()))
                .thenReturn(
                    flowOf(
                        ResultStatus.Success(
                            emptyList<ComicsViewData>() to emptyList()
                        )
                    )
                )

            viewModel.getHeroesDetails(getHeroesFactory.id)

            assertEquals(
                Empty,
                viewModel.viewState.value
            )
        }

    @Test
    fun `should notify viewState with Error from viewState when get heroes details returns exception`() =
        runTest {

            whenever(getComicsEventsUseCase.invoke(any()))
                .thenReturn(
                    flowOf(
                        ResultStatus.Error(
                            Throwable()
                        )
                    )
                )

            viewModel.getHeroesDetails(getHeroesFactory.id)

            assertEquals(
                Error,
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