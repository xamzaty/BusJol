package kz.busjol.ui.user

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import kz.busjol.R
import kz.busjol.base.BaseFragment
import kz.busjol.databinding.FragmentUserBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class UserFragment : BaseFragment<FragmentUserBinding>(FragmentUserBinding::inflate) {

    private val viewModel: UserViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupButtons()
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
                findNavController().navigate(R.id.action_navigation_user_to_aboutAppFragment)
            }
        }
    }
}