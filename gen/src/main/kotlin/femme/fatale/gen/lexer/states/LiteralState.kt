package femme.fatale.gen.lexer.states

import femme.fatale.gen.lexer.intf.IContext
import femme.fatale.gen.lexer.intf.IState
import femme.fatale.gen.lexer.intf.IToken
import femme.fatale.gen.lexer.intf.TokenBuilder

internal class LiteralState : IState {
    override fun handle(context: IContext): IToken? {
        when (context.current) {
            '(' ->
                return TokenBuilder.buildParenthesisLeftToken()

            ')' ->
                return TokenBuilder.buildParenthesisRightToken()

            '{' ->
                context.toState(RepetitionState())

            '\\' ->
                context.toState(EscapeState())

            '[' -> {
                context.toState(BeginSetState())
                return TokenBuilder.buildBracketLeftToken()
            }

            '*' ->
                return TokenBuilder.buildZeroOrMoreToken()

            '?' ->
                return TokenBuilder.buildZeroOrOneToken()

            '+' ->
                return TokenBuilder.buildOneOrMoreToken()

            '|' ->
                return TokenBuilder.buildAlternationToken()

            '.' ->
                return TokenBuilder.buildAnyToken()

            else ->
                return TokenBuilder.buildLiteralToken(context.current)
        }
        return null
    }
}
