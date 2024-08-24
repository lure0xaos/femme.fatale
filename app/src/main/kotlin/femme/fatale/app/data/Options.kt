package femme.fatale.app.data

import femme.fatale.kfx.pref.BooleanPreferencesProperty
import femme.fatale.kfx.pref.EnumPreferencesProperty
import femme.fatale.kfx.pref.IntegerPreferencesProperty
import femme.fatale.kfx.pref.StringPreferencesProperty
import javafx.beans.property.Property


object Options {
    private const val USER_NAME = "user.name"
    private const val SOUND = "sound"
    private const val MUSIC = "music"
    private const val USER = "user"
    private const val OPACITY = "opacity"
    private const val THEME = "theme"

    val soundProperty: Property<Boolean> = BooleanPreferencesProperty(this, SOUND, true)
    var sound: Boolean
        get() = soundProperty.value
        set(value) {
            soundProperty.value = value
        }
    val musicProperty: Property<Boolean> = BooleanPreferencesProperty(this, MUSIC, true)
    var music: Boolean
        get() = musicProperty.value
        set(value) {
            musicProperty.value = value
        }
    val userProperty: Property<String> =
        StringPreferencesProperty(this, USER, System.getProperty(USER_NAME))
    var user: String
        get() = userProperty.value
        set(value) {
            userProperty.value = value
        }
    val opacityProperty: Property<Int> = IntegerPreferencesProperty(this, OPACITY, 0)
    var opacity: Int
        get() = opacityProperty.value
        set(value) {
            opacityProperty.value = value
        }
    val themeProperty: Property<Theme> = EnumPreferencesProperty(this, THEME, Theme.AUTO)
    var theme: Theme
        get() = themeProperty.value
        set(value) {
            themeProperty.value = value
        }
}
