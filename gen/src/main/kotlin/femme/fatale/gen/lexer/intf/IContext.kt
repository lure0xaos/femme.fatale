package femme.fatale.gen.lexer.intf

internal interface IContext {
    fun tokenize(expression: String): List<IToken>
    fun toState(state: IState)
    fun endState()
    val current: Char
}
