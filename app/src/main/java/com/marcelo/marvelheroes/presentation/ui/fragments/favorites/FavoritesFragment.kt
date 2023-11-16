package com.marcelo.marvelheroes.presentation.ui.fragments.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.marcelo.marvelheroes.R
import com.marcelo.marvelheroes.databinding.FragmentFavoritesBinding
import com.marcelo.marvelheroes.databinding.FragmentFavoritesBinding.inflate
import com.marcelo.marvelheroes.domain.model.FavoriteItemData
import com.marcelo.marvelheroes.presentation.adapters.utils.getGenericAdapterOf
import com.marcelo.marvelheroes.presentation.adapters.viewholder.HeroesFavoriteViewHolder
import com.marcelo.marvelheroes.presentation.viewmodel.FavoritesViewModel
import com.marcelo.marvelheroes.presentation.viewmodel.viewstate.ErrorType.GenericError
import com.marcelo.marvelheroes.presentation.viewmodel.viewstate.State.Error
import com.marcelo.marvelheroes.presentation.viewmodel.viewstate.State.Loading
import com.marcelo.marvelheroes.presentation.viewmodel.viewstate.State.Success
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoritesFragment : Fragment() {

    private lateinit var binding: FragmentFavoritesBinding

    private val viewModel: FavoritesViewModel by viewModel()

    private val heroesFavoriteAdapter by lazy {
        getGenericAdapterOf {
            HeroesFavoriteViewHolder.create(it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fetchFavoritesHeroes()
        handleFavoritesHeroes()
    }

    private fun fetchFavoritesHeroes() = viewModel.getHeroesFavorites()

    private fun handleFavoritesHeroes() = viewLifecycleOwner.lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.CREATED) {
            viewModel.viewStateFavorites.collect { state ->
                showShimmer(state is Loading)
                if (state is Success) {
                    if (state.data.isNotEmpty()) {
                        showSuccess(state.data)
                    } else showEmpty()
                } else if (state is Error) {
                    when (state.errorType) {
                        is GenericError -> showError()
                        else -> {}
                    }
                }
            }
        }
    }

    private fun showShimmer(isLoading: Boolean) = with(binding) {
        val shimmer = includeShimmer.shimmerHeroes
        shimmer.isVisible = isLoading
        if (isLoading) {
            shimmer.startShimmer()
        } else {
            shimmer.stopShimmer()
            layoutShimmerHeroesFavorite.isVisible = false
            rvHeroesFavorite.isVisible = false
        }
    }

    private fun showSuccess(data: List<FavoriteItemData>) = with(binding) {
        showShimmer(isLoading = false)
        rvHeroesFavorite.apply {
            isVisible = true
            setHasFixedSize(true)
            adapter = heroesFavoriteAdapter
        }
        heroesFavoriteAdapter.submitList(data)
    }

    private fun showEmpty() = with(binding) {
        includeErrorEmpty.txtErrorLoading.text =
            getString(R.string.error_loading_favorite_heros_empty)
        layoutEmptyView.isVisible = true
    }

    private fun showError() = with(binding) {
        showShimmer(isLoading = false)
        layoutErrorView.isVisible = true
        includeErrorView.btnRetryLoading.setOnClickListener {
            showShimmer(true)
            fetchFavoritesHeroes()
        }
    }
}