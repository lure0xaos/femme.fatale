package femme.fatale.gen.parser.intf

import femme.fatale.gen.lexer.intf.IToken

internal abstract class NodeBase protected constructor(override val token: IToken) : INode {
    override val childNodes: MutableList<INode> = mutableListOf()
}
