package kz.busjol.ui.tickets

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import kz.busjol.R
import kz.busjol.base.BaseFragment
import kz.busjol.databinding.FragmentTicketsBinding
import kz.busjol.ext.FragmentExt.navigate
import kz.busjol.ext.FragmentExt.setVisibility
import org.koin.androidx.viewmodel.ext.android.viewModel

class TicketsFragment : BaseFragment<FragmentTicketsBinding>(FragmentTicketsBinding::inflate) {

    private val viewModel: TicketsViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupButtons()
        setupObservers()
    }

    private fun setupButtons() {
        binding.buttonMyTicketsAuthorizationEnter.setOnClickListener {
            navigate(R.id.action_navigation_tickets_to_navigation_user)
        }
    }

    private fun setupObservers() {
        viewModel.apply {
            binding.apply {
                isUserAuthorized.observe(viewLifecycleOwner) { isUserAuthorized ->
                    if (isUserAuthorized) {
                        noTicketsTextAuthorized.visibility = View.VISIBLE
                        setVisibility(false, arrayOf(noTicketsTextNotAuthorizeTitle,
                            noTicketsTextNotAuthorizeText, buttonMyTicketsAuthorizationEnter)
                        )
                    } else {
                        noTicketsTextAuthorized.visibility = View.GONE
                        setVisibility(true, arrayOf(noTicketsTextNotAuthorizeTitle,
                            noTicketsTextNotAuthorizeText, buttonMyTicketsAuthorizationEnter)
                        )
                    }
                }
            }
        }
    }
}