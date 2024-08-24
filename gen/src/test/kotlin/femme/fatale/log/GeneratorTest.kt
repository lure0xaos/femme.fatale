package femme.fatale.log

import femme.fatale.gen.RegExpDataGenerator
import kotlin.test.Test
import kotlin.test.assertTrue

class GeneratorTest {
    @Test
    fun testGenerator() {
        val pattern = "He[lr]{2,3}o*"
        val rxrdg = RegExpDataGenerator(pattern)
        for (i in 0..10) {
            val text = rxrdg.generate()
            val message = "$pattern: $text"
            assertTrue(Regex(pattern).matches(text), message)
            System.getLogger(this::class.qualifiedName).log(System.Logger.Level.INFO) { message }
        }
    }

}
