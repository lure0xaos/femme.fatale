package femme.fatale.kfx

import femme.fatale.kfx.util.getStream
import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.image.Image
import javafx.stage.Stage
import org.koin.core.component.KoinComponent
import org.koin.core.component.get


class Launcher : Application(), KoinComponent {

    override fun init() {
        get<App>().init()
    }

    override fun start(primaryStage: Stage) {
        getKoin().declare(primaryStage)
        get<App>().apply {
            title = get<App>()::class.simpleName!!
            icon = Image(Launcher::class.getStream(DEFAULT_ICON))
            start()
        }
        getKoin().getAll<PrimaryView>().let {
            require(it.size == 1) {
                "only one PrimaryView is allowed, but $it found"
            }
        }
        val primaryView = get<PrimaryView>()
        get<KfxState>().currentView = primaryView
        with(get<Stage>()) {
            scene = Scene(primaryView.root)
            primaryView.onShow()
            splash.close()
            centerOnScreen()
            show()
            toFront()
            requestFocus()
        }
    }

    override fun stop() {
        splash.close()
        get<App>().stop()
        getKoin().close()
    }

    companion object {
        private const val DEFAULT_ICON = "kfx.png"
    }
}
