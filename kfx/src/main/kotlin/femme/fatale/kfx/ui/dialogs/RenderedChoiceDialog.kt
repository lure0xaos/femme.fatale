package femme.fatale.kfx.ui.dialogs

import femme.fatale.kfx.ui.render.RenderedCellFactory
import femme.fatale.kfx.ui.render.RenderedListCell
import javafx.scene.control.ChoiceDialog
import javafx.scene.control.ComboBox

@Suppress("UNCHECKED_CAST")
class RenderedChoiceDialog<E : Any>(defaultChoice: E, choices: Collection<E>, format: (E) -> String) :
    ChoiceDialog<E>(defaultChoice, choices) {
    init {
        stylizeDialog()
        (dialogPane.lookup(".combo-box") as ComboBox<E>).apply {
            buttonCell = RenderedListCell(format)
            cellFactory = RenderedCellFactory(format)
        }
    }
}
