package com.marcelo.marvelheroes.domain.usecases

import androidx.paging.PagingConfig
import com.marcelo.marvelheroes.domain.repository.HeroesRepository
import com.marcelo.marvelheroes.domain.usecases.GetHeroesUseCaseImpl.Companion.GetHeroesParams
import com.marcelo.marvelheroes.domain.usecases.interfaces.GetHeroesUseCase
import com.marcelo.marvelheroes.extensions.emptyString
import com.marcelo.marvelheroes.utils.PAGING_SIZE
import com.marcelo.marvelheroes.utils.SetupCoroutines
import com.marcelo.marvelheroes.utils.getPagingSourceFactory
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertNotNull

@ExperimentalCoroutinesApi
class GetHeroesUseCaseImplTest {

    @get:Rule
    var setupCoroutineRule = SetupCoroutines()

    private lateinit var repository: HeroesRepository

    private lateinit var useCase: GetHeroesUseCase

    @Before
    fun setup() {
        repository = mock()
        useCase = GetHeroesUseCaseImpl(
            repository = repository
        )
    }

    @Test
    fun `should validate flow paging data creation when invoke from use case is called`() =
        runTest {

            whenever(repository.getHeroes(emptyString()))
                .thenReturn(
                    getPagingSourceFactory
                )

            val result = useCase.invoke(
                GetHeroesParams(emptyString(), PagingConfig(PAGING_SIZE))
            )

            verify(repository).getHeroes(emptyString())

            assertNotNull(result.first())
        }

    @Test
    fun `should throw exception when repository fails to provide paging data source`() = runTest {

        val errorMsg = "Failed to get paging data source"
        whenever(repository.getHeroes(emptyString())).thenThrow(RuntimeException(errorMsg))

        val result = runCatching {
            useCase.invoke(
                GetHeroesParams(emptyString(), PagingConfig(PAGING_SIZE))
            ).first()
        }

        verify(repository).getHeroes(emptyString())

        val expectedException = Result.failure<Throwable>(
            RuntimeException(errorMsg)
        ).exceptionOrNull()
        val actualException = result.exceptionOrNull()

        assertEquals(expectedException?.message, actualException?.message)
    }
}