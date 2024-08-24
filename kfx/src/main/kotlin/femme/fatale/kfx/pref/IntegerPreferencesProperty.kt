package femme.fatale.kfx.pref

import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.serializer

@OptIn(InternalSerializationApi::class)
class IntegerPreferencesProperty(bean: Any, name: String, initialValue: Int) :
    PreferencesProperty<Int>(Int::class.serializer(), bean, name, initialValue)
