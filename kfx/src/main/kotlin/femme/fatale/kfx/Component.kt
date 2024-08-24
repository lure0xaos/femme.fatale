package femme.fatale.kfx

import femme.fatale.kfx.util.className
import femme.fatale.kfx.util.findUrl
import javafx.application.Platform
import javafx.scene.Scene
import javafx.scene.image.Image
import javafx.stage.Stage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.javafx.JavaFx
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import java.net.URL
import java.util.*
import java.util.prefs.Preferences
import kotlin.coroutines.CoroutineContext
import kotlin.reflect.KFunction2

abstract class Component : KoinComponent, CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.JavaFx

    val logger: System.Logger by lazy {
        runCatching {
            ResourceBundle.getBundle(this::class.className)
        }.getOrNull()?.let { System.getLogger(this::class.className, it) }
            ?: System.getLogger(this::class.className)
    }

    @Suppress("NOTHING_TO_INLINE")
    inline fun findMessages(): ResourceBundle? = runCatching {
        ResourceBundle.getBundle(this::class.className)
    }.onFailure {
        System.getLogger(this::class.className)
            .log(System.Logger.Level.WARNING, (it as MissingResourceException).message)
    }.getOrNull()

    inline val messages: ResourceBundle
        get() = findMessages() ?: error("bundle ${this::class.className} not found at ${this::class.qualifiedName}")

    val stage: Stage by lazy { get<Stage>() }

    var title: String
        get() = stage.title
        set(value) {
            stage.title = value
        }

    var icon: Image
        get() = stage.icons.first()
        set(value) {
            stage.icons.setAll(value)
        }

    fun resourceName(ext: String): String =
        this::class.simpleName + '.' + ext

    fun findResource(name: String, locale: Locale = Locale.getDefault()): URL? =
        locale.let {
            listOf(
                '_' + it.language + '_' + it.country,
                '_' + it.language,
                ""
            )
        }.map {
            name.substringBeforeLast('.') + it + '.' + name.substringAfterLast('.')
        }.map {
            this::class.findUrl(it)
        }.firstNotNullOfOrNull { it }

    fun getResource(name: String, locale: Locale = Locale.getDefault()): URL =
        findResource(name, locale) ?: error("$name not found at ${this::class.qualifiedName}")

    class ComponentResources(private val getter: KFunction2<String, Locale, URL>) {
        operator fun get(name: String): URL =
            getter(name, Locale.getDefault())
    }

    val resources: ComponentResources = ComponentResources(::getResource)

    fun findLocalResource(ext: String, locale: Locale = Locale.getDefault()): URL? =
        findResource(resourceName(ext), locale)

    fun getLocalResource(ext: String, locale: Locale = Locale.getDefault()): URL =
        findLocalResource(ext, locale) ?: error("local $ext not found at ${this::class.qualifiedName}")

    val config: Map<String, String> by lazy {
        findLocalResource("properties").let { url ->
            Properties().apply {
                runCatching { url?.openStream()?.use { load(it) } ?: "" }.onFailure {
                    System.getLogger(this::class.className).log(System.Logger.Level.WARNING, it.message)
                }
            }
        }.map { (key, value) ->
            key.toString() to value.toString()
        }.associate { it }
    }

    fun goTo(view: View) {
        with(get<KfxState>()) {
            currentView.onHide()
            stage.scene = view.root.scene ?: Scene(view.root)
            stage.width = currentView.root.scene.width
            stage.height = currentView.root.scene.height
            currentView = view
            currentView.onShow()
        }
    }

    val preferences: Preferences by lazy { Preferences.userNodeForPackage(get<App>()::class.java) }

    fun exit(): Unit = Platform.exit()
}
