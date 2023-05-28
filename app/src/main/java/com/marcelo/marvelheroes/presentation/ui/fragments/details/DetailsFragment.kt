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
import androidx.recyclerview.widget.LinearLayoutManager
import com.marcelo.marvelheroes.databinding.FragmentDetailsBinding
import com.marcelo.marvelheroes.databinding.FragmentDetailsBinding.inflate
import com.marcelo.marvelheroes.domain.model.DetailParentViewData
import com.marcelo.marvelheroes.extensions.loadImage
import com.marcelo.marvelheroes.presentation.adapters.DetailParentAdapter
import com.marcelo.marvelheroes.presentation.viewmodel.DetailsViewModel
import com.marcelo.marvelheroes.presentation.viewmodel.DetailsViewModel.Companion.DetailsViewModelState.Empty
import com.marcelo.marvelheroes.presentation.viewmodel.DetailsViewModel.Companion.DetailsViewModelState.Error
import com.marcelo.marvelheroes.presentation.viewmodel.DetailsViewModel.Companion.DetailsViewModelState.Loading
import com.marcelo.marvelheroes.presentation.viewmodel.DetailsViewModel.Companion.DetailsViewModelState.Success
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
        imageHeroe.run {
            transitionName = getArgs.name
            loadImage(getArgs.imageUrl)
        }
        setSharedElementTransitionOnEnter()
    }

    private fun fetchDetailsHeroes() = viewModel.getHeroesDetails(args.detailsHeroesArg.heroeId)

    private fun setSharedElementTransitionOnEnter() {
        TransitionInflater.from(requireContext())
            .inflateTransition(android.R.transition.move)?.let {
                sharedElementEnterTransition = it
            }
    }

    private fun handleDetailsHeroes() = lifecycleScope.launchWhenCreated {
        viewModel.viewState.collect { state ->
            when (state) {
                is Loading -> showShimmer(true)
                is Error -> showError()
                is Empty -> showEmpty()
                is Success -> {
                    showShimmer(false)
                    handleSuccessState(state.data)
                }
            }
        }
    }

    private fun showShimmer(isVisibility: Boolean) =
        with(binding.includeShimmer.shimmerDetailHeroes) {
            isVisible = isVisibility
            if (isVisibility) startShimmer()
            else {
                stopShimmer()
                binding.layoutShimmer.isVisible = false
                binding.rvParentDetails.isGone = false
            }
        }

    private fun showError() = with(binding) {
        layoutShimmer.isVisible = false
        layoutErrorView.isVisible = true
        includeErrorView.btnRetryLoading.setOnClickListener {
            fetchDetailsHeroes()
            layoutShimmer.isVisible = true
            layoutErrorView.isVisible = false
        }
    }

    private fun showEmpty() = with(binding) {
        layoutShimmer.isVisible = false
        layoutErrorView.isVisible = false
        layoutErrorEmpty.isVisible = true
    }

    private fun handleSuccessState(data: List<DetailParentViewData>) =
        with(binding.rvParentDetails) {
            setHasFixedSize(true)
            val layoutManager = LinearLayoutManager(requireContext())
            this.layoutManager = layoutManager
            adapter = DetailParentAdapter(data)
        }
}