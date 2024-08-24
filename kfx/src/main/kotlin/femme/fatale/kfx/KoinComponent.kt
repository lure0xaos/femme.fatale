package femme.fatale.kfx

import org.koin.core.component.get

inline fun <reified V : View> Component.goTo(): Unit = goTo(get<V>())
