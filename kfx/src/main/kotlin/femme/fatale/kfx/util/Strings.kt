package femme.fatale.kfx.util

import java.util.*

fun String.titlecased(locale: Locale = Locale.getDefault()): String {
    return if (length > 1)
        this[0].uppercase(locale) + substring(1).lowercase(locale)
    else
        lowercase(locale)
}
