package femme.fatale.gen.parser.nodes

import femme.fatale.gen.lexer.tokens.ParenthesisRightToken
import femme.fatale.gen.parser.intf.NodeBase
import femme.fatale.gen.parser.intf.NodeType
import femme.fatale.gen.visitor.intf.IVisitor

internal class ParenthesisNode(token: ParenthesisRightToken) : NodeBase(token) {

    override val nodeType: NodeType = NodeType.UnaryOperator

    override val precedence: Int = 25

    override fun accept(visitor: IVisitor) {
        visitor.visit(this)
    }
}
