package femme.fatale.kfx.ui.dialogs

import javafx.css.PseudoClass
import javafx.scene.control.Button
import javafx.scene.control.ButtonType
import javafx.scene.control.Dialog
import javafx.scene.control.TextField
import javafx.scene.control.TextInputDialog

fun Dialog<*>.styleDialog(url: String) {
    dialogPane.scene.stylesheets += this::class.java.getResource(url)!!.toExternalForm()
}

fun Dialog<*>.stylizeDialog(url: String = "/femme/fatale/kfx/ui/dialogs/Alerts.css") {
    styleDialog(url)
}

fun TextInputDialog.validation(isValid: (String) -> Boolean) {
    stylizeDialog()
    val inputField: TextField = editor
    val okButton = dialogPane.lookupButton(ButtonType.OK) as Button
    val pseudoClass = PseudoClass.getPseudoClass("error")
    inputField.textProperty().addListener { _, _, _ ->
        val valid = isValid(inputField.text)
        okButton.isDisable = !valid
        inputField.pseudoClassStateChanged(pseudoClass, !valid)
    }
}
