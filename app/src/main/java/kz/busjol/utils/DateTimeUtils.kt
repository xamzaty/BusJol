package kz.busjol.utils

import android.annotation.SuppressLint
import android.content.Context
import android.text.format.DateUtils
import kz.busjol.R
import java.text.FieldPosition
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
object DateTimeUtils {

    const val MMMM_YYYY = "MMMM yyyy"
    const val DDoMMoYYYY = "dd.MM.yyyy"
    const val DDoMMoYYYYcHH_mm = "dd.MM.yyyy, HH:mm"
    const val DDsMMsYYYY = "dd/MM/yyyy"
    const val DDoMMoYY = "dd.MM.yy"
    const val MMoYYYY = "MM.yyyy"
    const val DD_MM_YYYY = "dd MM yyyy"
    const val D_MMMM = "d MMMM"
    const val DD_MMMM = "dd MMMM"
    const val D_MMMM_YYYY_HH_MM = "d MMMM yyyy, HH:mm"
    const val D_MMM_YYYY = "d MMM yyyy"
    const val DD_MMMM_YYYY = "dd MMMM yyyy"
    const val D_MMMM_YYYY = "d MMMM yyyy"
    const val yyyy_MM_dd = "yyyy-MM-dd"
    const val D_MMMM_HH_MM = "d MMMM HH:mm"
    const val D_MMM_YYYYo_E = "d MMM yyyy, E"
    const val DDoMMoYYYY_HH_MM_SS = "dd.MM.yyyy HH:mm:ss"
    const val ISO8601 = "yyyy-MM-dd'T'HH:mm:ssZ"
    const val YYYY_MM_DD = "yyyy-MM-dd"
    const val ISO8601_SSS = "yyyy-MM-dd'T'HH:mm:ss.SSS"
    const val ISO8601_DT = "yyyy-MM-dd'T'HH:mm:ss"
    const val DDoMMoYYYY_HH_MM = "yyyy-MM-dd HH:mm"
    const val DD_MM_YYYY_HH_MM = "dd MMMM yyyy, HH:mm"
    const val YYYY = "yyyy"
    const val MMMM = "MMMM"
    const val DD = "dd"
    const val LLLL = "LLLL"
    const val HH_MM = "HH:mm"
    const val HH_mm_ss = "HH:mm:ss"

    fun getDateAfter3Days(date: Date = Date()): Date {
        return Calendar.getInstance().apply {
            time = date
            add(Calendar.DAY_OF_MONTH, 4)
        }.time
    }

    fun getDateBefore1Month(): Date {
        return Calendar.getInstance().apply { add(Calendar.MONTH, -1) }.time
    }

    fun getDateBefore3Month(): Date {
        return Calendar.getInstance().apply { add(Calendar.MONTH, -3) }.time
    }

    fun getDateBefore3Years(): Date {
        return Calendar.getInstance().apply { add(Calendar.YEAR, -3) }.time
    }

    fun getDatePreviousYear(): Date {
        return Calendar.getInstance().apply { add(Calendar.YEAR, -1) }.time
    }

    fun getDateToday(): Date {
        return Calendar.getInstance().time
    }

    fun getDateTomorrow(): Date {
        return Calendar.getInstance().apply { add(Calendar.DAY_OF_MONTH, 1) }.time
    }

    fun getDateNextMonth(): Date {
        return Calendar.getInstance().apply { add(Calendar.MONTH, 1) }.time
    }

    fun getDatesBetween(startDate: Date, endDate: Date): List<Date> {
        val dates = arrayListOf(startDate)
        val tempDay = Calendar.getInstance().apply {
            time = startDate
        }

        while (tempDay.time < endDate) {
            dates.add(tempDay.apply { add(Calendar.DAY_OF_MONTH, 1) }.time)
        }

        return dates
    }

    fun getNextYear(): Int {
        return Calendar.getInstance().apply { add(Calendar.YEAR, 1) }.get(Calendar.YEAR)
    }

