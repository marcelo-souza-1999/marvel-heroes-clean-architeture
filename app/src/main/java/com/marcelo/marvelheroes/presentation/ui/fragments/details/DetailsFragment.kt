package com.marcelo.marvelheroes.presentation.ui.fragments.details

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.marcelo.marvelheroes.databinding.FragmentDetailsBinding
import com.marcelo.marvelheroes.databinding.FragmentDetailsBinding.inflate
import com.marcelo.marvelheroes.domain.model.DetailParentViewData
import com.marcelo.marvelheroes.extensions.loadImage
import com.marcelo.marvelheroes.presentation.adapters.details.DetailParentAdapter
import com.marcelo.marvelheroes.presentation.viewmodel.DetailsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailsFragment : Fragment() {

    private lateinit var binding: FragmentDetailsBinding

    private val viewModel: DetailsViewModel by viewModel()

    private val args by navArgs<DetailsFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        fetchDetailsHeroes()
        handleDetailsHeroes()
    }

    private fun initView() = with(binding) {
        val getArgs = args.detailsHeroesArg
        imageHero.run {
            transitionName = getArgs.name
            loadImage(getArgs.imageUrl)
        }
        setSharedElementTransitionOnEnter()
    }

    private fun fetchDetailsHeroes() = viewModel.getHeroesDetails(args.detailsHeroesArg.heroId)

    private fun setSharedElementTransitionOnEnter() {
        TransitionInflater.from(requireContext())
            .inflateTransition(android.R.transition.move)?.let {
                sharedElementEnterTransition = it
            }
    }

    private fun handleDetailsHeroes() = lifecycleScope.launchWhenCreated {
        viewModel.viewState.collect { state ->
            showShimmer(isLoading = state.isLoading)
            showError(isError = state.error)
            showEmpty(isEmpty = state.empty)
            showSuccess(state.success)
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

    private fun showError(isError: Boolean) = with(binding) {
        showShimmer(false)
        layoutErrorView.isVisible = isError
        includeErrorView.btnRetryLoading.setOnClickListener {
            requireActivity().finish()
        }
    }

    private fun showEmpty(isEmpty: Boolean) = with(binding) {
        layoutEmptyView.isVisible = isEmpty
    }

    private fun showSuccess(data: List<DetailParentViewData>) =
        with(binding.rvDetails) {
            showShimmer(isLoading = false)
            setHasFixedSize(true)
            adapter = DetailParentAdapter(data)
        }
}