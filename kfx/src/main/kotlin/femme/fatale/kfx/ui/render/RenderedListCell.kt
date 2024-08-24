package femme.fatale.kfx.ui.render

import javafx.scene.control.ListCell

class RenderedListCell<E : Any>(private val format: (E) -> String) : ListCell<E>() {
    override fun updateItem(item: E?, empty: Boolean) {
        super.updateItem(item, empty)
        text = if (!empty && item != null) format(item) else null
    }
}
