package femme.fatale.gen.parser.intf

import femme.fatale.gen.lexer.intf.TokenBuilder
import femme.fatale.gen.lexer.tokens.BracketRightToken
import femme.fatale.gen.lexer.tokens.LiteralToken
import femme.fatale.gen.lexer.tokens.NotToken
import femme.fatale.gen.parser.nodes.BracketNode
import femme.fatale.gen.parser.nodes.LiteralNode
import femme.fatale.gen.parser.nodes.NotNode
import femme.fatale.gen.parser.nodes.RangeNode

internal object NodeBuilder {
    fun buildLiteralNode(literalToken: LiteralToken): LiteralNode =
        LiteralNode(literalToken)

    private fun buildRangeNode(from: LiteralToken, to: LiteralToken): RangeNode =
        RangeNode(TokenBuilder.buildRangeToken(), buildLiteralNode(from), buildLiteralNode(to))

    fun buildAnyNode(): RangeNode =
        buildRangeNode(TokenBuilder.buildLiteralToken(32.toChar()), TokenBuilder.buildLiteralToken(126.toChar()))

    fun buildNumericNode(): RangeNode =
        buildRangeNode(TokenBuilder.buildLiteralToken(48.toChar()), TokenBuilder.buildLiteralToken(57.toChar()))

    fun buildWordNode(): BracketNode =
        BracketNode(
            BracketRightToken(),
            buildNumericNode(),
            buildRangeNode(TokenBuilder.buildLiteralToken(65.toChar()), TokenBuilder.buildLiteralToken(90.toChar())),
            buildRangeNode(TokenBuilder.buildLiteralToken(97.toChar()), TokenBuilder.buildLiteralToken(122.toChar())),
            buildLiteralNode(TokenBuilder.buildLiteralToken(95.toChar()))
        )

    fun buildWhitespaceNode(): BracketNode =
        BracketNode(
            BracketRightToken(),
            buildLiteralNode(TokenBuilder.buildLiteralToken(32.toChar())),
            buildLiteralNode(TokenBuilder.buildLiteralToken(9.toChar()))
        )

    fun buildNonNumericNode(): NotNode =
        NotNode(NotToken(), buildNumericNode())

    fun buildNonWordNode(): NotNode =
        NotNode(NotToken(), buildWordNode())

    fun buildNonWhitespaceNode(): NotNode =
        NotNode(NotToken(), buildWhitespaceNode())
}
