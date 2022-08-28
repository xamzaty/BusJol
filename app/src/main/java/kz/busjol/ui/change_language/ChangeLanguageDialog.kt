package kz.busjol.ui.change_language

import android.os.Bundle
import android.view.View
import kz.busjol.base.BaseBottomFragmentDialog
import kz.busjol.databinding.DialogChangeLanguageBinding
import kz.busjol.ext.FragmentExt.observe
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

private const val KAZAKH_LANGUAGE = "kk"
private const val RUSSIAN_LANGUAGE = "ru"

class ChangeLanguageDialog :  BaseBottomFragmentDialog<DialogChangeLanguageBinding>(DialogChangeLanguageBinding::inflate, false) {

    private val viewModel: ChangeLanguageViewModel by viewModel()

    lateinit var locale: Locale
    private var currentLanguage = RUSSIAN_LANGUAGE
    private var currentLang: String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupButtons()
        setupObservers()
    }

    private fun setupButtons() {
        binding.apply {
            closeButton.setOnClickListener { dismiss() }

            buttonKazakhLanguage.setOnClickListener {
                kazakhLanguageSelector.visibility = View.VISIBLE
                russianLanguageSelector.visibility = View.GONE
                setLocale(KAZAKH_LANGUAGE)
            }

            buttonRussianLanguage.setOnClickListener {
                kazakhLanguageSelector.visibility = View.GONE
                russianLanguageSelector.visibility = View.VISIBLE
                setLocale(RUSSIAN_LANGUAGE)
            }
        }
    }

    private fun setupObservers() {
        viewModel.appLanguage.observe(viewLifecycleOwner) { appLanguage ->
            binding.apply {
                if (appLanguage == "ru") {
                    kazakhLanguageSelector.visibility = View.GONE
                    russianLanguageSelector.visibility = View.VISIBLE
                } else {
                    kazakhLanguageSelector.visibility = View.VISIBLE
                    russianLanguageSelector.visibility = View.GONE
                }
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