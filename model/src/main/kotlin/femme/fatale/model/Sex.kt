package femme.fatale.model

enum class Sex(
    val hasBreasts: Boolean,
    val hasVagina: Boolean,
    val hasPenis: Boolean,
) {
    Female(hasBreasts = true, hasVagina = true, hasPenis = false),
    Male(hasBreasts = false, hasVagina = false, hasPenis = true),
    Futanari(hasBreasts = true, hasVagina = true, hasPenis = true)
}
