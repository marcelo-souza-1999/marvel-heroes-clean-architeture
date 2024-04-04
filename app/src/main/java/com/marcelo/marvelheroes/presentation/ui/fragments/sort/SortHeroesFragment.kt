package com.marcelo.marvelheroes.presentation.ui.fragments.sort

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.forEach
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.marcelo.marvelheroes.R
import com.marcelo.marvelheroes.databinding.FragmentSortHeroesBinding
import com.marcelo.marvelheroes.domain.model.SortHeroesViewData
import com.marcelo.marvelheroes.presentation.viewmodel.SortHeroesViewModel
import com.marcelo.marvelheroes.presentation.viewmodel.viewstate.State
import com.marcelo.marvelheroes.utils.constants.ASCENDING
import com.marcelo.marvelheroes.utils.constants.DESCENDING
import com.marcelo.marvelheroes.utils.constants.MODIFIED
import com.marcelo.marvelheroes.utils.constants.NAME
import com.marcelo.marvelheroes.utils.constants.ORDER_BY_MODIFIED_ASCENDING
import com.marcelo.marvelheroes.utils.constants.ORDER_BY_NAME_ASCENDING
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class SortHeroesFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentSortHeroesBinding

    private val viewModel: SortHeroesViewModel by viewModel()

    private var orderBy = NAME

    private var order = MODIFIED

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

    private fun setupListenerChipGroups() {
        with(binding) {
            chipGroupOrderByTitle.setOnCheckedChangeListener { _, checkedId ->
                orderBy = getOrderByValue(checkedId)
            }

            chipGroupOrderByOptions.setOnCheckedChangeListener { _, checkedId ->
                order = getOrderValue(checkedId)
            }

            btnApplySort.setOnClickListener {
                fetchSaveOrderHeroes()
                handleSaveOrderHeroes()
            }
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
        viewModel.saveOrderHeroes(orderBy, order)
    }

    private fun handleGetOrderHeroes() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.viewStateGetSortHeroes.collect { state ->
                    when (state) {
                        is State.Loading -> {
                            binding.btnApplySort.visibility = View.GONE
                            binding.progressBarSort.visibility = View.VISIBLE
                        }

                        is State.Success -> {
                            binding.btnApplySort.visibility = View.VISIBLE
                            binding.progressBarSort.visibility = View.GONE
                            if (state.data is SortHeroesViewData.Success) {
                                successGetOrderHeroes(state.data)
                            }
                        }

                        else -> {}
                    }
                }
            }
        }
    }

    private fun successGetOrderHeroes(data: SortHeroesViewData.Success) {
        with(binding) {
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
    }

    private fun handleSaveOrderHeroes() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.viewStateSaveSortHeroes.collect { state ->
                    when (state) {
                        is State.Loading -> {
                            binding.btnApplySort.visibility = View.GONE
                            binding.progressBarSort.visibility = View.VISIBLE
                        }

                        is State.Success -> {
                            binding.btnApplySort.visibility = View.VISIBLE
                            binding.progressBarSort.visibility = View.GONE
                        }

                        else -> {}
                    }
                }
            }
        }
    }
}
