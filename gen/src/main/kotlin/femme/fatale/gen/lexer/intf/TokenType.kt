package femme.fatale.gen.lexer.intf

internal enum class TokenType {
    Literal,
    Repetition,
    ParenthesisLeft,
    ParenthesisRight,
    Concatenation,
    Alternation,
    BracketLeft,
    BracketRight,
    Range,
    Not,
    Any,
    Numeric,
    Word,
    Whitespace,
    NonNumeric,
    NonWord,
    NonWhitespace
}
