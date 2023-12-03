package com.marcelo.marvelheroes.domain.usecases

import androidx.paging.PagingConfig
import com.marcelo.marvelheroes.domain.repository.HeroesRepository
import com.marcelo.marvelheroes.extensions.emptyString
import com.marcelo.marvelheroes.utils.PAGING_SIZE
import com.marcelo.marvelheroes.utils.SetupCoroutines
import com.marcelo.marvelheroes.utils.getPagingSourceFactory
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertNotNull

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

            coEvery { repository.getHeroes(emptyString()) }.returns(getPagingSourceFactory)

            val result = useCase(emptyString(), PagingConfig(PAGING_SIZE))

            coVerify { repository.getHeroes(emptyString()) }

            assertNotNull(result.first())
        }

    @Test
    fun `should throw exception when repository fails to provide paging data source`() = runTest {

        val errorMsg = "Failed to get paging data source"
        coEvery { repository.getHeroes(emptyString()) }.throws(RuntimeException(errorMsg))

        val result = runCatching {
            useCase(emptyString(), PagingConfig(PAGING_SIZE)).first()
        }

        coVerify { repository.getHeroes(emptyString()) }

        val expectedException = Result.failure<Throwable>(
            RuntimeException(errorMsg)
        ).exceptionOrNull()
        val actualException = result.exceptionOrNull()

        assertEquals(expectedException?.message, actualException?.message)
    }
}