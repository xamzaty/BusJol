package kz.busjol.preferences

import android.content.Context
import com.google.gson.Gson
import kz.busjol.data.Language
import kz.busjol.utils.LocaleKeeper
import java.util.*

class UserPreferences(
    context: Context
) {
    companion object {
        private const val APP_LANGUAGE = "APP_LANGUAGE"
        private const val USER_IS_AUTHORIZED = "USER_IS_AUTHORIZE"
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

    fun setUserIsAuthorizedStatus(isAuthorized: Boolean) {
        editor.putBoolean(USER_IS_AUTHORIZED, isAuthorized).apply()
    }

    fun getUserIsAuthorizedStatus(): Boolean {
        return sp.getBoolean(USER_IS_AUTHORIZED, false)
    }
}