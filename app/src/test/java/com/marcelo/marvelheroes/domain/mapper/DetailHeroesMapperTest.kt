package com.marcelo.marvelheroes.domain.mapper

import com.marcelo.marvelheroes.data.remote.model.ComicsResponse
import com.marcelo.marvelheroes.data.remote.model.EventsResponse
import com.marcelo.marvelheroes.data.remote.model.ThumbnailResponse
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DetailHeroesMapperTest {

    private lateinit var mapper: DetailHeroesMapper

    @Before
    fun setup() {
        mapper = DetailHeroesMapper()
    }

    @Test
    fun mapComicsResponseToDetailChildViewData_shouldReturnMappedData() {
        val comicsResponse = ComicsResponse(
            id = 1,
            thumbnail = ThumbnailResponse(
                path = "http://example.com/image",
                extension = "jpg"
            )
        )

        val result = mapper.mapComicsResponseToDetailChildViewData(comicsResponse)

        assertEquals(1, result.size)
        assertEquals(comicsResponse.id, result[0].id)
        assertEquals("https://example.com/image.jpg", result[0].imageUrl)
    }

    @Test
    fun mapEventsResponseToDetailChildViewData_shouldReturnMappedData() {
        val eventsResponse = EventsResponse(
            id = 1,
            thumbnail = ThumbnailResponse(
                path = "http://example.com/image",
                extension = "jpg"
            )
        )

        val result = mapper.mapEventsResponseToDetailChildViewData(eventsResponse)

        assertEquals(1, result.size)
        assertEquals(eventsResponse.id, result[0].id)
        assertEquals("https://example.com/image.jpg", result[0].imageUrl)
    }
}