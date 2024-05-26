package com.marcelo.marvelheroes.data.repository

import androidx.paging.PagingConfig
import com.marcelo.marvelheroes.data.local.database.MarvelDatabase
import com.marcelo.marvelheroes.data.remote.repository.HeroesRepositoryImpl
import com.marcelo.marvelheroes.domain.datasource.HeroesRemoteDataSource
import com.marcelo.marvelheroes.domain.mapper.DetailHeroesMapper
import com.marcelo.marvelheroes.domain.repository.HeroesRepository
import com.marcelo.marvelheroes.utils.getComicsFactory
import com.marcelo.marvelheroes.utils.getComicsFactoryFlow
import com.marcelo.marvelheroes.utils.getDetailChildFactoryList
import com.marcelo.marvelheroes.utils.getEventsFactory
import com.marcelo.marvelheroes.utils.getEventsFactoryFlow
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class HeroesRepositoryImplTest {

    private lateinit var repository: HeroesRepository
    private lateinit var database: MarvelDatabase
    private lateinit var mapper: DetailHeroesMapper
    private lateinit var remoteDataSource: HeroesRemoteDataSource
    private lateinit var pagerConfig: PagingConfig

    @Before
    fun setup() {
        remoteDataSource = mockk()
        database = mockk {
            every { createHeroDao().pagingSource() } returns mockk(relaxed = true)
            every { createRemoteKeyDao() } returns mockk()
        }
        mapper = mockk()
        pagerConfig = mockk()
        repository = HeroesRepositoryImpl(
            remoteDataSource = remoteDataSource,
            database = database,
            detailHeroesMapper = mapper
        )
    }

    @Test
    fun `getComics should Call Fetch Comics From Remote Data Source`() = runTest {

        val heroId = getComicsFactory.id
        val expectedComics = getDetailChildFactoryList

        coEvery { remoteDataSource.fetchComics(any()) } returns getComicsFactoryFlow
        coEvery { mapper.mapComicsResponseToDetailChildViewData(any()) } returns expectedComics

        val result = repository.getComics(heroId)

        assertEquals(expectedComics, result)
    }

    @Test
    fun `getEvents should Call Fetch Events From Remote Data Source`() = runTest {

        val heroId = getEventsFactory.id
        val expectedEvents = getDetailChildFactoryList

        coEvery { remoteDataSource.fetchEvents(heroId) } returns getEventsFactoryFlow
        coEvery { mapper.mapEventsResponseToDetailChildViewData(any()) } returns expectedEvents

        val result = repository.getEvents(heroId)

        assertEquals(expectedEvents, result)
    }
}