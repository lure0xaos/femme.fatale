package femme.fatale.gen.lexer.tokens

import femme.fatale.gen.lexer.intf.IToken
import femme.fatale.gen.lexer.intf.TokenType

internal class RepetitionToken(var minOccurs: Int, var maxOccurs: Int) : IToken {
    override val tokenType: TokenType = TokenType.Repetition
}
