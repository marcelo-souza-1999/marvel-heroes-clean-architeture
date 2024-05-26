package com.marcelo.marvelheroes.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.paging.PagingData
import com.marcelo.marvelheroes.domain.usecases.GetHeroes
import com.marcelo.marvelheroes.extensions.emptyString
import com.marcelo.marvelheroes.utils.SetupCoroutines
import com.marcelo.marvelheroes.utils.TEXT_SEARCH_KEY
import com.marcelo.marvelheroes.utils.getHeroesFactory
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import kotlin.test.assertNotNull
import kotlin.test.assertNull

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class HeroesViewModelTest {

    @get:Rule
    var setupCoroutineRule = SetupCoroutines()

    private lateinit var getHeroes: GetHeroes

    private lateinit var heroesViewModel: HeroesViewModel

    private val savedStateHandle: SavedStateHandle = mockk()

    private val pagingDataHeroes = PagingData.from(
        data = listOf(
            getHeroesFactory
        )
    )

    @Before
    fun setup() {
        coEvery { savedStateHandle.contains(any()) } returns true
        coEvery { savedStateHandle.get<String>(TEXT_SEARCH_KEY) } returns emptyString()
        getHeroes = mockk()
        heroesViewModel = HeroesViewModel(
            getHeroes = getHeroes,
            savedStateHandle = savedStateHandle
        )
    }

    @Test
    fun `should validate the paging data object values when calling getPagingHeroes`() =
        runTest {

            coEvery { getHeroes.invoke(any(), any()) }
                .returns(flowOf(pagingDataHeroes))

            val result = heroesViewModel.getPagingHeroes()

            assertNotNull(result.first())
        }

    @Test(expected = RuntimeException::class)
    fun `should return null when there's an error in getPagingHeroes`() =
        runTest {

            coEvery { getHeroes.invoke(any(), any()) }
                .throws(RuntimeException())

            val result = heroesViewModel.getPagingHeroes()

            assertNull(result.first())
        }
}