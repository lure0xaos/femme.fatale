package femme.fatale.gen.lexer.tokens

import femme.fatale.gen.lexer.intf.IToken
import femme.fatale.gen.lexer.intf.TokenType

internal class NonWordToken : IToken {
    override val tokenType: TokenType = TokenType.NonWord
}
