package femme.fatale.gen.lexer.states

import femme.fatale.gen.lexer.intf.IContext
import femme.fatale.gen.lexer.intf.IState
import femme.fatale.gen.lexer.intf.TokenBuilder
import femme.fatale.gen.lexer.tokens.RepetitionToken

internal class RepetitionState : IState {
    private var minOccurs = -1
    private var maxOccurs = -1
    private var isParsingMinOccurs = true
    private var isParsingFirstValue = true
    private var currentNumber: Int = 0
    override fun handle(context: IContext): RepetitionToken? {
        when (val character = context.current) {
            ',' -> {
                if (isParsingMinOccurs) {
                    if (isParsingFirstValue) error("missing minOccurs")
                    minOccurs = currentNumber
                    currentNumber = 0
                    isParsingMinOccurs = false
                    isParsingFirstValue = true
                } else error("too many ,")
            }

            '}' -> {
                if (isParsingMinOccurs) {
                    if (isParsingFirstValue) error("missing minOccurs")
                    //minOccurs equals maxOccurs
                    minOccurs = currentNumber
                    maxOccurs = currentNumber
                } else {
                    maxOccurs = if (isParsingFirstValue) -1 else currentNumber
                }
                context.endState()
                return TokenBuilder.buildRepetitionToken(minOccurs, maxOccurs)
            }

            else -> {
                currentNumber = currentNumber * 10 + character.toString().toInt()
                isParsingFirstValue = false
            }

        }
        return null
    }
}
