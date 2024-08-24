package femme.fatale.gen.visitor.intf

import femme.fatale.gen.parser.nodes.*

internal interface IVisitor {
    fun visit(node: LiteralNode)
    fun visit(node: RepetitionNode)
    fun visit(node: ConcatenationNode)
    fun visit(node: ParenthesisNode)
    fun visit(node: AlternationNode)
    fun visit(node: RangeNode)
    fun visit(node: BracketNode)
    fun visit(node: NotNode)
}
