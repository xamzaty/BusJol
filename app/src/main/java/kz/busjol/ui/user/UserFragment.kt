package kz.busjol.ui.user

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import kz.busjol.R
import kz.busjol.base.BaseFragment
import kz.busjol.databinding.FragmentUserBinding
import kz.busjol.ext.FragmentExt.setVisibility
import org.koin.androidx.viewmodel.ext.android.viewModel

class UserFragment : BaseFragment<FragmentUserBinding>(FragmentUserBinding::inflate) {

    private val viewModel: UserViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupButtons()
        setupObservers()
    }

    private fun setupButtons() {
        binding.apply {
            enterButton.setOnClickListener {
                findNavController().navigate(R.id.action_navigation_user_to_enterUserDialog)
            }

            changeLanguageButton.setOnClickListener {
                findNavController().navigate(R.id.action_navigation_user_to_changeLanguageFragment)
            }

            aboutAppButton.setOnClickListener {
                findNavController().navigate(R.id.action_navigation_user_to_rateAppDialog)
            }

            authorizedExitButton.setOnClickListener {
                exitAuthorizedStatus()
            }
        }
    }

    private fun exitAuthorizedStatus() {
        viewModel.apply {
            isDriver.observe(viewLifecycleOwner) { isDriver ->
                if (isDriver) { exitDriverStatus() }
            }
            isUserAuthorized.observe(viewLifecycleOwner) { userIsAuthorized ->
                if (userIsAuthorized) { exitUserAuthorizedStatus() }
            }
        }
    }

    private fun setupObservers() {
        viewModel.apply {
            isDriver.observe(viewLifecycleOwner) { isDriver ->
                driverModeViewsVisibility(isDriver)
            }
            isUserAuthorized.observe(viewLifecycleOwner) { userIsAuthorized ->
                authorizedUserModeViewsVisibility(userIsAuthorized)
            }
        }
    }

    private fun driverModeViewsVisibility(isDriver: Boolean) {
        binding.apply {
            if (isDriver) {
                setVisibility(false, arrayOf(nonAuthorizedLayout, authorizedMyPassengers,
                    authorizedPassengersButton, authorizedMyPassengersArrow, view11)
                )
                setVisibility(true, arrayOf(authorizedLayout, authorizedMyTripsButton, authorizedExitButton,
                    authorizedMyTrips, authorizedMyTripsArrow, view12)
                )
            } else {
                setVisibility(true, arrayOf(nonAuthorizedLayout, authorizedMyPassengers,
                    authorizedPassengersButton, authorizedMyPassengersArrow, view11)
                )
                setVisibility(false, arrayOf(authorizedLayout, authorizedMyTripsButton, authorizedExitButton,
                    authorizedMyTrips, authorizedMyTripsArrow, view12)
                )
            }
        }
    }

    private fun authorizedUserModeViewsVisibility(userIsAuthorized: Boolean) {
        binding.apply {
            if (userIsAuthorized) {
                setVisibility(false, arrayOf(nonAuthorizedLayout, authorizedMyTripsButton,
                    authorizedMyTrips, authorizedMyTripsArrow, view12)
                )
                setVisibility(true, arrayOf(authorizedLayout, authorizedMyPassengers, authorizedExitButton,
                    authorizedPassengersButton, authorizedMyPassengersArrow, view11)
                )
            } else {
                setVisibility(true, arrayOf(nonAuthorizedLayout, authorizedMyTripsButton,
                    authorizedMyTrips, authorizedMyTripsArrow, view12)
                )
                setVisibility(false, arrayOf(authorizedLayout, authorizedMyPassengers, authorizedExitButton,
                    authorizedPassengersButton, authorizedMyPassengersArrow, view11)
                )
            }
        }
    }
}