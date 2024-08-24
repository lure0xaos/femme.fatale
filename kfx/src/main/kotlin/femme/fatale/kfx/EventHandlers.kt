package femme.fatale.kfx

import javafx.event.EventHandler
import javafx.scene.Node
import javafx.scene.input.MouseEvent
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.javafx.JavaFx
import kotlinx.coroutines.launch

typealias Handler<N> = N.() -> Unit
typealias SuspendHandler<N> = suspend N.() -> Unit

@OptIn(DelicateCoroutinesApi::class)
inline fun <reified N : Node> N.invokeLater(crossinline handler: SuspendHandler<N>) {
    GlobalScope.launch(Dispatchers.JavaFx) { handler() }
}

inline fun <reified N : Node> N.eventHandler(crossinline handler: Handler<N>): EventHandler<MouseEvent> =
    EventHandler { handler() }

inline fun <reified N : Node> N.eventHandlerAsync(crossinline handler: SuspendHandler<N>): EventHandler<MouseEvent> =
    EventHandler { invokeLater<N>(handler) }

inline fun <reified N : Node> N.onClick(crossinline handler: Handler<N>) {
    onMouseClicked = eventHandler<N>(handler)
}

inline fun <reified N : Node> N.onClickLaunch(crossinline handler: SuspendHandler<N>) {
    onMouseClicked = eventHandlerAsync<N>(handler)
}
