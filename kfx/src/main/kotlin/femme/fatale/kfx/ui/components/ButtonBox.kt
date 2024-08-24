package femme.fatale.kfx.ui.components

import femme.fatale.kfx.Handler
import javafx.event.EventHandler
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.control.ButtonBase
import javafx.scene.control.TitledPane
import javafx.scene.layout.Priority
import javafx.scene.layout.VBox

open class ButtonBox(
    title: String,
    buttons: List<Pair<ButtonBase, Handler<ButtonBase>>> = listOf(),
    titleDecorations: Handler<TitledPane> = defaultTitleDecorations,
    containerDecorations: Handler<VBox> = defaultContainerDecorations,
    buttonDecorations: Handler<ButtonBase> = defaultButtonDecorations
) : TitledPane(title, VBox(
    *buttons.map { (buttonBase: ButtonBase, handler: Handler<ButtonBase>) ->
        buttonBase.apply {
            buttonDecorations()
            onAction = EventHandler { handler() }
        }
    }.toTypedArray(),
).apply { containerDecorations() }) {
    init {
        titleDecorations()
    }

    companion object {
        val defaultTitleDecorations: Handler<TitledPane> = {
            alignment = Pos.CENTER
            isCollapsible = false
        }
        val defaultContainerDecorations: Handler<VBox> = {
            VBox.setVgrow(this, Priority.ALWAYS)
            alignment = Pos.CENTER
            maxWidth = Double.MAX_VALUE
            spacing = 10.0
            padding = Insets(0.0, 20.0, 10.0, 20.0)
        }
        val defaultButtonDecorations: Handler<ButtonBase> = {
            maxWidth = Double.MAX_VALUE
        }
    }
}
