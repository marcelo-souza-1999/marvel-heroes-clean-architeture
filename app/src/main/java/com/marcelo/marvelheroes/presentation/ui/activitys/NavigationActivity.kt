package com.marcelo.marvelheroes.presentation.ui.activitys

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.marcelo.marvelheroes.R
import com.marcelo.marvelheroes.databinding.ActivityNavigationBinding
import com.marcelo.marvelheroes.databinding.ActivityNavigationBinding.inflate

class NavigationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNavigationBinding
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = inflate(layoutInflater)
        setContentView(binding.root)

        setupNavHost()
        setupToolbar()
    }

    private fun setupNavHost() {
        navHostFragment =
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

    private fun setupToolbar() {
        with(binding.toolbarMain) {
            setupWithNavController(navController, appBarConfiguration)

            navController.addOnDestinationChangedListener { _, destination, _ ->
                val isTopLevelDestination =
                    destination.id in appBarConfiguration.topLevelDestinations
                if (!isTopLevelDestination) {
                    setNavigationIcon(R.drawable.ic_arrow)
                }
            }
        }
    }

}
