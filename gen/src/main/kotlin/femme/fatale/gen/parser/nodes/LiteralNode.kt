package femme.fatale.gen.parser.nodes

import femme.fatale.gen.lexer.tokens.LiteralToken
import femme.fatale.gen.parser.intf.NodeBase
import femme.fatale.gen.parser.intf.NodeType
import femme.fatale.gen.visitor.intf.IVisitor

internal class LiteralNode(token: LiteralToken) : NodeBase(token) {

    override val token: LiteralToken = super.token as LiteralToken

    override val nodeType: NodeType = NodeType.Operand

    override val precedence: Int = 100

    override fun accept(visitor: IVisitor) {
        visitor.visit(this)
    }
}
