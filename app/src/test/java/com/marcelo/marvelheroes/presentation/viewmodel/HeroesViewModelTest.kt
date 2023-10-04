package com.marcelo.marvelheroes.presentation.viewmodel

import androidx.paging.PagingData
import com.marcelo.marvelheroes.domain.usecases.GetHeroesUseCase
import com.marcelo.marvelheroes.extensions.emptyString
import com.marcelo.marvelheroes.utils.SetupCoroutines
import com.marcelo.marvelheroes.utils.getHeroesFactory
import io.mockk.coEvery
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
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

            coEvery { getHeroesUseCase.invoke(any(), any()) }
                .returns(flowOf(pagingDataHeroes))

            val result = heroesViewModel.getPagingHeroes(emptyString())

            assertNotNull(result.first())
        }

    @Test(expected = RuntimeException::class)
    fun `should return null when there's an error in getPagingHeroes`() =
        runTest {

            coEvery { getHeroesUseCase.invoke(any(), any()) }
                .throws(RuntimeException())

            val result = heroesViewModel.getPagingHeroes(emptyString())

            assertNull(result.first())
        }
}