package femme.fatale.kfx.pref

import java.util.prefs.Preferences
import java.util.prefs.PreferencesFactory

class FilePreferencesFactory : PreferencesFactory {

    override fun systemRoot(): Preferences =
        FilePreferences(null, "", false)

    override fun userRoot(): Preferences =
        FilePreferences(null, "", true)

    companion object {
        fun init() {
            System.setProperty("java.util.prefs.PreferencesFactory", FilePreferencesFactory::class.qualifiedName!!)
        }
    }
}
