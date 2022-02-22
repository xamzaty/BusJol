package kz.busjol.ui.tickets

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import kz.busjol.R
import kz.busjol.base.BaseFragment
import kz.busjol.databinding.FragmentTicketsBinding

class TicketsFragment : BaseFragment<FragmentTicketsBinding>(FragmentTicketsBinding::inflate) {

    private lateinit var ticketsViewModel: TicketsViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupButtons()
    }

    private fun setupButtons() {
        binding.buttonMyTicketsAuthorizationEnter.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_tickets_to_navigation_user)
        }
    }
}