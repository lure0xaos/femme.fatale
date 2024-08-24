package femme.fatale.kfx.pref

import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.serializer

@OptIn(InternalSerializationApi::class)
class DoublePreferencesProperty(bean: Any, name: String, initialValue: Double) :
    PreferencesProperty<Double>(Double::class.serializer(), bean, name, initialValue)
