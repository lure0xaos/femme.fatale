package femme.fatale.kfx.ui.views

import femme.fatale.kfx.Component
import femme.fatale.kfx.Handler
import femme.fatale.kfx.View
import femme.fatale.kfx.ui.components.ButtonBox.Companion.defaultButtonDecorations
import femme.fatale.kfx.ui.components.ButtonBox.Companion.defaultContainerDecorations
import femme.fatale.kfx.ui.components.ButtonBox.Companion.defaultTitleDecorations
import femme.fatale.kfx.ui.components.ButtonMenu
import femme.fatale.kfx.util.get
import javafx.scene.Node
import javafx.scene.Parent
import javafx.scene.control.ButtonBase
import javafx.scene.control.TitledPane
import javafx.scene.layout.VBox

abstract class ButtonMenuView(
    title: String,
    buttons: Array<Triple<String, Node?, Component.(ButtonBase) -> Unit>> = arrayOf(),
    titleDecorations: Handler<TitledPane> = defaultTitleDecorations,
    containerDecorations: Handler<VBox> = defaultContainerDecorations,
    buttonDecorations: Handler<ButtonBase> = defaultButtonDecorations
) : View() {

    override val root: Parent = ButtonMenu(
        messages[title],
        buttons.map { (label: String, node: Node?, handler: Component.(ButtonBase) -> Unit) ->
            Triple(messages[label], node) { button: ButtonBase -> handler(this, button) }
        }.toTypedArray(),
        titleDecorations,
        containerDecorations,
        buttonDecorations
    )
}
