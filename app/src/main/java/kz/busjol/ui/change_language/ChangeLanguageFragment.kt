package kz.busjol.ui.change_language

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import kz.busjol.base.BaseFragment
import kz.busjol.databinding.FragmentChangeLanguageBinding
import java.util.*

class ChangeLanguageFragment : BaseFragment<FragmentChangeLanguageBinding>(FragmentChangeLanguageBinding::inflate) {

    lateinit var locale: Locale
    private var currentLanguage = "ru"
    private var currentLang: String? = null

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


    private fun setLocale(localeName: String) {
        if (localeName != currentLanguage) {
            locale = Locale(localeName)
            val res = resources
            val dm = res.displayMetrics
            val conf = res.configuration
            conf.locale = locale
            res.updateConfiguration(conf, dm)
            val refresh = activity?.recreate()
        }
    }
}