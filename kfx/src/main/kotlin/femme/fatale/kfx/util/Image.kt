package femme.fatale.kfx.util

import javafx.scene.image.Image
import java.net.URL

fun Image(url: URL): Image = Image(url.toExternalForm())
