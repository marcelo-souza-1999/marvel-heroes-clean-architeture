package com.marcelo.marvelheroes.presentation.ui.fragments.details

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.marcelo.marvelheroes.databinding.FragmentDetailsBinding
import com.marcelo.marvelheroes.databinding.FragmentDetailsBinding.inflate
import com.marcelo.marvelheroes.extensions.loadImage

class DetailsFragment : Fragment() {

    private lateinit var binding: FragmentDetailsBinding

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
    }

    private fun initView() = with(binding) {
        val getArgs = args.detailsHeroesArg
        imageHeroe.run {
            transitionName = getArgs.name
            loadImage(getArgs.imageUrl)
        }

        setSharedElementTransitionOnEnter()
    }

    private fun setSharedElementTransitionOnEnter() {
        TransitionInflater.from(requireContext())
            .inflateTransition(android.R.transition.move)?.let {
                sharedElementEnterTransition = it
            }
    }
}