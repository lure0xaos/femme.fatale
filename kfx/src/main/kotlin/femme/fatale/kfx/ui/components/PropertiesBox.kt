package femme.fatale.kfx.ui.components

import femme.fatale.kfx.ui.dialogs.RenderedChoiceDialog
import femme.fatale.kfx.ui.dialogs.stylizeDialog
import femme.fatale.kfx.ui.dialogs.validation
import femme.fatale.kfx.util.enumValues
import femme.fatale.kfx.util.get
import femme.fatale.kfx.util.isEnum
import femme.fatale.kfx.util.titlecased
import javafx.scene.control.Button
import javafx.scene.control.ButtonBase
import javafx.scene.control.ChoiceDialog
import javafx.scene.control.Hyperlink
import javafx.scene.control.Label
import javafx.scene.control.TextInputDialog
import javafx.scene.control.TitledPane
import javafx.scene.control.ToggleButton
import javafx.scene.layout.VBox
import java.io.StringReader
import java.util.PropertyResourceBundle
import java.util.ResourceBundle
import kotlin.reflect.KMutableProperty0
import kotlin.reflect.full.createType


@Suppress("UNCHECKED_CAST")
class PropertiesBox(
    title: String,
    properties: List<KMutableProperty0<out Any>> = listOf(),
    val exitAction: () -> Unit = {},
    messages: ResourceBundle = PropertyResourceBundle(StringReader("")),
    titleDecorations: (TitledPane) -> Unit = defaultTitleDecorations,
    containerDecorations: (VBox) -> Unit = defaultContainerDecorations,
    buttonDecorations: (ButtonBase) -> Unit = defaultButtonDecorations,
    textInputDialogDecorations: TextInputDialog.(KMutableProperty0<*>, String, ResourceBundle) -> Unit = defaultTextInputDialogDecorations,
    choiceDialogDecorations: ChoiceDialog<*>.(KMutableProperty0<*>, String, ResourceBundle) -> Unit = defaultChoiceDialogDecorations
) : ButtonBox(
    messages[title],
    properties.mapNotNull { property ->
        val kType = property.returnType
        when {
            kType == Boolean::class.createType() -> {
                val label = { it: Boolean -> if (it) "✔" else "✖" }
                val property = property as KMutableProperty0<Boolean>
                ToggleButton().apply {
                    isSelected = property.get()
                    decoratePropertyButton(property, label, messages)
                } to { button: ButtonBase ->
                    property.set((button as ToggleButton).isSelected)
                    button.decoratePropertyButton(property, label, messages)
                }
            }

            kType == String::class.createType() ->
                askValue(
                    property as KMutableProperty0<String>,
                    messages,
                    textInputDialogDecorations,
                    title
                ) { it }

            kType == Int::class.createType() ->
                askValue(
                    property as KMutableProperty0<Int>,
                    messages,
                    textInputDialogDecorations,
                    title
                ) { it.toInt() }

            kType == UInt::class.createType() ->
                askValue(
                    property as KMutableProperty0<UInt>,
                    messages,
                    textInputDialogDecorations,
                    title
                ) { it.toUInt() }

            kType == Long::class.createType() ->
                askValue(
                    property as KMutableProperty0<Long>,
                    messages,
                    textInputDialogDecorations,
                    title
                ) { it.toLong() }

            kType == ULong::class.createType() ->
                askValue(
                    property as KMutableProperty0<ULong>,
                    messages,
                    textInputDialogDecorations,
                    title
                ) { it.toULong() }

            kType == Double::class.createType() ->
                askValue(
                    property as KMutableProperty0<Double>,
                    messages,
                    textInputDialogDecorations,
                    title
                ) { it.toDouble() }

            kType.isEnum() ->
                askChoice<Enum<*>>(
                    property as KMutableProperty0<Enum<*>>,
                    messages,
                    choiceDialogDecorations,
                    title,
                    kType.enumValues()
                )

            else -> null
        }
    } + listOf(
        Hyperlink().apply { isVisible = false } to {},
        Button(messages["Back"], Label("←")).apply {
        } to { _: ButtonBase -> exitAction() }
    ),
    titleDecorations,
    containerDecorations,
    buttonDecorations
) {

    companion object {
        private fun <S> ButtonBase.decoratePropertyButton(
            property: KMutableProperty0<S>,
            format: (S) -> String,
            messages: ResourceBundle
        ) {
            text = messages[property.name] + ": " + format(property.get())
        }

        private inline fun <reified V : Any> askValue(
            property: KMutableProperty0<V>,
            messages: ResourceBundle,
            crossinline decorations: TextInputDialog.(KMutableProperty0<V>, String, ResourceBundle) -> Unit,
            title: String,
            noinline label: (V) -> String = { it.toString() },
            crossinline parser: (String) -> V
        ): Pair<Button, (ButtonBase) -> Unit> =
            Button().apply {
                decoratePropertyButton(property, label, messages)
            } to { button: ButtonBase ->
                TextInputDialog(label(property.get())).apply {
                    stylizeDialog()
                    validation { runCatching { parser(it) }.isSuccess }
                    decorations(property, title, messages)
                }.showAndWait().ifPresent {
                    property.set(parser(it))
                    button.decoratePropertyButton(property, label, messages)
                }
            }


        private inline fun <reified V : Any> askChoice(
            property: KMutableProperty0<V>,
            messages: ResourceBundle,
            crossinline decorations: ChoiceDialog<V>.(KMutableProperty0<V>, String, ResourceBundle) -> Unit,
            title: String,
            values: List<V>,
            noinline format: (V) -> String = { it.toString().titlecased() }
        ): Pair<Button, (ButtonBase) -> Unit> =
            Button().apply {
                decoratePropertyButton(property, format, messages)
            } to { button: ButtonBase ->
                RenderedChoiceDialog(property.get(), values, format).apply {
                    decorations(property, title, messages)
                }.showAndWait().ifPresent {
                    property.set(it)
                    button.decoratePropertyButton(property, format, messages)
                }
            }

        val defaultTextInputDialogDecorations: TextInputDialog.(KMutableProperty0<*>, String, ResourceBundle) -> Unit =
            { property, title, messages ->
                this.title = title
                headerText = null
                contentText = messages[property.name] + ":"
            }

        val defaultChoiceDialogDecorations: ChoiceDialog<*>.(KMutableProperty0<*>, String, ResourceBundle) -> Unit =
            { property, title, messages ->
                this.title = title
                headerText = null
                contentText = messages[property.name] + ":"
            }
    }

}
