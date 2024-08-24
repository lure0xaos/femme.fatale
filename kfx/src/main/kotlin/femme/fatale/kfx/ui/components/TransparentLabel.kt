package femme.fatale.kfx.ui.components

import java.awt.AlphaComposite
import java.awt.Color
import java.awt.Composite
import java.awt.Dimension
import java.awt.Graphics
import java.awt.Graphics2D
import java.net.URL
import javax.swing.ImageIcon
import javax.swing.JLabel
import kotlin.math.max
import kotlin.math.min

class TransparentLabel(url: URL, alpha: Int = 100) : JLabel() {

    var alpha: Int = alpha
        set(value) {
            field = max(0, min(100, value))
            repaint()
        }

    init {
        ImageIcon(url).also {
            icon = it
            background = Color(0, 0, 0, 0)
            isOpaque = false
            preferredSize = Dimension(it.image.getWidth(null), it.image.getHeight(null))
        }
    }

    override fun paintComponent(g: Graphics) {
        val g2: Graphics2D? = g as? Graphics2D
        val old: Composite? = g2?.composite
        g2?.composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha / 100f)
        super.paintComponent(g)
        g2?.composite = old
    }
}
