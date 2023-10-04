package com.marcelo.marvelheroes.presentation.ui.fragments.details

import android.os.Handler
import android.os.Looper
import androidx.core.os.bundleOf
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.runner.AndroidJUnit4
import com.marcelo.marvelheroes.R.id
import com.marcelo.marvelheroes.domain.model.DetailsHeroesArgViewData
import com.marcelo.marvelheroes.presentation.viewmodel.DetailsViewModel
import com.marcelo.marvelheroes.utils.FileJsonReader
import com.marcelo.marvelheroes.utils.heroIdDefault
import com.marcelo.marvelheroes.utils.heroNameDefault
import com.marcelo.marvelheroes.utils.imageUrlDefault
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.core.qualifier.named
import org.koin.dsl.module

@RunWith(AndroidJUnit4::class)
class DetailsFragmentTest {

    private lateinit var server: MockWebServer

    private lateinit var fragmentScenario: FragmentScenario<DetailsFragment>

    @Before
    fun setup() {
        server = MockWebServer().apply {
            start(PORT_8080)
        }
        loadKoinModules(testModule)

        val bundleArgs = bundleOf(
            DETAIL_HEROES_ARG to DetailsHeroesArgViewData(
                heroId = heroIdDefault,
                name = heroNameDefault,
                imageUrl = imageUrlDefault
            ),
            TOOLBAR_TITLE_ARG to heroNameDefault
        )

        fragmentScenario = launchFragmentInContainer(
            fragmentArgs = bundleArgs
        )
    }

    @After
    fun tearDown() {
        server.shutdown()
        fragmentScenario.close()
        unloadKoinModules(testModule)
    }

    @Test
    fun shouldDisplayShimmer() {
        Handler(Looper.getMainLooper()).postDelayed({
            onView(
                withId(id.layoutShimmerDetails)
            ).check(
                matches(isDisplayed())
            )
        }, DELAY_MS)
    }

    @Test
    fun shouldDisplayScreenError() {
        server.enqueue(MockResponse().setResponseCode(CODE_404))

        onView(
            withId(id.layoutErrorView)
        ).check(
            matches(isDisplayed())
        )
    }

    @Test
    fun shouldDisplayScreenEmpty() {
        server.enqueue(
            MockResponse().setBody(
                FileJsonReader().readJsonFile(FILE_NAME_HEROES_DETAILS_EMPTY_JSON)
            )
        )

        Handler(Looper.getMainLooper()).postDelayed({
            onView(
                withId(id.layoutEmptyView)
            ).check(
                matches(isDisplayed())
            )
        }, DELAY_MS)
    }

    @Test
    fun shouldDisplayDetailsHeroesRecyclerView() {
        with(server) {
            enqueue(
                MockResponse().setBody(
                    FileJsonReader().readJsonFile(
                        FILE_NAME_HEROES_DETAILS_COMICS_JSON
                    )
                )
            )
            enqueue(
                MockResponse().setBody(
                    FileJsonReader().readJsonFile(
                        FILE_NAME_HEROES_DETAILS_EVENTS_JSON
                    )
                )
            )
        }

        onView(
            withId(id.rvDetails)
        ).check(
            matches(isDisplayed())
        )
    }

    private companion object {
        const val DELAY_MS = 1000L
        const val CODE_404 = 404
        const val DETAIL_HEROES_ARG = "detailsHeroesArg"
        const val TOOLBAR_TITLE_ARG = "toolbarTitle"
        const val PORT_8080 = 8080
        const val BASE_URL = "http://localhost:${PORT_8080}"
        const val FILE_NAME_HEROES_DETAILS_EVENTS_JSON = "heroes_marvel_details_events.json"
        const val FILE_NAME_HEROES_DETAILS_COMICS_JSON = "heroes_marvel_details_comics.json"
        const val FILE_NAME_HEROES_DETAILS_EMPTY_JSON = "heroes_marvel_details_empty.json"

        val testModule = module {
            viewModel { DetailsViewModel(get(), get()) }
            single(named("baseUrl")) { BASE_URL }
        }
    }
}