package femme.fatale.kfx

import femme.fatale.kfx.pref.FilePreferencesFactory
import femme.fatale.kfx.splash.Splash
import femme.fatale.log.LogConfig
import javafx.application.Application
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.ksp.generated.module
import java.net.URL

internal lateinit var splash: Splash

fun launch(
    module: Module,
    args: Array<String>,
    url: URL? = null,
    splashProgress: (Splash.(Int) -> Unit)? = null
) {
    LogConfig
    splash = Loading(url)
    splashProgress?.also {
        splash.onProgress = it
    } ?: { splash.progress = 100 }()
    splash.open()
    FilePreferencesFactory.init()
    startKoin {
        modules(KfxModule().module, module)
        koin.declare(splash)
    }
    Application.launch(Launcher::class.java, *args)
}
