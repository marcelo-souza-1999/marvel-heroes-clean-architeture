package com.marcelo.marvelheroes.presentation.ui.activitys

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.marcelo.marvelheroes.R
import com.marcelo.marvelheroes.databinding.ActivityNavigationBinding.inflate

class NavigationActivity : AppCompatActivity() {

    private val binding by lazy { inflate(layoutInflater).apply { setContentView(root) } }

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupNavHost()
        setupToolbar()
    }

    private fun setupNavHost() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHostContainer) as NavHostFragment

        navController = navHostFragment.navController
        binding.bottomNavigation.setupWithNavController(navController)
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.heroesFragment,
                R.id.favoritesFragment,
                R.id.aboutFragment
            )
        )
    }

    private fun setupToolbar() = with(binding.toolbarMain) {
        setupWithNavController(navController, appBarConfiguration)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            val isTopLevelDestination =
                appBarConfiguration.topLevelDestinations.contains(destination.id)
            if (!isTopLevelDestination) {
                setNavigationIcon(R.drawable.ic_arrow)
            }
        }
    }
}