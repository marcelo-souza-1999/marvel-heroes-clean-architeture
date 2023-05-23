package com.marcelo.marvelheroes.presentation.ui.fragments.details

import android.os.Bundle
import android.transition.TransitionInflater
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.marcelo.marvelheroes.presentation.viewmodel.DetailsViewModel.DetailsViewModelState.Error
import com.marcelo.marvelheroes.presentation.viewmodel.DetailsViewModel.DetailsViewModelState.Loading
import com.marcelo.marvelheroes.presentation.viewmodel.DetailsViewModel.DetailsViewModelState.Success
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

    private fun fetchDetailsHeroes() = viewModel.getComics(args.detailsHeroesArg.heroeId)

    private fun setSharedElementTransitionOnEnter() {
        TransitionInflater.from(requireContext())
            .inflateTransition(android.R.transition.move)?.let {
                sharedElementEnterTransition = it
            }
    }

    private fun handleDetailsHeroes() = lifecycleScope.launchWhenCreated {
        viewModel.viewState.collect { state ->
            when (state) {
                is Loading -> {
                    Log.d(DetailsFragment::class.simpleName, "Loading")
                }

                is Error -> {
                    Log.d(DetailsFragment::class.simpleName, "Error")
                }

                is Success -> handleSuccessState(state.data)
            }
        }
    }

    private fun handleSuccessState(data: List<DetailParentViewData>) =
        with(binding.rvParentDetails) {
            setHasFixedSize(true)
            val layoutManager = LinearLayoutManager(requireContext())
            this.layoutManager = layoutManager
            adapter = DetailParentAdapter(data)
        }
}