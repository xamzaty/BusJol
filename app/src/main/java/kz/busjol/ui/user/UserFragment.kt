package kz.busjol.ui.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import kz.busjol.BuildConfig
import kz.busjol.R
import kz.busjol.base.BaseFragment
import kz.busjol.databinding.FragmentUserBinding

class UserFragment : BaseFragment<FragmentUserBinding>(FragmentUserBinding::inflate) {

    private val userViewModel: UserViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupButtons()
    }

    private fun setupButtons() {
        binding.apply {
            changeLanguageButton.setOnClickListener {
                findNavController().navigate(R.id.action_navigation_user_to_changeLanguageFragment)
            }
            aboutAppButton.setOnClickListener {
                findNavController().navigate(R.id.action_navigation_user_to_aboutAppFragment)
            }
        }
    }
}