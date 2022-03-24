package kz.busjol

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.res.Configuration
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
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
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val userPreferences: UserPreferences by inject()
    private val viewModel: MainViewModel by viewModel()

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

        setupNavView()
        statusBarColor(R.color.white, true)
    }

    override fun onResume() {
        super.onResume()
        checkConnectivity()
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration)
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(newBase?.createConfigurationContext(Configuration().apply {
            setLocale(Locale(userPreferences.getAppLanguage().name))
        }))
    }

    private fun setupNavView() {
        navView = binding.navView

        val navHostFragment = supportFragmentManager.findFragmentById(
            R.id.nav_host_container
        ) as NavHostFragment

        val driverConfiguration = AppBarConfiguration(
            setOf(
                R.id.driverScheduleFragment, R.id.nav_driver_scan
            )
        )

        val passengerConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_search, R.id.navigation_tickets, R.id.navigation_contacts, R.id.navigation_user
            )
        )

        navController = navHostFragment.navController
        appBarConfiguration = passengerConfiguration
        navView.menu.clear()
        navView.inflateMenu(R.menu.passenger_bottom_nav_menu)
        navHostFragment.navController.setGraph(R.navigation.nav_passenger)

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        viewModel.apply {
            isDriver.observe(this@MainActivity) { isDriver ->
                if (!isDriver) {
                    setupNav(passengerConfiguration, navHostFragment, R.menu.passenger_bottom_nav_menu, R.navigation.nav_passenger)
                } else {
                    setupNav(driverConfiguration, navHostFragment, R.menu.driver_bottom_nav_menu, R.navigation.nav_driver)
                }
            }
        }

        hideBottomMenu()
    }

    private fun setupNav(configuration: AppBarConfiguration, fragment: NavHostFragment, menu: Int, nav: Int) {
        appBarConfiguration = configuration
        navView.menu.clear()
        navView.inflateMenu(menu)
        fragment.navController.setGraph(nav)
    }

    private fun hideBottomMenu() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when(destination.id) {
                R.id.navigation_search, R.id.navigation_tickets, R.id.navigation_contacts, R.id.navigation_user, R.id.nav_driver_home, R.id.driverScheduleFragment -> showMenu()
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

    private fun checkConnectivity() {
        val manager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = manager.activeNetworkInfo

        if (null == activeNetwork) {
            val dialogBuilder = AlertDialog.Builder(this)
            Intent(this, MainActivity::class.java)
            dialogBuilder.setMessage(getString(R.string.no_internet_connection_text))
                .setCancelable(false)
                .setPositiveButton(getString(R.string.no_internet_connection_retry)) { _, _ ->
                    recreate()
                }
                .setNegativeButton(getString(R.string.no_internet_connection_cancel)) { _, _ ->
                    finish()
                }

            val alert = dialogBuilder.create()
            alert.setTitle(getString(R.string.no_internet_connection_title))
            alert.setIcon(R.mipmap.ic_launcher)
            alert.show()
        }
    }
}