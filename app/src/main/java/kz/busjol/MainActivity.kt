package kz.busjol

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import kz.busjol.databinding.ActivityMainBinding
import kz.busjol.ext.ActivityExt.statusBarColor
import kz.busjol.preferences.UserPreferences
import org.koin.android.ext.android.inject
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val userPreferences: UserPreferences by inject()

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_BusJol)
        super.onCreate(savedInstanceState)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        supportActionBar?.hide()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navView = binding.navView

        val navHostFragment = supportFragmentManager.findFragmentById(
            R.id.nav_host_container
        ) as NavHostFragment

        navController = navHostFragment.navController
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_search, R.id.navigation_tickets, R.id.navigation_contacts, R.id.navigation_user
            )
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        hideBottomMenu()
        statusBarColor(R.color.white, true)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration)
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(newBase?.createConfigurationContext(Configuration().apply {
            setLocale(Locale(userPreferences.getAppLanguage().name))
        }))
    }

    private fun hideBottomMenu() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when(destination.id) {
                R.id.navigation_search, R.id.navigation_tickets, R.id.navigation_contacts, R.id.navigation_user, R.id.nav_driver_home -> showMenu()
                else -> hideMenu()
            }
        }
    }

    private fun showMenu() {
        navView.visibility = View.VISIBLE
    }

    private fun hideMenu() {
        navView.visibility = View.GONE
    }
}