package femme.fatale.kfx.pref

import java.nio.file.StandardOpenOption
import java.util.*
import java.util.prefs.AbstractPreferences
import kotlin.io.path.*

class FilePreferences(parent: AbstractPreferences?, name: String, private val isUser: Boolean) :
    AbstractPreferences(parent, name) {
    private val root = Path(System.getProperty("user.home"))
    private val name = absolutePath().replace('/', '.')
    private val path = root / "${this.name}.$EXT"
    private val properties = Properties()

    override fun putSpi(key: String, value: String) {
        properties[key] = value
        flush()
    }

    override fun getSpi(key: String): String? {
        sync()
        return properties[key]?.toString()
    }

    override fun removeSpi(key: String) {
        properties.remove(key)
        flush()
    }

    override fun removeNodeSpi() {
        properties.clear()
        path.deleteIfExists()
    }

    override fun keysSpi(): Array<String> {
        sync()
        return properties.keys.map { it.toString() }.toTypedArray()
    }

    @OptIn(ExperimentalPathApi::class)
    override fun childrenNamesSpi(): Array<String> {
        return root.walk()
            .filter { it.isRegularFile() && it.extension == EXT && name in it.name }
            .map { it.name }
            .toList().toTypedArray()
    }

    override fun childSpi(name: String): AbstractPreferences {
        return FilePreferences(this, name, isUser)
    }

    override fun flushSpi() {
        path.outputStream(
            StandardOpenOption.WRITE,
            StandardOpenOption.TRUNCATE_EXISTING,
            StandardOpenOption.CREATE
        ).use { properties.store(it, Date().toString()) }
    }

    override fun syncSpi() {
        path.inputStream(StandardOpenOption.READ)
            .use { properties.load(it) }
    }

    companion object {
        private const val EXT: String = "prefs"
    }
}
