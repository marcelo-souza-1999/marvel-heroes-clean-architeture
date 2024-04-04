package com.marcelo.marvelheroes.presentation.ui.fragments.heroes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.paging.LoadState.Error
import androidx.paging.LoadState.Loading
import com.marcelo.marvelheroes.R
import com.marcelo.marvelheroes.databinding.FragmentHeroesBinding
import com.marcelo.marvelheroes.databinding.FragmentHeroesBinding.inflate
import com.marcelo.marvelheroes.domain.model.DetailsHeroesArgViewData
import com.marcelo.marvelheroes.domain.model.HeroesViewData
import com.marcelo.marvelheroes.extensions.emptyString
import com.marcelo.marvelheroes.presentation.adapters.heroes.HeroesAdapter
import com.marcelo.marvelheroes.presentation.adapters.heroes.HeroesLoadMoreAdapter
import com.marcelo.marvelheroes.presentation.adapters.heroes.HeroesRefreshDataAdapter
import com.marcelo.marvelheroes.presentation.viewmodel.HeroesViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class HeroesFragment : Fragment() {

    private lateinit var binding: FragmentHeroesBinding

    private val heroesAdapter by lazy { HeroesAdapter(::onHeroClicked) }

    private val heroesRefreshDataAdapter by lazy {
        HeroesRefreshDataAdapter(heroesAdapter::retry)
    }

    private val viewModel: HeroesViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.item_sort_heroes_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.sortHeroes -> {
                findNavController().navigate(R.id.action_heroesFragment_to_sortFragment)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initHeroesAdapter()
        fetchRequestHeroesPaging()
        handleHeroesPaging()
    }

    private fun initHeroesAdapter() = with(binding.rvHeroes) {
        postponeEnterTransition()
        setHasFixedSize(true)
        adapter = heroesAdapter.withLoadStateHeaderAndFooter(
            header = heroesRefreshDataAdapter,
            footer = HeroesLoadMoreAdapter(retryLoad = heroesAdapter::retry)
        )
        viewTreeObserver.addOnPreDrawListener {
            startPostponedEnterTransition()
            true
        }
    }

    private fun fetchRequestHeroesPaging() = lifecycleScope.launch {
        lifecycle.repeatOnLifecycle(Lifecycle.State.CREATED) {
            viewModel.getPagingHeroes(emptyString()).collect { pagingData ->
                heroesAdapter.submitData(pagingData)
            }
        }
    }

    private fun handleHeroesPaging() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.CREATED) {
                heroesAdapter.loadStateFlow.collectLatest { loadState ->
                    heroesRefreshDataAdapter.loadState = loadState.mediator?.refresh?.takeIf {
                        it is Error && heroesAdapter.itemCount > ZERO
                    } ?: loadState.prepend

                    when (loadState.mediator?.refresh) {
                        is Loading -> showShimmer(true)
                        is Error -> if (heroesAdapter.itemCount == ZERO) showError()
                        is LoadState.NotLoading -> showShimmer(false)
                        null -> showError()
                    }
                }
            }
        }
    }


    private fun showShimmer(isVisibility: Boolean) = with(binding.includeShimmer.shimmerHeroes) {
        isVisible = isVisibility
        if (isVisibility) startShimmer()
        else {
            stopShimmer()
            binding.layoutShimmerHeroes.isVisible = false
            binding.rvHeroes.isGone = false
        }
    }

    private fun showError() {
        with(binding) {
            layoutShimmerHeroes.isVisible = false
            layoutError.isVisible = true
            includeError.btnRetryLoading.setOnClickListener {
                heroesAdapter.retry()
                layoutShimmerHeroes.isVisible = true
                layoutError.isVisible = false
            }
        }

    }

    private fun onHeroClicked(heroesData: HeroesViewData, view: View) {
        val extras = FragmentNavigatorExtras(
            view to heroesData.name
        )

        val directions = HeroesFragmentDirections.actionOpenDetailsFragment(
            heroesData.name,
            DetailsHeroesArgViewData(
                heroId = heroesData.id,
                name = heroesData.name,
                imageUrl = heroesData.imageUrl
            )
        )

        findNavController().navigate(directions, extras)
    }

    private companion object {
        const val ZERO = 0
    }
}
