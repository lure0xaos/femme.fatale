package femme.fatale.gen

import femme.fatale.gen.parser.Parser
import femme.fatale.gen.parser.intf.INode
import femme.fatale.gen.visitor.GeneratorVisitor

class RegExpDataGenerator(pattern: String) {
    init {
        require(pattern.isNotBlank()) { "pattern is empty" }
    }

    private val syntaxTreeRootNode: INode = Parser().parse(pattern)

    fun generate(): String =
        GeneratorVisitor.visit(syntaxTreeRootNode)
}
