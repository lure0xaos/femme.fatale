package femme.fatale.model.units

@JvmInline
value class Color(val value: String) {
    init {
        require(value.matches(Regex("#[A-Za-z0-9]{3,8}"))) {
            "invalid color $value"
        }
    }

}
