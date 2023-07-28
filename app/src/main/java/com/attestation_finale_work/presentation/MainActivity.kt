package com.attestation_finale_work.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.attestation_finale_work.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        val navController = navHostFragment.navController

        val bottomNavView = findViewById<BottomNavigationView>(R.id.bottomNavigationBar)
        bottomNavView.setupWithNavController(navController)

        val destinationChangesListener =
            NavController.OnDestinationChangedListener { _, destination, _ ->
                bottomNavView.isVisible = destination.id in listOf(R.id.profile, R.id.favorites, R.id.fragment_subreddits)
            }

        navController.addOnDestinationChangedListener(destinationChangesListener)
    }
}