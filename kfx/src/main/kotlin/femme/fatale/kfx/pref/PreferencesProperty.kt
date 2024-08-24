package femme.fatale.kfx.pref

import javafx.beans.InvalidationListener
import javafx.beans.Observable
import javafx.beans.WeakListener
import javafx.beans.property.ObjectPropertyBase
import javafx.beans.value.ObservableValue
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import java.lang.ref.WeakReference
import java.util.prefs.Preferences

open class PreferencesProperty<T : Any>(
    private val serializer: KSerializer<T>,
    private val bean: Any,
    private val name: String,
    private val initialValue: T
) : ObjectPropertyBase<T>(initialValue) {

    private val preferences: Preferences = Preferences.userNodeForPackage(bean::class.java)
    private var preferencesValue: T
        get() {
            return Json.decodeFromString(
                serializer,
                preferences.get(name, Json.encodeToString(serializer, initialValue))
            )
        }
        set(value) {
            preferences.put(name, Json.encodeToString(serializer, value))
        }
    private var observable: ObservableValue<out T>? = null
    private var listener: InvalidationListener? = null
    private var valid = true

    override fun getBean(): Any =
        bean

    override fun getName(): String =
        name


    private fun markInvalid() {
        if (valid) {
            valid = false
            invalidated()
            fireValueChangedEvent()
        }
    }

    override fun invalidated() {
        preferences.flush()
    }

    override fun get(): T? {
        valid = true
        return if (observable == null) preferencesValue else observable!!.value
    }

    override fun set(newValue: T) {
        require(!isBound) { (getBean().javaClass.simpleName + "." + getName() + " : " + "A bound value cannot be set.") }
        if (preferencesValue !== newValue) {
            preferencesValue = newValue
            markInvalid()
        }
    }

    override fun isBound(): Boolean =
        observable != null

    override fun bind(newObservable: ObservableValue<out T>?) {
        requireNotNull(newObservable) { "Cannot bind to null" }
        if (newObservable != this.observable) {
            unbind()
            observable = newObservable
            if (listener == null) {
                listener = Listener(this)
            }
            observable!!.addListener(listener)
            markInvalid()
        }
    }

    override fun unbind() {
        if (observable != null) {
            preferencesValue = observable!!.value
            observable!!.removeListener(listener)
            observable = null
        }
    }

    override fun toString(): String =
        buildString {
            append("ObjectProperty [")
            append("bean: ")
            append(bean)
            append(", ")
            if (name.isNotBlank()) {
                append("name: ")
                append(name)
                append(", ")
            }
            if (isBound) {
                append("bound, ")
                if (valid) {
                    append("value: ")
                    append(get())
                } else {
                    append("invalid")
                }
            } else {
                append("value: ")
                append(get())
            }
            append("]")
        }

    private class Listener(ref: PreferencesProperty<*>) : InvalidationListener, WeakListener {
        private val wRef = WeakReference(ref)

        override fun invalidated(observable: Observable) {
            val ref = wRef.get()
            if (ref == null) {
                observable.removeListener(this)
            } else {
                ref.markInvalid()
            }
        }

        override fun wasGarbageCollected(): Boolean {
            return wRef.get() == null
        }
    }

}
