package femme.fatale.kfx.ui.components

import femme.fatale.kfx.Handler
import javafx.scene.Node
import javafx.scene.control.Button
import javafx.scene.control.ButtonBase
import javafx.scene.control.TitledPane
import javafx.scene.layout.VBox

class ButtonMenu(
    title: String,
    buttons: Map<ButtonBase, Handler<ButtonBase>> = mapOf(),
    titleDecorations: Handler<TitledPane> = defaultTitleDecorations,
    containerDecorations: Handler<VBox> = defaultContainerDecorations,
    buttonDecorations: Handler<ButtonBase> = defaultButtonDecorations
) : ButtonBox(title, buttons.map { it.key to it.value }, titleDecorations, containerDecorations, buttonDecorations) {
    constructor(
        title: String,
        buttons: Array<Pair<String, Handler<ButtonBase>>> = arrayOf(),
        titleDecorations: Handler<TitledPane> = defaultTitleDecorations,
        containerDecorations: Handler<VBox> = defaultContainerDecorations,
        buttonDecorations: Handler<ButtonBase> = defaultButtonDecorations
    ) : this(
        title,
        buttons.associate { Button(it.first) to it.second },
        titleDecorations,
        containerDecorations,
        buttonDecorations
    )

    constructor(
        title: String,
        buttons: Array<Triple<String, Node?, Handler<ButtonBase>>> = arrayOf(),
        titleDecorations: Handler<TitledPane> = defaultTitleDecorations,
        containerDecorations: Handler<VBox> = defaultContainerDecorations,
        buttonDecorations: Handler<ButtonBase> = defaultButtonDecorations
    ) : this(
        title,
        buttons.associate { Button(it.first, it.second) to it.third },
        titleDecorations,
        containerDecorations,
        buttonDecorations
    )
}