    fun currentYear(): Int = Calendar.getInstance().get(Calendar.YEAR)

    fun currentDay(): Int = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)

    fun getCurrentMonth(): Int = Calendar.getInstance().get(Calendar.MONTH)

    fun getDateMonth(): Date {
        return Calendar.getInstance().time
    }

    fun getDateMonth(month: Int): Date {
        return Calendar.getInstance().apply { add(Calendar.MONTH, month) }.time
    }
    /**
     * В случае любой ошибки возращается null
     */
    fun toDate(date: String, pattern: String) = try {
        val formatter = SimpleDateFormat(pattern, LocaleKeeper.currentLocale)
        formatter.isLenient = false
        formatter.parse(date)
    } catch (e: Exception) {
        null
    }

    @JvmStatic
    fun fromDate(date: Date, pattern: String): String = date.toString(pattern)

    fun getBirthDateAsString(birthDate: Date?): String? {
        return if (birthDate == null) null else fromDate(birthDate, DDsMMsYYYY)
    }

    fun parseBirthDateFromIin(text: String): Date? {
        if (text.length < 6) return null
        try {
            val year = text.substring(0, 2).toInt()
            val month = text.substring(2, 4).toInt()
            val day = text.substring(4, 6).toInt()
            val date = Calendar.getInstance()

            val currentYear = date.get(Calendar.YEAR) % 100

            date.set(Calendar.DAY_OF_MONTH, day)
            date.set(Calendar.MONTH, month - 1)

            if (year > currentYear) date.set(Calendar.YEAR, 1900 + year)
            else date.set(Calendar.YEAR, 2000 + year)

            return date.time
        } catch (ex: Exception) {
            // just ignore it
            return null
        }

    }

    @JvmStatic
    fun getDisplayInField(context: Context, date: Date): String {
        return date.toStringDisplayInField(context)
    }
}

fun Date.year(): Int {
    return this.toCalendar().get(Calendar.YEAR)
}

fun Date.month(): Int {
    return this.toCalendar().get(Calendar.MONTH)
}

fun Date.day(): Int {
    return this.toCalendar().get(Calendar.DAY_OF_MONTH)
}

fun Date.dayOfWeek(): Int {
    return this.toCalendar().get(Calendar.DAY_OF_WEEK)
}

fun Date.isToday(): Boolean {
    return DateUtils.isToday(time)
}

fun Date.isYesterday(): Boolean {
    return DateUtils.isToday(time + DateUtils.DAY_IN_MILLIS)
}

fun Date.isTomorrow(): Boolean {
    return DateUtils.isToday(time - DateUtils.DAY_IN_MILLIS)
}

fun Date.asStartOfQuarter(): Date = toCalendar().asStartOfQuarter().time
fun Date.asEndOfQuarter(): Date = toCalendar().asEndOfQuarter().time

fun Date.asStartOfMonth(): Date = toCalendar().asStartOfMonth().time
fun Date.asEndOfMonth(): Date = toCalendar().asEndOfMonth().time

fun Date.asStartOfWeek(): Date {
    val cal = toCalendar().asStartOfDay()
    cal.set(Calendar.DAY_OF_WEEK, cal.firstDayOfWeek)
    return cal.time
}

fun Date.asEndOfWeek(): Date {
    val cal = toCalendar().asEndOfDay()
    cal.set(Calendar.DAY_OF_WEEK, cal.firstDayOfWeek)
    cal.roll(Calendar.DAY_OF_YEAR, 6)
    return cal.time
}

fun Date.asStartOfDay(): Date = toCalendar().asStartOfDay().time
fun Date.asEndOfDay(): Date = toCalendar().asEndOfDay().time

fun Date.nextDay(): Date {
    val cal = toCalendar()
    cal.roll(Calendar.DAY_OF_YEAR, true)
    return cal.time
}

