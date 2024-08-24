package femme.fatale.gen.parser.nodes

import femme.fatale.gen.lexer.tokens.RepetitionToken
import femme.fatale.gen.parser.intf.NodeBase
import femme.fatale.gen.parser.intf.NodeType
import femme.fatale.gen.visitor.intf.IVisitor

internal class RepetitionNode(token: RepetitionToken) : NodeBase(token) {

    override val token: RepetitionToken = super.token as RepetitionToken

    override val nodeType: NodeType = NodeType.UnaryOperator

    override val precedence: Int = 150

    override fun accept(visitor: IVisitor) {
        visitor.visit(this)
    }
}
