package femme.fatale.kfx.splash

import femme.fatale.kfx.ui.components.TransparentLabel
import femme.fatale.kfx.ui.components.TransparentWindow
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.value.ChangeListener
import java.io.Closeable
import java.net.URL

open class Splash(url: URL, alpha: Int = 100) : Closeable {
    private val logger = System.getLogger(Splash::class.qualifiedName!!)
    private val label: TransparentLabel
    private val window: TransparentWindow = TransparentWindow(TransparentLabel(url, alpha).also { label = it })

    private val progressProperty = SimpleIntegerProperty().apply {
        addListener(ChangeListener { _, _, newValue ->
            onProgress(newValue.toInt())
            logger.log(System.Logger.Level.INFO, "$value%")
        })
    }

    var progress: Int
        get() = progressProperty.get()
        set(value) {
            progressProperty.set(value)
        }

    var onProgress: Splash.(Int) -> Unit = DEFAULT_ON_PROGRESS

    init {
        progress = 0
        onProgress(0)
    }

    fun open() {
        window.isVisible = true
    }

    override fun close() {
        progress = 100
        window.isVisible = false
        window.dispose()
    }

    companion object {
        val DEFAULT_ON_PROGRESS: Splash.(Int) -> Unit = { label.alpha = it }
    }

}
