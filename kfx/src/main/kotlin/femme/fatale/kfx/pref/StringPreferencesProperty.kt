package femme.fatale.kfx.pref

import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.serializer

@OptIn(InternalSerializationApi::class)
class StringPreferencesProperty(bean: Any, name: String, initialValue: String) :
    PreferencesProperty<String>(String::class.serializer(), bean, name, initialValue)
