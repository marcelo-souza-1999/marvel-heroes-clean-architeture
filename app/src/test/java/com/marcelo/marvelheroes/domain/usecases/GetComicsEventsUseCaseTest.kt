package com.marcelo.marvelheroes.domain.usecases

import com.marcelo.marvelheroes.domain.repository.HeroesRepository
import com.marcelo.marvelheroes.utils.SetupCoroutines
import com.marcelo.marvelheroes.utils.getDetailChildFactoryList
import com.marcelo.marvelheroes.utils.getHeroesFactory
import com.marcelo.marvelheroes.utils.states.ResultStatus.Error
import com.marcelo.marvelheroes.utils.states.ResultStatus.Loading
import com.marcelo.marvelheroes.utils.states.ResultStatus.Success
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class GetComicsEventsUseCaseTest {

    @get:Rule
    var setupCoroutineRule = SetupCoroutines()

    private lateinit var repository: HeroesRepository

    private lateinit var useCase: GetComicsEventsUseCase

    @Before
    fun setup() {
        repository = mockk()
        useCase = mockk()
    }


    @Test
    fun `should return Success from ResultStatus when get both requests return success`() =
        runTest {

            coEvery { repository.getComics(getHeroesFactory.id) }
                .returns(getDetailChildFactoryList)

            coEvery { repository.getEvents(getHeroesFactory.id) }
                .returns(getDetailChildFactoryList)

            val result = useCase(getHeroesFactory.id)

            val resultList = result.toList()
            assertEquals(Loading, resultList[0])
            assertTrue(resultList[1] is Success)
        }

    @Test
    fun `should return Error from ResultStatus when get comics request returns error`() =
        runTest {

            coEvery { repository.getComics(getHeroesFactory.id) }
                .throws(Throwable())

            val result = useCase(getHeroesFactory.id)

            val resultList = result.toList()
            assertEquals(Loading, resultList[0])
            assertTrue(resultList[1] is Error)
        }


}