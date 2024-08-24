package femme.fatale.app.views

import femme.fatale.kfx.PrimaryView
import femme.fatale.kfx.goTo
import femme.fatale.kfx.invoke
import femme.fatale.kfx.onClickLaunch
import femme.fatale.kfx.util.Image
import javafx.scene.Parent
import javafx.scene.control.Label
import javafx.scene.image.ImageView
import org.koin.core.annotation.Single

@Single
class IntroView : PrimaryView() {
    override val root: Parent =
        (Label("", ImageView(Image(resources[INTRO])))) {
            onClickLaunch {
                goTo<MenuView>()
            }
        }

    companion object {
        private const val INTRO = "intro.png"
    }
}
