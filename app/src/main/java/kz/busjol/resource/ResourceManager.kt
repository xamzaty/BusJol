package kz.busjol.resource

import androidx.annotation.ArrayRes
import androidx.annotation.StringRes

interface ResourceManager {
    fun getString(@StringRes resource: Int, vararg formatArgs: Any?): String

    fun getQuantityString(resource: Int, quantity: Int): String

    fun getColor(resource: Int): Int

    fun getStrings(@ArrayRes stringArrayRes: Int): List<String>
}