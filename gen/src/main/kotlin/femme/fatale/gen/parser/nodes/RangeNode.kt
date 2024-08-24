package femme.fatale.gen.parser.nodes

import femme.fatale.gen.lexer.tokens.RangeToken
import femme.fatale.gen.parser.intf.INode
import femme.fatale.gen.parser.intf.NodeBase
import femme.fatale.gen.parser.intf.NodeType
import femme.fatale.gen.visitor.intf.IVisitor

internal class RangeNode(token: RangeToken) : NodeBase(token) {

    constructor(token: RangeToken, from: INode, to: INode) : this(token) {
        childNodes.add(from)
        childNodes.add(to)
    }

    override val nodeType: NodeType = NodeType.BinaryOperator

    override val precedence: Int = 130

    override fun accept(visitor: IVisitor) {
        visitor.visit(this)
    }
}
