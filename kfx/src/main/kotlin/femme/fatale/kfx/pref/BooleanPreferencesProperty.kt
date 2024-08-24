package femme.fatale.kfx.pref

import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.serializer

@OptIn(InternalSerializationApi::class)
class BooleanPreferencesProperty(bean: Any, name: String, initialValue: Boolean) :
    PreferencesProperty<Boolean>(Boolean::class.serializer(), bean, name, initialValue)
