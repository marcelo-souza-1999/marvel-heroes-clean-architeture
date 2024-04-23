package com.marcelo.marvelheroes.presentation.ui.fragments.heroes

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuProvider
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.paging.LoadState.Error
import androidx.paging.LoadState.Loading
import com.marcelo.marvelheroes.R
import com.marcelo.marvelheroes.databinding.FragmentHeroesBinding
import com.marcelo.marvelheroes.databinding.FragmentHeroesBinding.inflate
import com.marcelo.marvelheroes.domain.model.DetailsHeroesArgViewData
import com.marcelo.marvelheroes.domain.model.HeroesViewData
import com.marcelo.marvelheroes.extensions.emptyString
import com.marcelo.marvelheroes.presentation.adapters.heroes.HeroesAdapter
import com.marcelo.marvelheroes.presentation.adapters.heroes.HeroesLoadMoreAdapter
import com.marcelo.marvelheroes.presentation.adapters.heroes.HeroesRefreshDataAdapter
import com.marcelo.marvelheroes.presentation.viewmodel.HeroesViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class HeroesFragment : Fragment(), SearchView.OnQueryTextListener, MenuProvider,
    MenuItem.OnActionExpandListener {

    private lateinit var binding: FragmentHeroesBinding

    private val heroesAdapter by lazy { HeroesAdapter(::onHeroClicked) }

    private val heroesRefreshDataAdapter by lazy {
        HeroesRefreshDataAdapter(heroesAdapter::retry)
    }

    private val viewModel: HeroesViewModel by viewModel()

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.item_sort_heroes_menu, menu)

        val widgetSearch = menu.findItem(R.id.searchHeroes)
        val searchView = widgetSearch.actionView as SearchView
        widgetSearch.setOnActionExpandListener(this)

        if (viewModel.getTextSearch().isNotEmpty()) {
            widgetSearch.expandActionView()
            searchView.setQuery(viewModel.getTextSearch(), false)
        }

        with(searchView) {
            isSubmitButtonEnabled = true
            setOnQueryTextListener(this@HeroesFragment)
        }
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.sortHeroes -> {
                findNavController().navigate(R.id.action_heroesFragment_to_sortFragment)
                true
            }

            else -> false
        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return query?.let { queryData ->
            viewModel.setTextSearch(queryData)
            fetchRequestHeroesPaging()
            hideKeyboard(requireActivity())
            true
        } ?: false
    }

    override fun onQueryTextChange(newText: String?) = true

    override fun onMenuItemActionExpand(expand: MenuItem) = true

    override fun onMenuItemActionCollapse(collapsed: MenuItem): Boolean {
        viewModel.setTextSearch(emptyString())
        fetchRequestHeroesPaging()
        return true
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)
        initHeroesAdapter()
        fetchRequestHeroesPaging()
        handleHeroesPaging()
        observerStateHandle()
    }

    private fun initHeroesAdapter() = with(binding.rvHeroes) {
        postponeEnterTransition()
        setHasFixedSize(true)
        adapter = heroesAdapter.withLoadStateHeaderAndFooter(
            header = heroesRefreshDataAdapter,
            footer = HeroesLoadMoreAdapter(retryLoad = heroesAdapter::retry)
        )
        viewTreeObserver.addOnPreDrawListener {
            startPostponedEnterTransition()
            true
        }
    }

    private fun observerStateHandle() {
        val navController = findNavController()
        val navBackStackEntry = navController.getBackStackEntry(R.id.heroesFragment)
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME && navBackStackEntry.savedStateHandle.contains(
                    ORDER_APPLIED_BASK_STACK_KEY
                )
            ) {
                fetchRequestHeroesPaging()
                navBackStackEntry.savedStateHandle.remove<Boolean>(ORDER_APPLIED_BASK_STACK_KEY)
            }
        }

        navBackStackEntry.getLifecycle().addObserver(observer)

        val onDestroyObserver = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_DESTROY) {
                navBackStackEntry.getLifecycle().removeObserver(observer)
            }
        }
        navBackStackEntry.getLifecycle().addObserver(onDestroyObserver)
    }

    private fun fetchRequestHeroesPaging() = lifecycleScope.launch {
        lifecycle.repeatOnLifecycle(Lifecycle.State.CREATED) {
            viewModel.getPagingHeroes().collect { pagingData ->
                heroesAdapter.submitData(pagingData)
            }
        }
    }

    private fun handleHeroesPaging() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.CREATED) {
                heroesAdapter.loadStateFlow.collectLatest { loadState ->
                    heroesRefreshDataAdapter.loadState = loadState.mediator?.refresh?.takeIf {
                        it is Error && heroesAdapter.itemCount > ZERO
                    } ?: loadState.prepend

                    when (loadState.mediator?.refresh) {
                        is Loading -> showShimmer(true)
                        is Error -> if (heroesAdapter.itemCount == ZERO) showError()
                        is LoadState.NotLoading -> showShimmer(false)
                        null -> showError()
                    }
                }
            }
        }
    }

    private fun showShimmer(isVisibility: Boolean) = with(binding.includeShimmer.shimmerHeroes) {
        isVisible = isVisibility
        if (isVisibility) startShimmer()
        else {
            stopShimmer()
            binding.layoutShimmerHeroes.isVisible = false
            binding.rvHeroes.isGone = false
        }
    }

    private fun showError() {
        with(binding) {
            layoutShimmerHeroes.isVisible = false
            layoutError.isVisible = true
            includeError.btnRetryLoading.setOnClickListener {
                heroesAdapter.retry()
                layoutShimmerHeroes.isVisible = true
                layoutError.isVisible = false
            }
        }
    }

    private fun onHeroClicked(heroesData: HeroesViewData, view: View) {
        val extras = FragmentNavigatorExtras(
            view to heroesData.name
        )

        val directions = HeroesFragmentDirections.actionOpenDetailsFragment(
            heroesData.name, DetailsHeroesArgViewData(
                heroId = heroesData.id, name = heroesData.name, imageUrl = heroesData.imageUrl
            )
        )

        findNavController().navigate(directions, extras)
    }

    private fun hideKeyboard(activity: Activity) {
        val inputMethodManager =
            activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        val currentFocusedView = activity.currentFocus
        currentFocusedView?.let {
            inputMethodManager.hideSoftInputFromWindow(
                it.windowToken, InputMethodManager.HIDE_NOT_ALWAYS
            )
        }
    }

    private companion object {
        const val ZERO = 0
        const val ORDER_APPLIED_BASK_STACK_KEY = "sortingAppliedBackStackKey"
    }
}
