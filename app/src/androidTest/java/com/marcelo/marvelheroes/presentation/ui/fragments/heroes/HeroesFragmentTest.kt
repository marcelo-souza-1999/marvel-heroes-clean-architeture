package com.marcelo.marvelheroes.presentation.ui.fragments.heroes

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.runner.AndroidJUnit4
import com.marcelo.marvelheroes.R
import com.marcelo.marvelheroes.presentation.adapters.viewholder.HeroesViewHolder
import com.marcelo.marvelheroes.presentation.ui.activitys.NavigationActivity
import com.marcelo.marvelheroes.presentation.viewmodel.HeroesViewModel
import com.marcelo.marvelheroes.utils.FileJsonReader
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
class HeroesFragmentTest {

    private lateinit var server: MockWebServer

    private lateinit var scenario: ActivityScenario<NavigationActivity>

    @Before
    fun setup() {
        server = MockWebServer().apply {
            start(PORT_8080)
        }
        loadKoinModules(testModule)
    }

    @After
    fun tearDown() {
        server.shutdown()
        scenario.close()
        unloadKoinModules(testModule)
    }

    @Test
    fun shouldDisplayShimmer() {
        scenario = ActivityScenario.launch(NavigationActivity::class.java)

        onView(
            withId(R.id.layoutShimmer)
        ).check(
            matches(isDisplayed())
        )
    }

    @Test
    fun shouldDisplayScreenError() {
        scenario = ActivityScenario.launch(NavigationActivity::class.java)

        server.enqueue(MockResponse().setResponseCode(CODE_404))

        onView(
            withId(R.id.layoutError)
        ).check(
            matches(isDisplayed())
        )
    }

    @Test
    fun shouldDisplayHeroesRecyclerView() {
        scenario = ActivityScenario.launch(NavigationActivity::class.java)

        server.enqueue(MockResponse().setBody(FileJsonReader().readJsonFile(FILE_NAME_HEROES_JSON)))

        onView(
            withId(R.id.rvHeroes)
        ).check(
            matches(isDisplayed())
        )
    }

    @Test
    fun shouldDisplayHeroesPagingTwoRecyclerView() {
        scenario = ActivityScenario.launch(NavigationActivity::class.java)

        with(server) {
            enqueue(MockResponse().setBody(FileJsonReader().readJsonFile(FILE_NAME_HEROES_JSON)))
            enqueue(
                MockResponse().setBody(
                    FileJsonReader().readJsonFile(
                        FILE_NAME_HEROES_PAGING_TWO_JSON
                    )
                )
            )
        }

        onView(
            withId(R.id.rvHeroes)
        ).perform(
            RecyclerViewActions
                .scrollToPosition<HeroesViewHolder>(TWENTY)
        )

        onView(
            withText("Amora")
        ).check(
            matches(isDisplayed())
        )
    }

    private val testModule = module {
        viewModel { HeroesViewModel(get()) }
        single(named("baseUrl")) { BASE_URL }
    }

    private companion object {
        const val TWENTY = 20
        const val CODE_404 = 404
        const val PORT_8080 = 8080
        const val BASE_URL = "http://localhost:$PORT_8080"
        const val FILE_NAME_HEROES_JSON = "heroes_marvel.json"
        const val FILE_NAME_HEROES_PAGING_TWO_JSON = "heroes_marvel_paging_two.json"
    }
}