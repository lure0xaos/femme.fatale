package femme.fatale.kfx.ui.render

import javafx.scene.control.ListCell
import javafx.scene.control.ListView
import javafx.util.Callback

class RenderedCellFactory<E : Any>(private val format: (E) -> String) : Callback<ListView<E>, ListCell<E>> {
    override fun call(p: ListView<E>): ListCell<E> = RenderedListCell(format)
}
