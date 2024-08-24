package femme.fatale.gen.lexer.states

import femme.fatale.gen.lexer.intf.IContext
import femme.fatale.gen.lexer.intf.IState
import femme.fatale.gen.lexer.intf.IToken
import femme.fatale.gen.lexer.intf.TokenBuilder

internal class EscapeState : IState {
    override fun handle(context: IContext): IToken {
        context.endState()
        return when (context.current) {
            'd' -> TokenBuilder.buildNumericToken()
            'w' -> TokenBuilder.buildWordToken()
            's' -> TokenBuilder.buildWhitespaceToken()
            'D' -> TokenBuilder.buildNonNumericToken()
            'W' -> TokenBuilder.buildNonWordToken()
            'S' -> TokenBuilder.buildNonWhitespaceToken()
            else -> TokenBuilder.buildLiteralToken(context.current)
        }
    }
}
