package femme.fatale.gen.visitor.intf

internal fun interface IVisitable {
    fun accept(visitor: IVisitor)
}
