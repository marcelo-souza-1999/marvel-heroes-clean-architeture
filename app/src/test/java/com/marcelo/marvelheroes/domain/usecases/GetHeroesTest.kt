package com.marcelo.marvelheroes.domain.usecases

import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.marcelo.marvelheroes.domain.model.HeroesViewData
import com.marcelo.marvelheroes.domain.repository.HeroesRepository
import com.marcelo.marvelheroes.domain.repository.LocalStorageRepository
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
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class GetHeroesTest {

    @get:Rule
    var setupCoroutineRule = SetupCoroutines()

    private lateinit var repository: HeroesRepository

    private val mockLocalStorageRepository: LocalStorageRepository = mockk()

    private lateinit var useCase: GetHeroes

    @Before
    fun setup() {
        coEvery { mockLocalStorageRepository.sort } returns flowOf("name")
        repository = mockk()
        useCase = GetHeroes(
            repository = repository,
            localStorageRepository = mockLocalStorageRepository
        )
    }

    @Test
    fun `should throw exception when repository fails to provide paging data source`() = runTest {

        val errorMsg = "Failed to get paging data source"
        coEvery { repository.getCachedHeroes(emptyString(), any(), any()) }.throws(
            RuntimeException(
                errorMsg
            )
        )

        val result = runCatching {
            useCase.invoke(emptyString(), PagingConfig(PAGING_SIZE)).first()
        }

        coVerify { repository.getCachedHeroes(emptyString(), any(), any()) }

        val expectedException = Result.failure<Throwable>(
            RuntimeException(errorMsg)
        ).exceptionOrNull()
        val actualException = result.exceptionOrNull()

        assertEquals(expectedException?.message, actualException?.message)
    }

}