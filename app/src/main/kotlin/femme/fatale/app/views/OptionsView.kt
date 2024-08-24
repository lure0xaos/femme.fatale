package femme.fatale.app.views

import femme.fatale.app.data.Options
import femme.fatale.kfx.goTo
import femme.fatale.kfx.ui.views.PropertiesView
import org.koin.core.annotation.Single

@Single
class OptionsView : PropertiesView(
    "Options",
    listOf(
        Options::sound,
        Options::music,
        Options::user,
        Options::opacity,
        Options::theme,
    ), { goTo<MenuView>() }
)
