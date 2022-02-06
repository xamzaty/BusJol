package kz.busjol

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import kz.busjol.databinding.ActivityMainBinding
import kz.busjol.ext.ActivityExt.statusBarColor
import kz.busjol.utils.Coroutines

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        supportActionBar?.hide()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_search, R.id.navigation_user
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        hideBottomMenu()
        statusBarColor(R.color.white, true)
    }

    private fun hideBottomMenu() {

        findNavController(R.id.nav_host_fragment_activity_main).addOnDestinationChangedListener { _, destination, _ ->
            when(destination.id) {
                R.id.navigation_search, R.id.navigation_tickets, R.id.navigation_contacts, R.id.navigation_user -> showMenu()
                else -> hideMenu()
            }
        }
    }

    private fun showMenu() {
        val bottomMenu = binding.navView
        bottomMenu.visibility = View.VISIBLE
    }

    private fun hideMenu() {
        val bottomMenu = binding.navView
        bottomMenu.visibility = View.GONE
    }
}