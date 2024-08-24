package femme.fatale.gen.visitor

import femme.fatale.gen.parser.intf.INode
import femme.fatale.gen.parser.nodes.*
import femme.fatale.gen.visitor.intf.IVisitor

internal class XmlVisitor private constructor() : IVisitor {

    private var level: Int = 0
    private val builder: StringBuilder = StringBuilder()


    override fun visit(node: LiteralNode) {
        builder.append("{0}<{1}>{2}</{1}>{3}".replace(Regex("\\{([0-9])}")) {
            arrayOf(getTab(level), "literal", node.token.character.toString(), LR)[it.value.toInt()]
        })
    }

    override fun visit(node: RepetitionNode) {
        builder.append("{0}<{1} min='{2}' max='{3}'>{4}".replace(Regex("\\{([0-9])}")) {
            arrayOf(
                getTab(level),
                "repetition",
                node.token.minOccurs.toString(),
                node.token.maxOccurs.toString(),
                LR
            )[it.value.toInt()]
        })
        level++
        for (childNode in node.childNodes) {
            childNode.accept(this)
        }
        level--
        builder.append("{0}</{1}>{2}".replace(Regex("\\{([0-9])}")) {
            arrayOf(getTab(level), "repetition", LR)[it.value.toInt()]
        })
    }

    override fun visit(node: ConcatenationNode) {
        builder.append("{0}<{1}>{2}".replace(Regex("\\{([0-9])}")) {
            arrayOf(getTab(level), "sequence", LR)[it.value.toInt()]
        })
        level++
        for (childNode in node.childNodes) {
            childNode.accept(this)
        }
        level--
        builder.append("{0}</{1}>{2}".replace(Regex("\\{([0-9])}")) {
            arrayOf(getTab(level), "sequence", LR)[it.value.toInt()]
        })
    }

    override fun visit(node: ParenthesisNode) {
        builder.append("{0}<{1}>{2}".replace(Regex("\\{([0-9])}")) {
            arrayOf(getTab(level), "parenthesis", LR)[it.value.toInt()]
        })
        level++
        for (childNode in node.childNodes) {
            childNode.accept(this)
        }
        level--
        builder.append("{0}</{1}>{2}".replace(Regex("\\{([0-9])}")) {
            arrayOf(getTab(level), "parenthesis", LR)[it.value.toInt()]
        })
    }

    override fun visit(node: AlternationNode) {
        builder.append("{0}<{1}>{2}".replace(Regex("\\{([0-9])}")) {
            arrayOf(getTab(level), "alternation", LR)[it.value.toInt()]
        })
        level++
        for (childNode in node.childNodes) {
            childNode.accept(this)
        }
        level--
        builder.append("{0}</{1}>{2}".replace(Regex("\\{([0-9])}")) {
            arrayOf(getTab(level), "alternation", LR)[it.value.toInt()]
        })
    }

    override fun visit(node: RangeNode) {
        builder.append("{0}<{1}>{2}".replace(Regex("\\{([0-9])}")) {
            arrayOf(getTab(level), "range", LR)[it.value.toInt()]
        })
        level++
        for (childNode in node.childNodes) {
            childNode.accept(this)
        }
        level--
        builder.append("{0}</{1}>{2}".replace(Regex("\\{([0-9])}")) {
            arrayOf(getTab(level), "range", LR)[it.value.toInt()]
        })
    }

    override fun visit(node: BracketNode) {
        builder.append("{0}<{1}>{2}".replace(Regex("\\{([0-9])}")) {
            arrayOf(getTab(level), "set", LR)[it.value.toInt()]
        })
        level++
        for (childNode in node.childNodes) {
            childNode.accept(this)
        }
        level--
        builder.append("{0}</{1}>{2}".replace(Regex("\\{([0-9])}")) {
            arrayOf(getTab(level), "set", LR)[it.value.toInt()]
        })
    }

    override fun visit(node: NotNode) {
        builder.append("{0}<{1}>{2}".replace(Regex("\\{([0-9])}")) {
            arrayOf(getTab(level), "not", LR)[it.value.toInt()]
        })
        level++
        for (childNode in node.childNodes) {
            childNode.accept(this)
        }
        level--
        builder.append("{0}</{1}>{2}".replace(Regex("\\{([0-9])}")) {
            arrayOf(getTab(level), "not", LR)[it.value.toInt()]
        })
    }

    companion object {
        const val LR: String = "\n"
        private const val TAB_SPACES: Int = 3

        fun visit(node: INode): String {
            val visitor = XmlVisitor()
            node.accept(visitor)
            return visitor.builder.toString()
        }

        private fun getTab(tabLevel: Int): String =
            " ".repeat(tabLevel * TAB_SPACES)

    }
}
