package femme.fatale.kfx.ui.dialogs

import javafx.scene.control.*
import javafx.scene.control.Alert.AlertType
import javafx.scene.image.Image
import javafx.scene.layout.GridPane
import javafx.scene.layout.Priority
import javafx.stage.Stage
import kotlin.jvm.optionals.getOrNull


fun showMessage(
    alertType: AlertType = AlertType.INFORMATION,
    content: String? = null,
    header: String? = null,
    title: String? = null,
    icon: Image? = null,
    owner: Stage? = null
): Unit {
    Alert(alertType).apply {
        stylizeDialog()
        if (title != null) this.title = title
        if (header != null) headerText = header
        if (content != null) contentText = content
        if (owner != null) initOwner(owner)
        if (icon != null) (dialogPane.scene.window as Stage).icons.setAll(icon)
    }.showAndWait()
}

fun askConfirmation(
    alertType: AlertType = AlertType.INFORMATION,
    content: String? = null,
    header: String? = null,
    title: String? = null,
    icon: Image? = null,
    owner: Stage? = null
): Boolean =
    Alert(alertType).apply {
        stylizeDialog()
        if (title != null) this.title = title
        if (header != null) headerText = header
        if (content != null) contentText = content
        if (owner != null) initOwner(owner)
        if (icon != null) (dialogPane.scene.window as Stage).icons.setAll(icon)
    }.showAndWait().getOrNull() == ButtonType.OK

fun inputText(
    alertType: String = "",
    content: String? = null,
    header: String? = null,
    title: String? = null,
    icon: Image? = null,
    owner: Stage? = null
): String? =
    TextInputDialog(alertType).apply {
        stylizeDialog()
        if (title != null) this.title = title
        if (header != null) headerText = header
        if (content != null) contentText = content
        if (owner != null) initOwner(owner)
        if (icon != null) (dialogPane.scene.window as Stage).icons.setAll(icon)
    }.showAndWait().getOrNull()

fun showInfo(
    content: String? = null,
    header: String? = null,
    title: String? = null,
    icon: Image? = null,
    owner: Stage? = null
): Unit =
    showMessage(AlertType.INFORMATION, content, header, title, icon, owner)

fun showWarning(
    content: String? = null,
    header: String? = null,
    title: String? = null,
    icon: Image? = null,
    owner: Stage? = null
): Unit =
    showMessage(AlertType.WARNING, content, header, title, icon, owner)

fun showError(
    content: String? = null,
    header: String? = null,
    title: String? = null,
    icon: Image? = null,
    owner: Stage? = null
): Unit =
    showMessage(AlertType.ERROR, content, header, title, icon, owner)

fun showException(
    content: String? = null,
    header: String? = null,
    title: String? = null,
    icon: Image? = null,
    owner: Stage? = null,
    ex: Exception? = null
) {
    Alert(AlertType.ERROR).apply {
        stylizeDialog()
        if (title != null) this.title = title
        if (header != null) headerText = header
        if (content != null) contentText = content
        if (owner != null) initOwner(owner)
        if (icon != null) (dialogPane.scene.window as Stage).icons.setAll(icon)
        dialogPane.expandableContent = GridPane().apply {
            maxWidth = Double.MAX_VALUE
            add(Label(ex?.message ?: ""), 0, 0)
            add(TextArea(ex?.stackTraceToString()).apply {
                isEditable = false
                isWrapText = true
                maxWidth = Double.MAX_VALUE
                maxHeight = Double.MAX_VALUE
                GridPane.setVgrow(this, Priority.ALWAYS)
                GridPane.setHgrow(this, Priority.ALWAYS)
            }, 0, 1)
        }
    }.showAndWait()
}
