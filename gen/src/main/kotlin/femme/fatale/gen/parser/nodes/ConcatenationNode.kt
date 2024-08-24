package femme.fatale.gen.parser.nodes

import femme.fatale.gen.lexer.tokens.ConcatenationToken
import femme.fatale.gen.parser.intf.NodeBase
import femme.fatale.gen.parser.intf.NodeType
import femme.fatale.gen.visitor.intf.IVisitor

internal class ConcatenationNode(token: ConcatenationToken) : NodeBase(token) {

    override val nodeType: NodeType = NodeType.BinaryOperator

    override val precedence: Int = 50


    override fun accept(visitor: IVisitor) {
        visitor.visit(this)
    }
}
