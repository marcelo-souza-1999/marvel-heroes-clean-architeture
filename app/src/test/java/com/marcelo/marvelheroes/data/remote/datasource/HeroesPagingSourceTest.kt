package com.marcelo.marvelheroes.data.remote.datasource

import androidx.paging.PagingSource
import com.marcelo.marvelheroes.domain.datasource.HeroesRemoteDataSource
import com.marcelo.marvelheroes.domain.model.HeroesViewData
import com.marcelo.marvelheroes.extensions.emptyString
import com.marcelo.marvelheroes.utils.ONE
import com.marcelo.marvelheroes.utils.PAGING_SIZE
import com.marcelo.marvelheroes.utils.SetupCoroutines
import com.marcelo.marvelheroes.utils.getComicsFactory
import com.marcelo.marvelheroes.utils.getComicsFactoryFlow
import com.marcelo.marvelheroes.utils.getComicsResponseList
import com.marcelo.marvelheroes.utils.getEventsFactory
import com.marcelo.marvelheroes.utils.getEventsFactoryFlow
import com.marcelo.marvelheroes.utils.getEventsResponseList
import com.marcelo.marvelheroes.utils.getHeroesFactoryFlow
import com.marcelo.marvelheroes.utils.getHeroesPagingSourceFactory
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.single
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
        remoteDataSource = mockk()
        heroesPagingSource = HeroesPagingSource(
            remoteDataSource = remoteDataSource,
            query = emptyString()
        )
    }

    @Test
    fun `should return success first load result when load is called`() = runTest {

        coEvery { remoteDataSource.fetchHeroes(any()) } returns getHeroesFactoryFlow

        val result = heroesPagingSource.load(
            params = PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = ONE,
                placeholdersEnabled = false
            )
        )

        val expected = listOf(
            getHeroesPagingSourceFactory
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

        val exception = Exception()

        coEvery { remoteDataSource.fetchHeroes(any()) } throws exception

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

    @Test
    fun `should return success result for fetchComics when load is called`() = runTest {

        val heroId = getComicsFactory.id

        coEvery { remoteDataSource.fetchComics(any()) } returns getComicsFactoryFlow

        val result = remoteDataSource.fetchComics(heroId).single().dataContainerHeroes.results

        val expected = getComicsResponseList

        assertEquals(expected, result)
    }

    @Test
    fun `should return success result for fetchEvents when load is called`() = runTest {

        val heroId = getEventsFactory.id

        coEvery { remoteDataSource.fetchEvents(any()) } returns getEventsFactoryFlow

        val result = remoteDataSource.fetchEvents(heroId).single().dataContainerHeroes.results

        val expected = getEventsResponseList

        assertEquals(expected, result)
    }
}
