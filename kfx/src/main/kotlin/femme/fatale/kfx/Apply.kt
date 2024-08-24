package femme.fatale.kfx

import javafx.scene.Node

inline infix operator fun <reified N : Node> N.invoke(initializer: Handler<N>): N {
    apply { initializer() }
    return this
}
