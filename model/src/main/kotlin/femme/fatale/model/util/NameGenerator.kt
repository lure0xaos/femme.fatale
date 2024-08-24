package femme.fatale.model.util

import femme.fatale.gen.RegExpDataGenerator
import femme.fatale.model.Sex

object NameGenerator {
    private const val V = "[aeiouy]"
    private const val C = "[bcdfghjklmnpqrstvwxz]"
    private const val VC = "($V$C)"
    private const val CV = "($C$V)"
    private const val VCV = "($V$C$V)"
    private const val CVV = "($C$V$V)"
    fun generate(sex: Sex): String =
        RegExpDataGenerator(
            if (sex == Sex.Male)
                "$VC{2,4}"
            else
                "$CV{2,4}"
        ).generate().replaceFirstChar { it.uppercaseChar() }
}
