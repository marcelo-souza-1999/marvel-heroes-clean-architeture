package com.marcelo.marvelheroes.data.repository

import com.marcelo.marvelheroes.data.remote.datasource.HeroesPagingSource
import com.marcelo.marvelheroes.domain.datasource.HeroesRemoteDataSource
import com.marcelo.marvelheroes.data.remote.repository.HeroesRepositoryImpl
import com.marcelo.marvelheroes.domain.mapper.DetailHeroesMapper
import com.marcelo.marvelheroes.domain.repository.HeroesRepository
import com.marcelo.marvelheroes.extensions.emptyString
import com.marcelo.marvelheroes.utils.getComicsFactory
import com.marcelo.marvelheroes.utils.getComicsFactoryFlow
import com.marcelo.marvelheroes.utils.getDetailChildFactoryList
import com.marcelo.marvelheroes.utils.getEventsFactory
import com.marcelo.marvelheroes.utils.getEventsFactoryFlow
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class HeroesRepositoryImplTest {

    private lateinit var repository: HeroesRepository
    private lateinit var mapper: DetailHeroesMapper
    private lateinit var remoteDataSource: HeroesRemoteDataSource

    @Before
    fun setup() {
        remoteDataSource = mockk()
        mapper = mockk()
        repository = HeroesRepositoryImpl(
            remoteDataSource = remoteDataSource, detailHeroesMapper = mapper
        )
    }

    @Test
    fun `getHeroes shouldReturn HeroesPagingSource`() {

        val result = repository.getHeroes(emptyString())

        assertEquals(HeroesPagingSource::class.java, result.javaClass)
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