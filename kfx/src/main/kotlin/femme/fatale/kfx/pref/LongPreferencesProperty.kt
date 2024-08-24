package femme.fatale.kfx.pref

import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.serializer

@OptIn(InternalSerializationApi::class)
class LongPreferencesProperty(bean: Any, name: String, initialValue: Long) :
    PreferencesProperty<Long>(Long::class.serializer(), bean, name, initialValue)
