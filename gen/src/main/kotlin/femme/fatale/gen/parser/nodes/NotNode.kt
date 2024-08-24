package femme.fatale.gen.parser.nodes

import femme.fatale.gen.lexer.tokens.NotToken
import femme.fatale.gen.parser.intf.INode
import femme.fatale.gen.parser.intf.NodeBase
import femme.fatale.gen.parser.intf.NodeType
import femme.fatale.gen.visitor.intf.IVisitor

internal class NotNode(token: NotToken) : NodeBase(token) {

    constructor(token: NotToken, children: INode) : this(token) {
        childNodes.add(children)
    }

    override val nodeType: NodeType = NodeType.UnaryOperator

    override val precedence: Int = 11

    override fun accept(visitor: IVisitor) {
        visitor.visit(this)
    }
}
