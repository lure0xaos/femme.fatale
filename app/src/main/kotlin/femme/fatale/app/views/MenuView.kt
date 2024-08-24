package femme.fatale.app.views

import femme.fatale.kfx.goTo
import femme.fatale.kfx.ui.views.ButtonMenuView
import javafx.scene.control.ButtonBase
import org.koin.core.annotation.Single

@Single
class MenuView : ButtonMenuView(
    "Femme Fatale",
    arrayOf(
        Triple("Start", null) { _: ButtonBase -> },
        Triple("Load", null) { _: ButtonBase -> },
        Triple("Options", null) { _: ButtonBase -> goTo<OptionsView>() },
        Triple("Exit", null) { _: ButtonBase -> exit() }
    )
)
