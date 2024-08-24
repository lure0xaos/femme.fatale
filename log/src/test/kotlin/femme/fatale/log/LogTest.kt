package femme.fatale.log

import kotlin.test.BeforeTest
import kotlin.test.Test

class LogTest {

    @BeforeTest
    fun beforeTest() {
        LogConfig
    }

    @Test
    fun testError() {
        System.getLogger(LogTest::class.qualifiedName).log(System.Logger.Level.ERROR) { "error message" }
    }

    @Test
    fun testWarn() {
        System.getLogger(LogTest::class.qualifiedName).log(System.Logger.Level.WARNING) { "warn message" }
    }

    @Test
    fun testDebug() {
        System.getLogger(LogTest::class.qualifiedName).log(System.Logger.Level.DEBUG) { "debug message" }
    }

    @Test
    fun testJson() {
        System.getLogger(LogTest::class.qualifiedName).log(System.Logger.Level.INFO) {
            "\n{\n\t\"name\":\"value\"\n}"
        }
    }

    @Test
    fun testException() {
        System.getLogger(LogTest::class.qualifiedName)
            .log(System.Logger.Level.ERROR, "exception message", Exception("err"))
    }
}
