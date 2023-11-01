package com.marcelo.marvelheroes.domain.usecases

import com.marcelo.marvelheroes.domain.factories.DetailParentFactory
import com.marcelo.marvelheroes.domain.model.DetailParentViewData
import com.marcelo.marvelheroes.domain.repository.HeroesRepository
import com.marcelo.marvelheroes.utils.SetupCoroutines
import com.marcelo.marvelheroes.utils.getDetailChildFactoryList
import com.marcelo.marvelheroes.utils.getEventsFactory
import com.marcelo.marvelheroes.utils.getHeroesFactory
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertFailsWith

@ExperimentalCoroutinesApi
class GetComicsEventsUseCaseTest {

    @get:Rule
    var setupCoroutineRule = SetupCoroutines()

    private lateinit var repository: HeroesRepository

    private lateinit var detailParentFactory: DetailParentFactory

    private lateinit var useCase: GetComicsEventsUseCase

    @Before
    fun setup() {
        repository = mockk()
        detailParentFactory = mockk()
        useCase = GetComicsEventsUseCase(repository, detailParentFactory)
    }

    @Test
    fun `should return list of DetailParentViewData with Comics and Events`() = runTest {

        coEvery { repository.getComics(getHeroesFactory.id) } returns getDetailChildFactoryList
        coEvery { repository.getEvents(getEventsFactory.id) } returns getDetailChildFactoryList

        coEvery {
            detailParentFactory.createDetailParent(
                COMICS,
                getDetailChildFactoryList
            )
        } returns DetailParentViewData(
            categories = COMICS,
            detailChildList = getDetailChildFactoryList
        )

        coEvery {
            detailParentFactory.createDetailParent(
                EVENTS,
                getDetailChildFactoryList
            )
        } returns DetailParentViewData(
            categories = EVENTS,
            detailChildList = getDetailChildFactoryList
        )

        val result = useCase(getHeroesFactory.id).toList().first()

        val expectedList = listOf(
            DetailParentViewData(COMICS, getDetailChildFactoryList),
            DetailParentViewData(EVENTS, getDetailChildFactoryList)
        )

        assertEquals(expectedList, result)
    }

    @Test
    fun `should return list of DetailParentViewData with only Events`() = runTest {

        coEvery { repository.getComics(getHeroesFactory.id) } returns emptyList()
        coEvery { repository.getEvents(getEventsFactory.id) } returns getDetailChildFactoryList

        coEvery {
            detailParentFactory.createDetailParent(
                COMICS,
                emptyList()
            )
        } returns DetailParentViewData(
            categories = COMICS,
            detailChildList = emptyList()
        )

        coEvery {
            detailParentFactory.createDetailParent(
                EVENTS,
                getDetailChildFactoryList
            )
        } returns DetailParentViewData(
            categories = EVENTS,
            detailChildList = getDetailChildFactoryList
        )

        val result = useCase(getHeroesFactory.id).toList().first()

        val expectedList = listOf(
            DetailParentViewData(EVENTS, getDetailChildFactoryList)
        )

        assertEquals(expectedList, result)
    }

    @Test
    fun `should return list of DetailParentViewData with only Comics`() = runTest {

        coEvery { repository.getComics(getHeroesFactory.id) } returns getDetailChildFactoryList
        coEvery { repository.getEvents(getEventsFactory.id) } returns emptyList()

        coEvery {
            detailParentFactory.createDetailParent(
                COMICS,
                getDetailChildFactoryList
            )
        } returns DetailParentViewData(
            categories = COMICS,
            detailChildList = getDetailChildFactoryList
        )

        coEvery {
            detailParentFactory.createDetailParent(
                EVENTS,
                emptyList()
            )
        } returns DetailParentViewData(
            categories = EVENTS,
            detailChildList = emptyList()
        )

        val result = useCase(getHeroesFactory.id).toList().first()

        val expectedList = listOf(
            DetailParentViewData(COMICS, getDetailChildFactoryList)
        )

        assertEquals(expectedList, result)
    }

    @Test
    fun `should return empty list when both Comics and Events empty data`() = runTest {

        coEvery { repository.getComics(getHeroesFactory.id) } returns emptyList()
        coEvery { repository.getEvents(getEventsFactory.id) } returns emptyList()

        val result = useCase(getHeroesFactory.id).toList().first()

        assertTrue(result.isEmpty())
    }

    @Test
    fun `should return list of DetailParentViewData with only Comics empty data`() = runTest {

        coEvery { repository.getComics(getHeroesFactory.id) } returns emptyList()
        coEvery { repository.getEvents(getEventsFactory.id) } returns getDetailChildFactoryList

        coEvery {
            detailParentFactory.createDetailParent(
                COMICS,
                emptyList()
            )
        } returns DetailParentViewData(
            categories = COMICS,
            detailChildList = emptyList()
        )

        coEvery {
            detailParentFactory.createDetailParent(
                EVENTS,
                getDetailChildFactoryList
            )
        } returns DetailParentViewData(
            categories = EVENTS,
            detailChildList = getDetailChildFactoryList
        )

        val result = useCase(getHeroesFactory.id).toList().first()

        assertTrue(result.size == 1)
    }

    @Test
    fun `should return list of DetailParentViewData with only Events empty data`() = runTest {

        coEvery { repository.getComics(getHeroesFactory.id) } returns getDetailChildFactoryList
        coEvery { repository.getEvents(getEventsFactory.id) } returns emptyList()

        coEvery {
            detailParentFactory.createDetailParent(
                COMICS,
                getDetailChildFactoryList
            )
        } returns DetailParentViewData(
            categories = COMICS,
            detailChildList = getDetailChildFactoryList
        )

        coEvery {
            detailParentFactory.createDetailParent(
                EVENTS,
                emptyList()
            )
        } returns DetailParentViewData(
            categories = EVENTS,
            detailChildList = emptyList()
        )

        val result = useCase(getHeroesFactory.id).toList().first()

        assertTrue(result.size == 1)
    }

    @Test
    fun `should throw an exception when both Comics and Events fail`() = runTest {

        coEvery { repository.getComics(getHeroesFactory.id) } throws Throwable("Comics failed")
        coEvery { repository.getEvents(getEventsFactory.id) } throws Throwable("Events failed")

        assertFailsWith<Throwable> {
            useCase(getHeroesFactory.id).toList()
        }
    }

    @Test
    fun `should throw an exception when Comics fail`() = runTest {

        coEvery { repository.getComics(getHeroesFactory.id) } throws Throwable("Comics failed")
        coEvery { repository.getEvents(getEventsFactory.id) } returns getDetailChildFactoryList

        assertFailsWith<Throwable> {
            useCase(getHeroesFactory.id).toList()
        }
    }

    @Test
    fun `should throw an exception when Events fail`() = runTest {

        coEvery { repository.getComics(getHeroesFactory.id) } returns getDetailChildFactoryList
        coEvery { repository.getEvents(getEventsFactory.id) } throws Throwable("Events failed")


        assertFailsWith<Throwable> {
            useCase(getHeroesFactory.id).toList()
        }
    }

    private companion object {
        const val COMICS = "Comics"
        const val EVENTS = "Events"
    }
}
