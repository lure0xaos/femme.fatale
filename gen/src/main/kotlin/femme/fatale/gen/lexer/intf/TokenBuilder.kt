package femme.fatale.gen.lexer.intf

import femme.fatale.gen.lexer.tokens.*

internal object TokenBuilder {
    private val padlock: Any = Any()
    private val literalTokens: MutableMap<Char, LiteralToken> = mutableMapOf()
    fun buildLiteralToken(character: Char): LiteralToken {
        if (!literalTokens.containsKey(character)) {
            synchronized(padlock) {
                literalTokens[character] = (LiteralToken(character))
            }
        }
        return literalTokens[character]!!
    }

    fun buildZeroOrMoreToken(): RepetitionToken = buildRepetitionToken(0, -1)

    fun buildOneOrMoreToken(): RepetitionToken = buildRepetitionToken(1, -1)

    fun buildZeroOrOneToken(): RepetitionToken = buildRepetitionToken(0, 1)

    fun buildRepetitionToken(minOccurs: Int, maxOccurs: Int): RepetitionToken = RepetitionToken(minOccurs, maxOccurs)

    fun buildParenthesisLeftToken(): ParenthesisLeftToken = ParenthesisLeftToken()

    fun buildParenthesisRightToken(): ParenthesisRightToken = ParenthesisRightToken()

    fun buildAlternationToken(): AlternationToken = AlternationToken()

    fun buildBracketRightToken(): BracketRightToken = BracketRightToken()

    fun buildBracketLeftToken(): BracketLeftToken = BracketLeftToken()

    fun buildRangeToken(): RangeToken = RangeToken()

    fun buildNotToken(): NotToken = NotToken()

    fun buildAnyToken(): AnyToken = AnyToken()

    fun buildNumericToken(): NumericToken = NumericToken()

    fun buildWordToken(): WordToken = WordToken()

    fun buildWhitespaceToken(): WhitespaceToken = WhitespaceToken()

    fun buildNonNumericToken(): NonNumericToken = NonNumericToken()

    fun buildNonWordToken(): NonWordToken = NonWordToken()

    fun buildNonWhitespaceToken(): NonWhitespaceToken = NonWhitespaceToken()
}
