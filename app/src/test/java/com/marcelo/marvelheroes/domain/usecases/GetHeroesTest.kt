package com.marcelo.marvelheroes.domain.usecases

import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.marcelo.marvelheroes.domain.model.HeroesViewData
import com.marcelo.marvelheroes.domain.repository.HeroesRepository
import com.marcelo.marvelheroes.extensions.emptyString
import com.marcelo.marvelheroes.utils.PAGING_SIZE
import com.marcelo.marvelheroes.utils.SetupCoroutines
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class GetHeroesTest {

    @get:Rule
    var setupCoroutineRule = SetupCoroutines()

    private lateinit var repository: HeroesRepository

    private lateinit var useCase: GetHeroes

    @Before
    fun setup() {
        repository = mockk()
        useCase = GetHeroes(
            repository = repository
        )
    }

    @Test
    fun `should validate flow paging data creation when invoke from use case is called`() =
        runTest {

            coEvery {
                repository.getCachedHeroes(
                    emptyString(),
                    any()
                )
            } returns flow {
                emit(PagingData.empty())
            }

            val result = useCase.invoke(emptyString(), PagingConfig(PAGING_SIZE))

            result.collect { pagingData ->
                assertEquals(PagingData.empty<HeroesViewData>(), pagingData)
            }
        }

    @Test
    fun `should throw exception when repository fails to provide paging data source`() = runTest {

        val errorMsg = "Failed to get paging data source"
        coEvery { repository.getCachedHeroes(emptyString(), any()) }.throws(
            RuntimeException(
                errorMsg
            )
        )

        val result = runCatching {
            useCase.invoke(emptyString(), PagingConfig(PAGING_SIZE)).first()
        }

        coVerify { repository.getCachedHeroes(emptyString(), any()) }

        val expectedException = Result.failure<Throwable>(
            RuntimeException(errorMsg)
        ).exceptionOrNull()
        val actualException = result.exceptionOrNull()

        assertEquals(expectedException?.message, actualException?.message)
    }

}