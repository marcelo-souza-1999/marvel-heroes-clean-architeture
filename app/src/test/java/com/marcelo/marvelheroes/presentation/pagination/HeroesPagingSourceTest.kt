package com.marcelo.marvelheroes.presentation.pagination

import androidx.paging.PagingSource
import com.marcelo.marvelheroes.data.repository.interfaces.HeroesRemoteDataSource
import com.marcelo.marvelheroes.domain.model.HeroesViewData
import com.marcelo.marvelheroes.extensions.emptyString
import com.marcelo.marvelheroes.utils.ONE
import com.marcelo.marvelheroes.utils.PAGING_SIZE
import com.marcelo.marvelheroes.utils.SetupCoroutines
import com.marcelo.marvelheroes.utils.getHeroesFactory
import com.marcelo.marvelheroes.utils.getHeroesPagingViewData
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class HeroesPagingSourceTest {

    @get:Rule
    var setupCoroutineRule = SetupCoroutines()

    private lateinit var heroesPagingSource: HeroesPagingSource

    private lateinit var remoteDataSource: HeroesRemoteDataSource

    @Before
    fun setup() {
        remoteDataSource = mock()
        heroesPagingSource = HeroesPagingSource(
            remoteDataSource = remoteDataSource,
            query = emptyString()
        )
    }

    @Test
    fun `should return success first load result when load is called`() = runTest {
        whenever(remoteDataSource.fetchHeroes(any())).thenReturn(
            getHeroesPagingViewData
        )

        val result = heroesPagingSource.load(
            params = PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = ONE,
                placeholdersEnabled = false
            )
        )

        val expected = listOf(
            getHeroesFactory
        )

        assertEquals(
            PagingSource.LoadResult.Page(
                data = expected,
                prevKey = null,
                nextKey = PAGING_SIZE
            ),
            result
        )
    }

    @Test
    fun `should return a error load result when load is called`() = runTest {
        val exception = RuntimeException()
        whenever(remoteDataSource.fetchHeroes(any())).thenThrow(
            exception
        )

        val result = heroesPagingSource.load(
            params = PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = ONE,
                placeholdersEnabled = false
            )
        )

        assertEquals(
            PagingSource.LoadResult.Error<Int, HeroesViewData>(exception),
            result
        )
    }
}