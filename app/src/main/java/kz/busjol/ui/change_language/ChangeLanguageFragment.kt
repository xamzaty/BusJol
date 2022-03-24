package kz.busjol.ui.change_language

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import kz.busjol.base.BaseFragment
import kz.busjol.databinding.FragmentChangeLanguageBinding

class ChangeLanguageFragment : BaseFragment<FragmentChangeLanguageBinding>(FragmentChangeLanguageBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupButtons()
    }

    private fun setupButtons() {
        binding.apply {
            backButton.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }
}