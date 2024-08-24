package femme.fatale.model

import femme.fatale.log.LogConfig
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.util.ResourceBundle
import kotlin.test.Test
import kotlin.test.assertEquals

class GenTest {
    private val logger: System.Logger by lazy {
        runCatching {
            ResourceBundle.getBundle(this::class.qualifiedName!!)
        }.getOrNull()?.let { System.getLogger(this::class.qualifiedName!!, it) }
            ?: System.getLogger(this::class.qualifiedName!!)
    }

    init {
        LogConfig
    }

    private val json = Json {
        prettyPrint = true
        encodeDefaults = true
    }

    @Test
    fun testGenerate() {
        val character = Character()
        val encoded = json.encodeToString(character)
        val decoded = json.decodeFromString<Character>(encoded)
        assertEquals(character, decoded)
        logger.log(System.Logger.Level.INFO) { encoded }
    }
}
