package com.marcelo.marvelheroes.data.repository

import com.marcelo.marvelheroes.data.remote.datasource.HeroesRemoteDataSource
import com.marcelo.marvelheroes.domain.repository.HeroesRepository
import com.marcelo.marvelheroes.extensions.emptyString
import com.marcelo.marvelheroes.data.remote.datasource.HeroesPagingSource
import com.marcelo.marvelheroes.utils.getComicsFactory
import com.marcelo.marvelheroes.utils.getComicsFactoryList
import com.marcelo.marvelheroes.utils.getEventsFactory
import com.marcelo.marvelheroes.utils.getEventsFactoryList
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class HeroesRepositoryImplTest {

    private lateinit var repository: HeroesRepository
    private lateinit var remoteDataSource: HeroesRemoteDataSource

    @Before
    fun setup() {
        remoteDataSource = mock()
        repository = HeroesRepositoryImpl(remoteDataSource)
    }

    @Test
    fun `getHeroes shouldReturn HeroesPagingSource`() {

        val result = repository.getHeroes(emptyString())

        assertEquals(HeroesPagingSource::class.java, result.javaClass)
    }

    @Test
    fun `getComics should Call Fetch Comics From Remote Data Source`() = runTest {

        val heroId = getComicsFactory.id
        val expectedComics = getComicsFactoryList
        whenever(remoteDataSource.fetchComics(heroId)).thenReturn(expectedComics)


        val result = repository.getComics(heroId)

        assertEquals(expectedComics, result)
    }

    @Test
    fun `getComics should Call Fetch Events From Remote Data Source`() = runTest {

        val heroId = getEventsFactory.id
        val expectedEvents = getEventsFactoryList
        whenever(remoteDataSource.fetchEvents(heroId)).thenReturn(expectedEvents)


        val result = repository.getEvents(heroId)

        assertEquals(expectedEvents, result)
    }
}