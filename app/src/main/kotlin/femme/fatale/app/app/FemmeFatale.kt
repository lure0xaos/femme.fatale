package femme.fatale.app.app

import femme.fatale.kfx.App
import femme.fatale.kfx.util.Image
import org.koin.core.annotation.Single

@Single
class FemmeFatale : App() {
    override fun start() {
        icon = Image(resources[ICON])
    }

    companion object {
        private const val ICON = "icon.png"
    }
}
