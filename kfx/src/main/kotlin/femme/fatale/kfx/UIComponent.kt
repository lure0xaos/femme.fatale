package femme.fatale.kfx

import javafx.scene.Parent

abstract class UIComponent : Component() {
    abstract val root: Parent
    open fun onShow() {}
    open fun onHide() {}
}
