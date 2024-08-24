package femme.fatale.kfx.ui.components

import java.awt.BorderLayout
import java.awt.Color
import javax.swing.JComponent
import javax.swing.JWindow

class TransparentWindow(component: JComponent) : JWindow() {
    init {
        isAlwaysOnTop = true
        background = Color(0, 0, 0, 0)
        with(contentPane) {
            layout = BorderLayout()
            add(component)
        }
        pack()
        setLocationRelativeTo(null)
        toFront()
    }
}
