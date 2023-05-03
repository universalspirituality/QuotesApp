package com.universalspirituality.quotesfromswami

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.universalspirituality.quotesfromswami.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
// Hide the toolbar
       // supportActionBar?.hide()
        Log.d("MainActivity", "onCreate")
        val dbHelper = DatabaseHelper(this)
        // Delete all existing quotes and add new quotes
        dbHelper.deleteAndAddQuotes()

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        navController = navHostFragment.navController

        setupActionBarWithNavController(navController)

        binding.bottomNavigation.setupWithNavController(navController)
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_home -> {
                    Log.d("MainActivity", "Clicked on Home button")
                    navController.navigate(R.id.HomeFragmentPage)
                    Log.d("MainActivity", "Navigated to HomeFragmentPage")

                    true
                }
                R.id.menu_settings -> {
                    Log.d("MainActivity", "Clicked on Settings button")
                    navController.navigate(R.id.SettingsFragmentPage)
                    Log.d("MainActivity", "Navigated to SettingsFragmentPage")

                    true
                }
                R.id.menu_about -> {
                    Log.d("MainActivity", "Clicked on About button")
                    navController.navigate(R.id.AboutFragmentpage)
                    Log.d("MainActivity", "Navigated to AboutFragmentpage")

                    true
                }
                else -> false
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}
