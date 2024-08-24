package femme.fatale.kfx.util

import java.io.InputStream
import java.net.URL
import java.util.*
import kotlin.reflect.KClass

operator fun ResourceBundle.get(name: String): String =
    if (containsKey(name)) getString(name) else name

val KClass<*>.className: String
    get() = this.qualifiedName!!

@Suppress("NOTHING_TO_INLINE")
inline fun KClass<*>.findUrl(name: String): URL? =
    this.java.getResource(name)

@Suppress("NOTHING_TO_INLINE")
inline fun KClass<*>.getUrl(name: String): URL =
    findUrl(name) ?: error("$name not found loading ${this.qualifiedName}")

@Suppress("NOTHING_TO_INLINE")
inline fun KClass<*>.findStream(name: String): InputStream? =
    this.java.getResourceAsStream(name)

@Suppress("NOTHING_TO_INLINE")
inline fun KClass<*>.getStream(name: String): InputStream =
    findStream(name) ?: error("$name not found loading ${this.qualifiedName}")
