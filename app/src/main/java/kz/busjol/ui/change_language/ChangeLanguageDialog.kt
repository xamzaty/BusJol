package kz.busjol.ui.change_language

import android.os.Bundle
import android.view.View
import kz.busjol.base.BaseBottomFragmentDialog
import kz.busjol.databinding.DialogChangeLanguageBinding
import java.util.*

private const val KAZAKH_LANGUAGE = "kk"
private const val RUSSIAN_LANGUAGE = "ru"

class ChangeLanguageDialog :  BaseBottomFragmentDialog<DialogChangeLanguageBinding>(DialogChangeLanguageBinding::inflate, false) {

    lateinit var locale: Locale
    private var currentLanguage = RUSSIAN_LANGUAGE
    private var currentLang: String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupButtons()
    }

    private fun setupButtons() {
        binding.apply {
            closeButton.setOnClickListener { dismiss() }

            buttonKazakhLanguage.setOnClickListener {
                kazakhLanguageSelector.visibility = View.VISIBLE
                russianLanguageSelector.visibility = View.GONE
                dismiss()
                setLocale(KAZAKH_LANGUAGE)
                activity?.recreate()
            }

            buttonRussianLanguage.setOnClickListener {
                kazakhLanguageSelector.visibility = View.GONE
                russianLanguageSelector.visibility = View.VISIBLE
                dismiss()
                setLocale(RUSSIAN_LANGUAGE)
                activity?.recreate()
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