fun Date.previousDay(): Date {
    val cal = toCalendar()
    cal.roll(Calendar.DAY_OF_YEAR, false)
    return cal.time
}

fun Date.toCalendar(): Calendar {
    val cal = Calendar.getInstance()
    cal.time = this
    return cal
}

fun Date.toSmart(context: Context): String {
    return when {
        isToday() -> context.getString(R.string.today)
        isYesterday() -> context.getString(R.string.yesterday)
        else -> toString(DateTimeUtils.D_MMMM)
    }
}

fun Calendar.asStartOfQuarter(): Calendar {
    // month 0 = January
    var month = get(Calendar.MONTH)
    month -= month % 3
    set(Calendar.MONTH, month)
    return asStartOfMonth()
}

fun Calendar.asEndOfQuarter(): Calendar {
    // month 0 = January
    var month = get(Calendar.MONTH)
    month += (2 - month % 3)
    set(Calendar.MONTH, month)
    return asEndOfMonth()
}

fun Calendar.asStartOfMonth(): Calendar {
    set(Calendar.DAY_OF_MONTH, 1)
    return asStartOfDay()
}

fun Calendar.asEndOfMonth(): Calendar {
    set(Calendar.DAY_OF_MONTH, getActualMaximum(Calendar.DAY_OF_MONTH))
    return asEndOfDay()
}

fun Calendar.asStartOfDay(): Calendar {
    set(Calendar.HOUR_OF_DAY, 0)
    set(Calendar.MINUTE, 0)
    set(Calendar.SECOND, 0)
    set(Calendar.MILLISECOND, 0)
    return this
}

fun Calendar.asEndOfDay(): Calendar {
    set(Calendar.HOUR_OF_DAY, 23)
    set(Calendar.MINUTE, 59)
    set(Calendar.SECOND, 59)
    set(Calendar.MILLISECOND, 999)
    return this
}

fun Calendar.between(dateStart: Calendar, dateEnd: Calendar): Boolean {
    return this >= dateStart && this < dateEnd
}

fun Date.until(endDate: Date): List<Date> {
    return DateTimeUtils.getDatesBetween(this, endDate)
}

fun Date.toStringDisplayInField(context: Context): String {
    // todo: add translations
    val prefix = when {
        isToday() -> context.getString(R.string.today) + ", "
        isTomorrow() -> context.getString(R.string.tomorrow) + ", "
        else -> ""
    }
    return "$prefix${toString(DateTimeUtils.D_MMMM)}"
}

fun Date.toStringMonth(): String {
    return toString(DateTimeUtils.MMoYYYY)
}

fun Date.getNextMonth(pattern: String): String {
    return Calendar.getInstance().apply {
        time = this@getNextMonth
        add(Calendar.MONTH, 1)
    }.time.toString(pattern)
}

fun Date.toStringPeriod(months: Array<String>): String = "${months[month()]} ${year()}"

@SuppressLint("SimpleDateFormat")
fun Date.toString(pattern: String): String {
    val sdfFrom = SimpleDateFormat(pattern, LocaleKeeper.currentLocale)
    return sdfFrom.format(this)
}

fun String.capitalizeText(): String{
    return replaceFirstChar {
        if (it.isLowerCase()) it.titlecase(
            Locale.getDefault()
        ) else it.toString()
    }
}

@SuppressLint("SimpleDateFormat")
class MonthFormat(context: Context) : SimpleDateFormat("MMMM") {
    private val months = context.resources.getStringArray(R.array.months)
    override fun format(date: Date, toAppendTo: StringBuffer?, pos: FieldPosition?): StringBuffer {
        calendar.time = date
        var result = months[calendar.get(Calendar.MONTH)]

        calendar.get(Calendar.YEAR).takeIf { it != DateTimeUtils.currentYear() }?.let {
            result += " $it"
        }

        return StringBuffer(result)
    }
}