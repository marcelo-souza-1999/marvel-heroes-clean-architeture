package com.marcelo.marvelheroes.presentation.ui.fragments.sort

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.forEach
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.marcelo.marvelheroes.R
import com.marcelo.marvelheroes.databinding.FragmentSortHeroesBinding
import com.marcelo.marvelheroes.domain.model.SortHeroesViewData
import com.marcelo.marvelheroes.presentation.viewmodel.SortHeroesViewModel
import com.marcelo.marvelheroes.presentation.viewmodel.viewstate.State
import com.marcelo.marvelheroes.utils.constants.ASCENDING
import com.marcelo.marvelheroes.utils.constants.DESCENDING
import com.marcelo.marvelheroes.utils.constants.ORDER_BY_MODIFIED_ASCENDING
import com.marcelo.marvelheroes.utils.constants.ORDER_BY_NAME_ASCENDING
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class SortHeroesFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentSortHeroesBinding

    private val viewModel: SortHeroesViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentSortHeroesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupListenerChipGroups()
        handleGetOrderHeroes()
    }

    private fun setupListenerChipGroups() = with(binding) {
        chipGroupOrderByTitle.setOnCheckedStateChangeListener { group, checkIds ->
            checkIds.forEach {
                viewModel.setOrderBy(getOrderByValue(group.findViewById<Chip>(it).id))
            }
        }

        chipGroupOrderByOptions.setOnCheckedStateChangeListener { group, checkIds ->
            checkIds.forEach {
                viewModel.setOrder(getOrderValue(group.findViewById<Chip>(it).id))
            }
        }

        btnApplySort.setOnClickListener {
            fetchSaveOrderHeroes()
            handleSaveOrderHeroes()
        }
    }

    private fun getOrderByValue(chipId: Int): String = when (chipId) {
        R.id.chipOrderName -> ORDER_BY_NAME_ASCENDING
        R.id.chipOrderModified -> ORDER_BY_MODIFIED_ASCENDING
        else -> ORDER_BY_NAME_ASCENDING
    }

    private fun getOrderValue(chipId: Int): String = when (chipId) {
        R.id.chipOrderGrowing -> ASCENDING
        R.id.chipOrderDescending -> DESCENDING
        else -> ASCENDING
    }

    private fun fetchSaveOrderHeroes() {
        viewModel.saveOrderHeroes()
    }

    private fun handleGetOrderHeroes() = viewLifecycleOwner.lifecycleScope.launch {
        viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
            viewModel.viewStateGetSortHeroes.collect { state ->
                when (state) {
                    is State.Loading -> updateUIVisibility(isLoading = true)
                    is State.Success -> {
                        updateUIVisibility(isLoading = false)
                        if (state.data is SortHeroesViewData.Success) {
                            successGetOrderHeroes(state.data)
                        }
                    }

                    is State.Error -> TODO("Será construido ainda")
                }
            }
        }
    }

    private fun successGetOrderHeroes(data: SortHeroesViewData.Success) = with(binding) {
        val (orderBy, orderOptions) = data.sortingPair

        chipGroupOrderByTitle.forEach { chip ->
            if (getOrderByValue(chip.id) == orderBy) {
                (chip as Chip).isChecked = true
            }
        }

        chipGroupOrderByOptions.forEach { chip ->
            if (getOrderValue(chip.id) == orderOptions) {
                (chip as Chip).isChecked = true
            }
        }


    }

    private fun handleSaveOrderHeroes() = viewLifecycleOwner.lifecycleScope.launch {
        viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
            viewModel.viewStateSaveSortHeroes.collect { state ->
                when (state) {
                    is State.Loading -> updateUIVisibility(isLoading = true)
                    is State.Success -> {
                        updateUIVisibility(isLoading = false)
                        successSaveOrderHeroes()
                    }

                    is State.Error -> TODO("Será construido ainda")
                }
            }
        }
    }

    private fun successSaveOrderHeroes() = with(findNavController()) {
        previousBackStackEntry?.savedStateHandle?.set(
            key = ORDER_APPLIED_BASK_STACK_KEY, value = true
        )
        popBackStack()
    }

    private fun updateUIVisibility(isLoading: Boolean) = with(binding) {
        btnApplySort.visibility = if (isLoading) View.GONE else View.VISIBLE
        progressBarSort.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private companion object {
        const val ORDER_APPLIED_BASK_STACK_KEY = "sortingAppliedBackStackKey"
    }
}
