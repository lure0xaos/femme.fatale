package femme.fatale.gen.lexer

import femme.fatale.gen.lexer.intf.IContext
import femme.fatale.gen.lexer.intf.IState
import femme.fatale.gen.lexer.intf.IToken
import femme.fatale.gen.lexer.states.LiteralState
import java.util.*

internal class Lexer : IContext {
    private val states: Stack<IState> = Stack()
    private var currentState: IState = LiteralState()
    private var characters: CharArray = CharArray(0)

    override fun tokenize(expression: String): List<IToken> {
        states.clear()
        currentState = LiteralState()
        characters = expression.toCharArray()
        val result = mutableListOf<IToken>()
        for (character in characters) {
            current = character
            val token = currentState.handle(this)
            if (token != null) {
                result += token
            }
        }
        return result
    }

    override fun toState(state: IState) {
        states.push(currentState)
        currentState = state
    }

    override fun endState() {
        currentState = states.pop()
    }

    override var current: Char = '\u0000'


}
