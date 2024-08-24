package femme.fatale.kfx.ui.dialogs

import femme.fatale.kfx.Component
import femme.fatale.kfx.Handler
import femme.fatale.kfx.ui.render.RenderedCellFactory
import femme.fatale.kfx.ui.render.RenderedListCell
import javafx.scene.Node
import javafx.scene.control.*
import javafx.scene.control.Alert.AlertType
import javafx.scene.layout.GridPane
import javafx.scene.layout.Priority
import javafx.stage.Stage
import javafx.stage.Window
import kotlin.jvm.optionals.getOrNull


fun Window.showAlert(
    alertType: AlertType = AlertType.INFORMATION,
    title: String = "",
    header: String = "",
    text: String = "",
    graphic: Node? = null,
    init: Handler<Alert> = {}
): Alert =
    Alert(alertType).also {
        it.stylizeDialog()
        it.title = title
        it.headerText = header
        it.contentText = text
        it.initOwner(this)
        if (graphic != null) it.graphic = graphic
        (it.dialogPane.scene.window as? Stage)?.icons?.setAll((this.scene.window as? Stage)?.icons)
        it.init()
        it.showAndWait()
    }

fun Window.showWarning(
    title: String = "",
    header: String = "",
    text: String = "",
    graphic: Node? = null,
    init: Handler<Alert> = {}
): Alert =
    showAlert(AlertType.WARNING, title, header, text, graphic, init)

fun Window.showError(
    title: String = "",
    header: String = "",
    text: String = "",
    graphic: Node? = null,
    init: Handler<Alert> = {}
): Alert =
    showAlert(AlertType.ERROR, title, header, text, graphic, init)

fun Window.showError(
    title: String = "",
    header: String = "",
    text: String = "",
    exception: Exception,
    graphic: Node? = null,
    init: Handler<Alert> = {}
): Alert =
    showAlert(AlertType.ERROR, title, header, text, graphic) {
        val textArea = TextArea(exception.stackTraceToString()).apply {
            isEditable = false
            isWrapText = true
            maxWidth = Double.MAX_VALUE
            maxHeight = Double.MAX_VALUE
        }
        GridPane.setVgrow(textArea, Priority.ALWAYS)
        GridPane.setHgrow(textArea, Priority.ALWAYS)
        dialogPane.expandableContent = GridPane().apply {
            maxWidth = Double.MAX_VALUE
            add(Label("The exception stacktrace was:"), 0, 0)
            add(textArea, 0, 1)
        }
        init()
    }

fun Window.showConfirm(
    alertType: AlertType = AlertType.CONFIRMATION,
    title: String = "",
    header: String = "",
    text: String = "",
    graphic: Node? = null,
    init: Handler<Alert> = {}
): Boolean? =
    Alert(alertType).let {
        it.stylizeDialog()
        it.title = title
        it.headerText = header
        it.contentText = text
        it.initOwner(this)
        if (graphic != null) it.graphic = graphic
        (it.dialogPane.scene.window as? Stage)?.icons?.setAll((this.scene.window as? Stage)?.icons)
        it.init()
        it.showAndWait()
    }.map { it == ButtonType.OK }.getOrNull()

fun Window.showPrompt(
    title: String = "",
    header: String = "",
    text: String = "",
    defaultText: String = "",
    graphic: Node? = null,
    init: Handler<TextInputDialog> = {}
): String? =
    TextInputDialog(defaultText).let {
        it.title = title
        it.headerText = header
        it.contentText = text
        it.initOwner(this)
        if (graphic != null) it.graphic = graphic
        (it.dialogPane.scene.window as? Stage)?.icons?.setAll((this.scene.window as? Stage)?.icons)
        it.init()
        it.showAndWait()
    }.getOrNull()

fun Window.showChoice(
    title: String = "",
    header: String = "",
    text: String = "",
    defaultText: String = "",
    graphic: Node? = null,
    choices: Collection<String>,
    init: ChoiceDialog<String>.() -> Unit = {}
): String? =
    ChoiceDialog(defaultText, choices).let {
        it.title = title
        it.headerText = header
        it.contentText = text
        it.initOwner(this)
        if (graphic != null) it.graphic = graphic
        (it.dialogPane.scene.window as? Stage)?.icons?.setAll((this.scene.window as? Stage)?.icons)
        it.init()
        it.showAndWait()
    }.getOrNull()

fun <R : Any> Window.showChoice(
    title: String = "",
    header: String = "",
    text: String = "",
    defaultText: R,
    graphic: Node? = null,
    choices: Collection<R>,
    converter: (R) -> String = { it.toString() },
    init: ChoiceDialog<R>.() -> Unit = {}
): R? =
    ChoiceDialog(defaultText, choices).let {
        it.title = title
        it.headerText = header
        it.contentText = text
        it.initOwner(this)
        if (graphic != null) it.graphic = graphic
        (it.dialogPane.scene.window as? Stage)?.icons?.setAll((this.scene.window as? Stage)?.icons)
        @Suppress("UNCHECKED_CAST")
        (it.dialogPane.lookup(".combo-box") as? ComboBox<R>)?.apply {
            buttonCell = RenderedListCell(converter)
            cellFactory = RenderedCellFactory(converter)
        }
        it.init()
        it.showAndWait()
    }.orElseGet { null }

fun Component.showAlert(
    alertType: AlertType = AlertType.INFORMATION,
    title: String = "",
    header: String = "",
    text: String = "",
    graphic: Node? = null,
    init: Handler<Alert> = {}
): Alert =
    stage.showAlert(alertType, title, header, text, graphic, init)

fun Component.showWarning(
    title: String = "",
    header: String = "",
    text: String = "",
    graphic: Node? = null,
    init: Handler<Alert> = {}
): Alert =
    stage.showWarning(title, header, text, graphic, init)

fun Component.showError(
    title: String = "",
    header: String = "",
    text: String = "",
    graphic: Node? = null,
    init: Handler<Alert> = {}
): Alert =
    stage.showError(title, header, text, graphic, init)

fun Component.showError(
    title: String = "",
    header: String = "",
    text: String = "",
    exception: Exception,
    graphic: Node? = null,
    init: Handler<Alert> = {}
): Alert =
    stage.showError(title, header, text, exception, graphic, init)

fun Component.showConfirm(
    alertType: AlertType = AlertType.CONFIRMATION,
    title: String = "",
    header: String = "",
    text: String = "",
    graphic: Node? = null,
    init: Handler<Alert> = {}
): Boolean? =
    stage.showConfirm(alertType, title, header, text, graphic, init)

fun Component.showPrompt(
    title: String = "",
    header: String = "",
    text: String = "",
    defaultText: String = "",
    graphic: Node? = null,
    init: Handler<TextInputDialog> = {}
): String? =
    stage.showPrompt(title, header, text, defaultText, graphic, init)

fun Component.showChoice(
    title: String = "",
    header: String = "",
    text: String = "",
    defaultText: String = "",
    graphic: Node? = null,
    choices: Collection<String>,
    init: ChoiceDialog<String>.() -> Unit = {}
): String? =
    stage.showChoice(title, header, text, defaultText, graphic, choices, init)

fun <R : Any> Component.showChoice(
    title: String = "",
    header: String = "",
    text: String = "",
    defaultText: R,
    graphic: Node? = null,
    choices: Collection<R>,
    converter: (R) -> String = { it.toString() },
    init: ChoiceDialog<R>.() -> Unit = {}
): R? =
    stage.showChoice(title, header, text, defaultText, graphic, choices, converter, init)
