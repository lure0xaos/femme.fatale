package femme.fatale.gen.parser.intf

import femme.fatale.gen.lexer.intf.IToken
import femme.fatale.gen.visitor.intf.IVisitable

internal interface INode : IVisitable {
    val childNodes: MutableList<INode>
    val token: IToken
    val nodeType: NodeType
    val precedence: Int
}
