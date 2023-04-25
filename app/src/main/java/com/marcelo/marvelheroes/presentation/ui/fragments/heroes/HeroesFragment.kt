package com.marcelo.marvelheroes.presentation.ui.fragments.heroes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState.Error
import androidx.paging.LoadState.Loading
import androidx.recyclerview.widget.LinearLayoutManager
import com.marcelo.marvelheroes.databinding.FragmentHeroesBinding
import com.marcelo.marvelheroes.databinding.FragmentHeroesBinding.inflate
import com.marcelo.marvelheroes.extensions.emptyString
import com.marcelo.marvelheroes.presentation.adapters.HeroesAdapter
import com.marcelo.marvelheroes.presentation.adapters.HeroesLoadMoreAdapter
import com.marcelo.marvelheroes.presentation.viewmodel.HeroesViewModel
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.viewmodel.ext.android.viewModel
import androidx.paging.LoadState.NotLoading as Success

class HeroesFragment : Fragment() {

    private lateinit var binding: FragmentHeroesBinding

    private val heroesAdapter by lazy { HeroesAdapter() }

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
        scrollToPosition(INITIAL_POSITION)
        setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(requireContext())
        this.layoutManager = layoutManager
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
        if (isVisibility) {
            startShimmer()
        } else {
            stopShimmer()
            binding.layoutShimmer.isVisible = false
            binding.rvHeroes.isGone = false
        }
    }

    private fun showError() =
        with(binding) {
            layoutShimmer.isVisible = false
            layoutError.isVisible = true
            binding.includeError.btnRetryLoading.setOnClickListener {
                heroesAdapter.refresh()
            }
        }

    companion object {
        private const val INITIAL_POSITION = 0
    }
}
