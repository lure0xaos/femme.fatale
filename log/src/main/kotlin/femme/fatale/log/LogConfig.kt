package femme.fatale.log

import java.util.logging.LogManager

object LogConfig {
    private const val DEFAULT_CONFIGURATION = "logging.properties"

    init {
        LogManager.getLogManager()
            .readConfiguration(LogConfig::class.java.getResourceAsStream(DEFAULT_CONFIGURATION))
    }
}
