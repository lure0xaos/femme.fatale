package femme.fatale.kfx

import femme.fatale.kfx.splash.Splash
import femme.fatale.kfx.util.getUrl
import java.net.URL

private const val DEFAULT_SPLASH = "loading.gif"

internal class Loading(url: URL?) : Splash(url ?: Loading::class.getUrl(DEFAULT_SPLASH))
