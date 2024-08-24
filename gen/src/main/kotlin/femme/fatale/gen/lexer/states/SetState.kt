package femme.fatale.gen.lexer.states

import femme.fatale.gen.lexer.intf.IContext
import femme.fatale.gen.lexer.intf.IState
import femme.fatale.gen.lexer.intf.IToken
import femme.fatale.gen.lexer.intf.TokenBuilder

internal class SetState : IState {
    override fun handle(context: IContext): IToken? {
        when (context.current) {
            ']' -> {
                context.endState()
                context.endState()
                return TokenBuilder.buildBracketRightToken()
            }

            '-' ->
                return TokenBuilder.buildRangeToken()

            '\\' ->
                context.toState(EscapeState())

            else ->
                return TokenBuilder.buildLiteralToken(context.current)
        }
        return null
    }
}
