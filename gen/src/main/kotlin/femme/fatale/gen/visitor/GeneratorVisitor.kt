package femme.fatale.gen.visitor

import femme.fatale.gen.lexer.intf.TokenBuilder
import femme.fatale.gen.lexer.intf.TokenType
import femme.fatale.gen.parser.intf.INode
import femme.fatale.gen.parser.intf.NodeBuilder
import femme.fatale.gen.parser.nodes.*
import femme.fatale.gen.visitor.intf.IVisitor
import kotlin.random.Random

internal class GeneratorVisitor private constructor() : IVisitor {
    private val defaultMaxOccurs: Int = 11
    private val builder: StringBuilder = StringBuilder()


    override fun visit(node: LiteralNode) {
        builder.append(node.token.character.toString())
    }

    override fun visit(node: RepetitionNode) {
        val index = Random.nextInt(
            node.token.minOccurs,
            if (node.token.maxOccurs > -1) node.token.maxOccurs + 1 else defaultMaxOccurs
        )
        (0 until index).forEach {
            for (childNode in node.childNodes) {
                childNode.accept(this)
            }
        }
    }

    override fun visit(node: AlternationNode) {
        node.childNodes[Random.nextInt(0, 2)].accept(this)
    }

    override fun visit(node: ConcatenationNode) {
        for (childNode in node.childNodes) {
            childNode.accept(this)
        }
    }

    override fun visit(node: ParenthesisNode) {
        for (childNode in node.childNodes) {
            childNode.accept(this)
        }
    }

    override fun visit(node: BracketNode) {
        if (node.childNodes[0].token.tokenType == TokenType.Not) {
            node.childNodes[0].accept(this)
            return
        }

        var current: INode = node
        while (current.childNodes[0].token.tokenType == TokenType.Concatenation) {
            current = node.childNodes[0]
        }

        val nodes: MutableMap<Char, LiteralNode> = mutableMapOf()
        for (expandedNode in expand(current)) {
            if (!nodes.containsValue(expandedNode)) {
                nodes[expandedNode.token.character] = (expandedNode)
            }
        }
        nodes.values.random().accept(this)
    }

    override fun visit(node: RangeNode) {
        LiteralNode(
            TokenBuilder.buildLiteralToken(
                Random.nextInt(
                    (node.childNodes[0] as LiteralNode).token.character.code,
                    (node.childNodes[1] as LiteralNode).token.character.code + 1
                ).toChar()
            )
        ).accept(this)
    }

    override fun visit(node: NotNode) {
        val nodes: MutableMap<Char, LiteralNode> = mutableMapOf()
        for (i in 32 until 126) {
            nodes[i.toChar()] = (NodeBuilder.buildLiteralNode(TokenBuilder.buildLiteralToken(i.toChar())))
        }

        var current: INode = node
        while (current.childNodes[0].token.tokenType == TokenType.Concatenation) {
            current = node.childNodes[0]
        }

        for (expandedNode in expand(current)) {
            if (nodes.containsValue(expandedNode)) {
                nodes.remove(expandedNode.token.character)
            }
        }

        nodes.values.random().accept(this)
    }

    companion object {
        fun visit(node: INode): String {
            val visitor = GeneratorVisitor()
            node.accept(visitor)
            return visitor.builder.toString()
        }


        private fun expand(bracketNode: INode): List<LiteralNode> {
            val result = mutableListOf<LiteralNode>()
            for (childNode in bracketNode.childNodes) {
                when (childNode.token.tokenType) {
                    TokenType.Literal ->
                        result += childNode as LiteralNode

                    TokenType.Range -> {
                        for (i in (childNode.childNodes[0] as LiteralNode).token.character.code..(childNode.childNodes[1] as LiteralNode).token.character.code) {
                            result += NodeBuilder.buildLiteralNode(TokenBuilder.buildLiteralToken(i.toChar()))
                        }
                    }

                    TokenType.BracketRight -> {
                        for (node in expand(childNode)) {
                            result += node
                        }
                    }

                    else -> {}
                }
            }
            return result
        }
    }

}
