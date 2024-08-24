package femme.fatale.gen.lexer.states

import femme.fatale.gen.lexer.intf.IContext
import femme.fatale.gen.lexer.intf.IState
import femme.fatale.gen.lexer.intf.IToken
import femme.fatale.gen.lexer.intf.TokenBuilder

internal class BeginSetState : IState {
    override fun handle(context: IContext): IToken? {
        when (context.current) {
            '^' ->
                return TokenBuilder.buildNotToken()

            '\\' -> {
                context.toState(SetState())
                context.toState(EscapeState())
            }

            else -> {
                context.toState(SetState())
                return TokenBuilder.buildLiteralToken(context.current)
            }
        }
        return null
    }
}
