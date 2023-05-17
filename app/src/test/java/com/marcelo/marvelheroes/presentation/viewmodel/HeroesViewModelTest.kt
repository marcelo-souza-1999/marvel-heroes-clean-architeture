package com.marcelo.marvelheroes.presentation.viewmodel

import androidx.paging.PagingData
import com.marcelo.marvelheroes.domain.usecases.interfaces.GetHeroesUseCase
import com.marcelo.marvelheroes.extensions.emptyString
import com.marcelo.marvelheroes.utils.SetupCoroutines
import com.marcelo.marvelheroes.utils.getHeroesFactory
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import kotlin.test.assertNotNull
import kotlin.test.assertNull

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class HeroesViewModelTest {

    @get:Rule
    var setupCoroutineRule = SetupCoroutines()

    @Mock
    lateinit var getHeroesUseCase: GetHeroesUseCase

    private lateinit var heroesViewModel: HeroesViewModel

    private val pagingDataHeroes = PagingData.from(
        data = listOf(
            getHeroesFactory
        )
    )

    @Before
    fun setup() {
        heroesViewModel = HeroesViewModel(
            getHeroesUseCase = getHeroesUseCase
        )
    }

    @Test
    fun `should validate the paging data object values when calling getPagingHeroes`() =
        runTest {
            whenever(getHeroesUseCase.invoke(any())).thenReturn(
                flowOf(
                    pagingDataHeroes,
                )
            )

            val result = heroesViewModel.getPagingHeroes(emptyString())

            assertNotNull(result.first())
        }

    @Test(expected = RuntimeException::class)
    fun `should return null when there's an error in getPagingHeroes`() =
        runTest {
            whenever(getHeroesUseCase.invoke(any())).thenThrow(RuntimeException())

            val result = heroesViewModel.getPagingHeroes(emptyString())

            assertNull(result.first())
        }
}