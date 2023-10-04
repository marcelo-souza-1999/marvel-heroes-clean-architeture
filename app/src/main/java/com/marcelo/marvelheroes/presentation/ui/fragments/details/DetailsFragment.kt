package com.marcelo.marvelheroes.presentation.ui.fragments.details

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.marcelo.marvelheroes.R
import com.marcelo.marvelheroes.databinding.FragmentDetailsBinding
import com.marcelo.marvelheroes.databinding.FragmentDetailsBinding.inflate
import com.marcelo.marvelheroes.domain.model.DetailParentViewData
import com.marcelo.marvelheroes.extensions.loadImage
import com.marcelo.marvelheroes.presentation.adapters.details.DetailParentAdapter
import com.marcelo.marvelheroes.presentation.viewmodel.DetailsViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailsFragment : Fragment() {

    private lateinit var binding: FragmentDetailsBinding

    private val viewModel: DetailsViewModel by viewModel()

    private val args by navArgs<DetailsFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        fetchDetailsHeroes()
        handleDetailsHeroes()
        handleSaveHeroFavorite()
        fetchCheckHeroFavorite()
        handleCheckHeroFavorite()
        saveHeroFavoriteListener()
    }

    private fun initView() = with(binding) {
        imageHero.run {
            transitionName = args.detailsHeroesArg.name
            loadImage(args.detailsHeroesArg.imageUrl)
        }
        setSharedElementTransitionOnEnter()
    }

    private fun setSharedElementTransitionOnEnter() {
        TransitionInflater.from(requireContext()).inflateTransition(android.R.transition.move)
            ?.let {
                sharedElementEnterTransition = it
            }
    }

    private fun fetchDetailsHeroes() = viewModel.getHeroesDetails(args.detailsHeroesArg.heroId)

    private fun handleDetailsHeroes() = viewLifecycleOwner.lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.CREATED) {
            viewModel.viewState.collect { state ->
                showShimmer(isLoading = state.isLoading)
                showErrorHeroes(isError = state.error)
                showEmpty(isEmpty = state.empty)
                showSuccess(state.success)
            }
        }
    }

    private fun handleSaveHeroFavorite() = viewLifecycleOwner.lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.CREATED) {
            viewModel.viewStateFavorite.collect { state ->
                setupLoading(isLoading = state.isLoading)
                setupSaveHero(
                    isSuccessIcon = state.successIcon
                )
            }
        }
    }

    private fun showShimmer(isLoading: Boolean) = with(binding.includeShimmer.shimmerDetailHeroes) {
        isVisible = isLoading
        if (isLoading) startShimmer()
        else {
            stopShimmer()
            binding.layoutShimmerDetails.isVisible = false
            binding.rvDetails.isGone = false
        }
    }

    private fun showErrorHeroes(isError: Boolean) = with(binding) {
        showShimmer(false)
        layoutErrorView.isVisible = isError
        includeErrorView.btnRetryLoading.setOnClickListener {
            requireActivity().finish()
        }
    }

    private fun showEmpty(isEmpty: Boolean) = with(binding) {
        layoutEmptyView.isVisible = isEmpty
    }

    private fun showSuccess(data: List<DetailParentViewData>) = with(binding.rvDetails) {
        showShimmer(isLoading = false)
        setHasFixedSize(true)
        adapter = DetailParentAdapter(data)
    }

    private fun fetchSaveHeroFavorite() = viewModel.saveHeroFavorite(
        heroId = args.detailsHeroesArg.heroId,
        heroName = args.detailsHeroesArg.name,
        heroImageUrl = args.detailsHeroesArg.imageUrl
    )

    private fun fetchDeleteHeroFavorite() = viewModel.deleteFavorite(
        heroId = args.detailsHeroesArg.heroId
    )

    private fun setupLoading(isLoading: Boolean) = with(binding) {
        if (isLoading) {
            imageFavorite.isVisible = false
            progressBarFavorite.isVisible = true
        } else {
            imageFavorite.isVisible = true
            progressBarFavorite.isVisible = false
        }
    }

    private fun setupSaveHero(isSuccessIcon: Int) =
        with(binding.imageFavorite) {
            if (isSuccessIcon == R.drawable.ic_favorite_hero) {
                setImageResource(R.drawable.ic_not_favorite_hero)
                setOnClickListener {
                    fetchDeleteHeroFavorite()
                }
            } else {
                setImageResource(R.drawable.ic_favorite_hero)
                setOnClickListener {
                    fetchSaveHeroFavorite()
                }
            }
        }

    private fun saveHeroFavoriteListener() = with(binding.imageFavorite) {
        setOnClickListener {
            fetchSaveHeroFavorite()
        }
    }

    private fun fetchCheckHeroFavorite() = viewModel.checkFavorite(
        heroId = args.detailsHeroesArg.heroId
    )

    private fun handleCheckHeroFavorite() = viewLifecycleOwner.lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.CREATED) {
            viewModel.viewStateFavorite.collect { state ->
                setupLoading(isLoading = state.isLoading)
                setupCheckFavorite(icon = state.successIcon)
            }
        }
    }

    private fun setupCheckFavorite(icon: Int) = with(binding.imageFavorite) {
        setImageResource(icon)
    }
}