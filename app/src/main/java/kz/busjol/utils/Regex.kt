package kz.busjol.utils

import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.FragmentActivity
import java.util.regex.Pattern

object Regex {

    private val EMAIL_ADDRESS_PATTERN: Pattern = Pattern.compile(
        "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                "\\@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+"
    )

    fun isValidEmail(str: String) = EMAIL_ADDRESS_PATTERN.matcher(str).matches()

    fun checkValidEmail(str: String, activity: FragmentActivity?, @StringRes textId:  Int) = when {
        isValidEmail(str) -> true

        else -> {
            Toast.makeText(activity, textId, Toast.LENGTH_SHORT).show()
            false
        }
    }
}