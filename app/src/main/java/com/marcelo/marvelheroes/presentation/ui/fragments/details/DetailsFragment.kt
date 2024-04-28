package com.marcelo.marvelheroes.presentation.ui.fragments.details

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.marcelo.marvelheroes.presentation.viewmodel.viewstate.ErrorType.GenericError
import com.marcelo.marvelheroes.presentation.viewmodel.viewstate.ErrorType.NetworkError
import com.marcelo.marvelheroes.presentation.viewmodel.viewstate.ErrorType.ServerError
import com.marcelo.marvelheroes.presentation.viewmodel.viewstate.State.Error
import com.marcelo.marvelheroes.presentation.viewmodel.viewstate.State.Loading
import com.marcelo.marvelheroes.presentation.viewmodel.viewstate.State.Success
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailsFragment : Fragment() {

    private lateinit var binding: FragmentDetailsBinding

    private val viewModel: DetailsViewModel by viewModel()

    private val args by navArgs<DetailsFragmentArgs>()

    private var isFavorite: Boolean = false

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
        saveHeroFavoriteListener()
        handleSaveHeroFavorite()
        fetchCheckHeroFavorite()
        handleCheckHeroFavorite()
        handleDeleteHeroFavorite()
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
            viewModel.viewStateDetails.collect { state ->
                when (state) {
                    is Loading -> showShimmer(true)

                    is Success -> {
                        if (state.data.isNotEmpty()) {
                            showSuccess(data = state.data)
                        } else {
                            showEmpty()
                        }
                    }

                    is Error -> {
                        when (state.errorType) {
                            is NetworkError -> showErrorHeroes(isNetwork = true)
                            is GenericError -> showErrorHeroes()
                            is ServerError -> showErrorHeroes(isErrorServer = true)
                        }
                    }
                }
            }
        }
    }

    private fun showShimmer(isLoading: Boolean) = with(binding) {
        val shimmer = includeShimmer.shimmerDetailHeroes
        shimmer.isVisible = isLoading
        if (isLoading) {
            shimmer.startShimmer()
        } else {
            shimmer.stopShimmer()
            layoutShimmerDetails.isVisible = false
            rvDetails.isVisible = false
        }
    }

    private fun showErrorHeroes(
        isNetwork: Boolean = false,
        isErrorServer: Boolean = false
    ) = with(binding) {
        showShimmer(false)
        layoutErrorView.isVisible = true
        includeErrorView.btnRetryLoading.setOnClickListener {
            showShimmer(true)
            fetchDetailsHeroes()
        }
        if (isNetwork) {
            includeErrorView.txtErrorLoading.text = getString(R.string.error_network)
        } else if (isErrorServer) {
            includeErrorView.txtErrorLoading.text = getString(R.string.error_server)
            includeErrorView.btnRetryLoading.isVisible = false
        }
    }

    private fun showSuccess(data: List<DetailParentViewData>) = with(binding) {
        showShimmer(isLoading = false)
        layoutErrorView.isVisible = false
        rvDetails.apply {
            isVisible = true
            setHasFixedSize(true)
            adapter = DetailParentAdapter(data)
        }
    }

    private fun showEmpty() = with(binding) {
        layoutEmptyView.isVisible = true
    }

    private fun fetchSaveHeroFavorite() = viewModel.saveHeroFavorite(
        heroId = args.detailsHeroesArg.heroId,
        heroName = args.detailsHeroesArg.name,
        heroImageUrl = args.detailsHeroesArg.imageUrl
    )

    private fun handleSaveHeroFavorite() = viewLifecycleOwner.lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.CREATED) {
            viewModel.viewStateSaveFavorite.collect { state ->
                setupLoadingFavorite(state is Loading)
                isFavorite = when (state) {
                    is Success -> true
                    is Error -> false
                    else -> false
                }
                setIconFavorite(isFavorite)
            }
        }
    }


    private fun fetchCheckHeroFavorite() = viewModel.checkHeroFavorite(
        heroId = args.detailsHeroesArg.heroId
    )

    private fun handleCheckHeroFavorite() = viewLifecycleOwner.lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.CREATED) {
            viewModel.viewStateCheckFavorite.collect { state ->
                setupLoadingFavorite(state is Loading)
                isFavorite = when (state) {
                    is Success -> state.data
                    is Error -> false
                    else -> false
                }
                setIconFavorite(isFavorite)
            }
        }
    }

    private fun fetchDeleteHeroFavorite() = viewModel.deleteHeroFavorite(
        heroId = args.detailsHeroesArg.heroId
    )

    private fun handleDeleteHeroFavorite() = viewLifecycleOwner.lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.CREATED) {
            viewModel.viewStateDeleteFavorite.collect { state ->
                setupLoadingFavorite(state is Loading)
                isFavorite = when (state) {
                    is Success -> {
                        setIconFavorite(isSuccessIcon = !state.data)
                        !state.data
                    }

                    is Error -> {
                        setIconFavorite(isSuccessIcon = false)
                        false
                    }

                    else -> {
                        false
                    }
                }
            }
        }
    }


    private fun setupLoadingFavorite(isLoading: Boolean) = with(binding) {
        imageFavorite.isVisible = !isLoading
        progressBarFavorite.isVisible = isLoading
    }

    private fun setIconFavorite(isSuccessIcon: Boolean) = with(binding.imageFavorite) {
        if (isSuccessIcon) {
            setImageResource(R.drawable.ic_favorite_hero)
        } else {
            setImageResource(R.drawable.ic_not_favorite_hero)
        }
    }

    private fun saveHeroFavoriteListener() = with(binding.imageFavorite) {
        setOnClickListener {
            if (isFavorite) {
                fetchDeleteHeroFavorite()
            } else {
                fetchSaveHeroFavorite()
            }
        }
    }
}