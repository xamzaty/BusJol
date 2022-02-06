package kz.busjol.preferences

import android.content.Context
import com.google.gson.Gson
import kz.busjol.data.Language
import kz.busjol.utils.LocaleKeeper
import java.util.*

class UserPreferences(
    context: Context,
    val gson: Gson
) {
    companion object {
        private const val APP_LANGUAGE = "APP_LANGUAGE"
    }

    private var sp = context.getSharedPreferences("PassengerPreferences", Context.MODE_PRIVATE)
    private var editor = sp.edit()

    fun setAppLanguage(lang: Language) {
        editor.putString(APP_LANGUAGE, lang.name).apply()
        LocaleKeeper.currentLocale = Locale(lang.name.lowercase())
    }

    fun getAppLanguage(): Language {
        return Language.valueOf(sp.getString(APP_LANGUAGE, Language.RU.name)!!)
    }
}