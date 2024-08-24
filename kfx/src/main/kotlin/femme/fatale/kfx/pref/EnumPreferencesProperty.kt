package femme.fatale.kfx.pref

import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.serializer

@OptIn(InternalSerializationApi::class)
class EnumPreferencesProperty<E : Enum<E>>(bean: Any, name: String, initialValue: E) :
    PreferencesProperty<E>(initialValue::class.serializer() as KSerializer<E>, bean, name, initialValue)
