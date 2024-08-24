package femme.fatale.gen.parser.nodes

import femme.fatale.gen.lexer.tokens.BracketRightToken
import femme.fatale.gen.parser.intf.INode
import femme.fatale.gen.parser.intf.NodeBase
import femme.fatale.gen.parser.intf.NodeType
import femme.fatale.gen.visitor.intf.IVisitor

internal class BracketNode(token: BracketRightToken) : NodeBase(token) {

    constructor(token: BracketRightToken, vararg children: INode) : this(token) {
        childNodes += children
    }

    override val nodeType: NodeType = NodeType.UnaryOperator

    override val precedence: Int = 10

    override fun accept(visitor: IVisitor) {
        visitor.visit(this)
    }
}
