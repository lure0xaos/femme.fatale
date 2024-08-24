package femme.fatale.kfx.ui.views

import femme.fatale.kfx.View
import femme.fatale.kfx.ui.components.ButtonBox.Companion.defaultButtonDecorations
import femme.fatale.kfx.ui.components.ButtonBox.Companion.defaultContainerDecorations
import femme.fatale.kfx.ui.components.ButtonBox.Companion.defaultTitleDecorations
import femme.fatale.kfx.ui.components.PropertiesBox
import femme.fatale.kfx.util.get
import javafx.scene.Parent
import javafx.scene.control.ButtonBase
import javafx.scene.control.TextInputDialog
import javafx.scene.control.TitledPane
import javafx.scene.layout.VBox
import java.util.ResourceBundle
import kotlin.reflect.KMutableProperty0


abstract class PropertiesView(
    title: String,
    properties: List<KMutableProperty0<out Any>> = listOf(),
    val exitAction: View.() -> Unit = {},
    titleDecorations: (TitledPane) -> Unit = defaultTitleDecorations,
    containerDecorations: (VBox) -> Unit = defaultContainerDecorations,
    buttonDecorations: (ButtonBase) -> Unit = defaultButtonDecorations,
    textInputDialogDecorations: TextInputDialog.(KMutableProperty0<*>, String, ResourceBundle) -> Unit = defaultTextInputDialogDecorations
) : View() {

    override val root: Parent = PropertiesBox(
        title,
        properties,
        { exitAction(this@PropertiesView) },
        messages,
        titleDecorations,
        containerDecorations,
        buttonDecorations,
        textInputDialogDecorations,
    )

    companion object {
        val defaultTextInputDialogDecorations: TextInputDialog.(KMutableProperty0<*>, String, ResourceBundle) -> Unit =
            { property, title, messages ->
                this.title = title
                headerText = null
                contentText = messages[property.name] + ":"
            }
    }

}
