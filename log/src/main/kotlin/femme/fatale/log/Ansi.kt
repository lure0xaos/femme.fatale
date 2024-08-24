package femme.fatale.log

enum class Ansi(val value: String) {
    RESET("\u001B[0m"),
    BLACK("\u001B[30m"),
    RED("\u001B[31m"),
    GREEN("\u001B[32m"),
    YELLOW("\u001B[33m"),
    BLUE("\u001B[34m"),
    PURPLE("\u001B[35m"),
    CYAN("\u001B[36m"),
    WHITE("\u001B[37m"),
    BLACK_BACKGROUND("\u001B[40m"),
    RED_BACKGROUND("\u001B[41m"),
    GREEN_BACKGROUND("\u001B[42m"),
    YELLOW_BACKGROUND("\u001B[43m"),
    BLUE_BACKGROUND("\u001B[44m"),
    PURPLE_BACKGROUND("\u001B[45m"),
    CYAN_BACKGROUND("\u001B[46m"),
    WHITE_BACKGROUND("\u001B[47m"),
    BLACK_BRIGHT("\u001B[90m"),
    RED_BRIGHT("\u001B[91m"),
    GREEN_BRIGHT("\u001B[92m"),
    YELLOW_BRIGHT("\u001B[93m"),
    BLUE_BRIGHT("\u001B[94m"),
    PURPLE_BRIGHT("\u001B[95m"),
    CYAN_BRIGHT("\u001B[96m"),
    WHITE_BRIGHT("\u001B[97m"),
    BLACK_BRIGHT_BACKGROUND("\u001B[100m"),
    RED_BRIGHT_BACKGROUND("\u001B[101m"),
    GREEN_BRIGHT_BACKGROUND("\u001B[102m"),
    YELLOW_BRIGHT_BACKGROUND("\u001B[103m"),
    BLUE_BRIGHT_BACKGROUND("\u001B[104m"),
    PURPLE_BRIGHT_BACKGROUND("\u001B[105m"),
    CYAN_BRIGHT_BACKGROUND("\u001B[106m"),
    WHITE_BRIGHT_BACKGROUND("\u001B[107m"),
    ;

    override fun toString(): String =
        value

    fun wrap(text: String, color2: Ansi = RESET): String =
        value + text + color2.value

    fun ifColor(condition: Boolean, color2: Ansi): Ansi =
        if (condition) this else color2
}
