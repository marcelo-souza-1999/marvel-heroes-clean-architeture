package com.marcelo.marvelheroes.domain.usecases

import com.marcelo.marvelheroes.domain.repository.HeroesRepository
import com.marcelo.marvelheroes.domain.usecases.GetComicsEventsEventsUseCaseImpl.Companion.GetComicsEventsParams
import com.marcelo.marvelheroes.domain.usecases.interfaces.GetComicsEventsUseCase
import com.marcelo.marvelheroes.utils.SetupCoroutines
import com.marcelo.marvelheroes.utils.getComicsFactoryList
import com.marcelo.marvelheroes.utils.getEventsFactoryList
import com.marcelo.marvelheroes.utils.getHeroesFactory
import com.marcelo.marvelheroes.utils.states.ResultStatus.Error
import com.marcelo.marvelheroes.utils.states.ResultStatus.Loading
import com.marcelo.marvelheroes.utils.states.ResultStatus.Success
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class GetComicsEventsEventsUseCaseImplTest {

    @get:Rule
    var setupCoroutineRule = SetupCoroutines()

    private lateinit var repository: HeroesRepository

    private lateinit var useCase: GetComicsEventsUseCase

    @Before
    fun setup() {
        repository = mock()
        useCase = GetComicsEventsEventsUseCaseImpl(
            repository = repository,
            dispatcher = setupCoroutineRule.testDispatcherProvider
        )
    }


    @Test
    fun `should return Success from ResultStatus when get both requests return success`() =
        runTest {

            whenever(repository.getComics(getHeroesFactory.id))
                .thenReturn(
                    getComicsFactoryList
                )

            whenever(repository.getEvents(getHeroesFactory.id))
                .thenReturn(
                    getEventsFactoryList
                )

            val result = useCase
                .invoke(
                    GetComicsEventsParams(
                        heroeId = getHeroesFactory.id
                    )
                )

            val resultList = result.toList()
            assertEquals(Loading, resultList[0])
            assertTrue(resultList[1] is Success)
        }

    @Test
    fun `should return Error from ResultStatus when get events request returns error`() =
        runTest {

            whenever(repository.getEvents(getHeroesFactory.id))
                .thenAnswer { throw Throwable() }

            val result = useCase
                .invoke(
                    GetComicsEventsParams(
                        heroeId = getHeroesFactory.id
                    )
                )

            val resultList = result.toList()
            assertEquals(Loading, resultList[0])
            assertTrue(resultList[1] is Error)
        }

    @Test
    fun `should return Error from ResultStatus when get comics request returns error`() =
        runTest {

            whenever(repository.getComics(getHeroesFactory.id))
                .thenAnswer { throw Throwable() }

            val result = useCase
                .invoke(
                    GetComicsEventsParams(
                        heroeId = getHeroesFactory.id
                    )
                )

            val resultList = result.toList()
            assertEquals(Loading, resultList[0])
            assertTrue(resultList[1] is Error)
        }
}