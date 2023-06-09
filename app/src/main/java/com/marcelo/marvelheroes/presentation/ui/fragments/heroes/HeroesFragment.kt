package com.marcelo.marvelheroes.presentation.ui.fragments.heroes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState.Error
import androidx.paging.LoadState.Loading
import com.marcelo.marvelheroes.databinding.FragmentHeroesBinding
import com.marcelo.marvelheroes.databinding.FragmentHeroesBinding.inflate
import com.marcelo.marvelheroes.domain.model.DetailsHeroesArgViewData
import com.marcelo.marvelheroes.domain.model.HeroesViewData
import com.marcelo.marvelheroes.extensions.emptyString
import com.marcelo.marvelheroes.presentation.adapters.heroes.HeroesAdapter
import com.marcelo.marvelheroes.presentation.adapters.heroes.HeroesLoadMoreAdapter
import com.marcelo.marvelheroes.presentation.viewmodel.HeroesViewModel
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.viewmodel.ext.android.viewModel
import androidx.paging.LoadState.NotLoading as Success

class HeroesFragment : Fragment() {

    private lateinit var binding: FragmentHeroesBinding

    private val heroesAdapter by lazy { HeroesAdapter(::onHeroClicked) }

    private val viewModel: HeroesViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initHeroesAdapter()
        fetchRequestHeroesPaging()
        handleHeroesPaging()
    }

    private fun initHeroesAdapter() = with(binding.rvHeroes) {
        scrollToPosition(HEROES_INITIAL_POSITION)
        setHasFixedSize(true)
        adapter = heroesAdapter.withLoadStateFooter(
            footer = HeroesLoadMoreAdapter(retryLoad = heroesAdapter::retry)
        )
    }

    private fun fetchRequestHeroesPaging() = lifecycleScope.launchWhenCreated {
        viewModel.getPagingHeroes(emptyString()).collect { pagingData ->
            heroesAdapter.submitData(pagingData)
        }
    }

    private fun handleHeroesPaging() = lifecycleScope.launchWhenCreated {
        heroesAdapter.loadStateFlow.collectLatest { loadState ->
            when (loadState.refresh) {
                is Loading -> showShimmer(true)
                is Success -> showShimmer(false)
                is Error -> showError()
            }
        }
    }

    private fun showShimmer(isVisibility: Boolean) = with(binding.includeShimmer.shimmerHeroes) {
        isVisible = isVisibility
        if (isVisibility) startShimmer()
        else {
            stopShimmer()
            binding.layoutShimmer.isVisible = false
            binding.rvHeroes.isGone = false
        }
    }

    private fun showError() =
        with(binding) {
            layoutShimmer.isVisible = false
            layoutError.isVisible = true
            includeError.btnRetryLoading.setOnClickListener {
                heroesAdapter.retry()
                layoutShimmer.isVisible = true
                layoutError.isVisible = false
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

    companion object {
        private const val HEROES_INITIAL_POSITION = 0
    }
}
