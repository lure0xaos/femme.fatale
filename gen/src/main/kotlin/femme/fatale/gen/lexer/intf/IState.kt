package femme.fatale.gen.lexer.intf

internal fun interface IState {
    fun handle(context: IContext): IToken?
}
