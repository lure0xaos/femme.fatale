package femme.fatale.gen.parser

import femme.fatale.gen.lexer.Lexer
import femme.fatale.gen.lexer.intf.IToken
import femme.fatale.gen.parser.intf.INode
import femme.fatale.gen.parser.intf.ParseState
import java.util.*

internal class Parser {
    private val states: Stack<ParseState> = Stack()
    private var currentState: ParseState = ParseState()
    private val tokens: MutableList<IToken> = mutableListOf()

    lateinit var current: IToken

    fun parse(expression: String): INode =
        parse(Lexer().tokenize(expression))

    private fun parse(tokens: List<IToken>): INode {
        this.tokens.clear()
        this.tokens.addAll(tokens)
        states.clear()
        currentState = ParseState()
        this.tokens.forEach { token ->
            current = token
            currentState.handle(this)
        }
        while (!states.isEmpty())
            endState()
        return currentState.close()
    }

    fun toState(state: ParseState) {
        states.push(currentState)
        currentState = state
    }

    fun endState() {
        val toState = states.pop()
        toState.addOperand(currentState.close())
        currentState = toState
    }

}
