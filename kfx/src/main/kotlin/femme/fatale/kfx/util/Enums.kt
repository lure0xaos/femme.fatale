@file:OptIn(ExperimentalSerializationApi::class)

package femme.fatale.kfx.util

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialKind
import kotlinx.serialization.descriptors.elementNames
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import kotlin.reflect.KType

fun KType.isEnum(serializer: KSerializer<Any?> = serializer(this)): Boolean {
    return serializer.descriptor.kind == SerialKind.ENUM
}

fun KType.enumValueOf(name: String, serializer: KSerializer<Any?> = serializer(this)): Enum<*> {
    require(isEnum(serializer)) { "enumValueOf must be used on enum" }
    return Json.decodeFromString(serializer, "\"$name\"") as Enum<*>
}

fun KType.enumValuesName(serializer: KSerializer<Any?> = serializer(this)): List<String> {
    require(isEnum(serializer)) { "enumValuesName must be used on enum" }
    val enumName = serializer.descriptor.serialName
    return serializer.descriptor.elementNames.map { it.removePrefix(enumName) }
}

fun KType.enumValues(serializer: KSerializer<Any?> = serializer(this)): List<Enum<*>> {
    require(isEnum(serializer)) { "enumValues must be used on enum" }
    return enumValuesName(serializer).map { enumValueOf(it, serializer) }
}
