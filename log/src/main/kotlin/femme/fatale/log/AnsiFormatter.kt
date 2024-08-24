package femme.fatale.log

import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.logging.Formatter
import java.util.logging.Level
import java.util.logging.LogRecord

class AnsiFormatter : Formatter() {

    private val dateTimeFormat = "yyyy-MM-dd HH:mm:ss.SSS"
    private val defaultColor = Ansi.WHITE
    private val levelNameLength = 5
    private val grayColor = Ansi.BLACK_BRIGHT
    private val redColor = Ansi.RED
    private val loggerNameColor = Ansi.PURPLE
    private val loggerNameLength = 30
    private val sourceColor = Ansi.GREEN

    private val dateTimeFormatter = DateTimeFormatter.ofPattern(dateTimeFormat)

    override fun format(record: LogRecord): String {
        val significant = record.level in arrayOf(Level.SEVERE, Level.WARNING, Level.INFO)
        val color = if (significant) defaultColor else grayColor
        return buildString {
            append(color.wrap(dateTimeFormatter.format(record.instant.atZone(ZoneId.systemDefault()))))
            append(color.wrap(" ["))
                .append(
                    levelColor(record.level).wrap(
                        levelName(record.level).takeLast(levelNameLength).padStart(levelNameLength)
                    )
                )
                .append(color.wrap("] "))
            append(
                loggerNameColor.ifColor(significant, color)
                    .wrap((record.loggerName ?: "").take(loggerNameLength).padStart(loggerNameLength))
            )
            append(when {
                record.sourceClassName != null && record.sourceMethodName == null ->
                    record.sourceClassName

                record.sourceClassName != null && record.sourceMethodName != null ->
                    "${record.sourceClassName}#${record.sourceMethodName}"

                else -> null
            }?.let { color.wrap(" (") + sourceColor.ifColor(significant, color).wrap(it) + color.wrap(") ") } ?: "")
            append(formatMessage(record)?.split('\n')?.joinToString("\n") { color.wrap(it) } ?: "")
            append(record.thrown?.message?.let { color.wrap(": ") + color.wrap(it) } ?: "")
            append(record.thrown?.stackTraceToString()?.split('\n')?.joinToString("\n") {
                (if (it.trimStart().startsWith("at ") && record.sourceClassName.substringBeforeLast('.') !in it)
                    grayColor else redColor).wrap(it)
            }?.let { '\n' + it } ?: "")
            append('\n')
        }
    }

    private fun levelName(level: Level): String =
        when (level) {
            Level.SEVERE -> "ERROR"
            Level.WARNING -> "WARN"
            Level.INFO -> "INFO"
            Level.FINE -> "DEBUG"
            Level.FINER -> "TRACE"
            Level.FINEST -> "TRACE"
            else -> ""
        }

    private fun levelColor(level: Level): Ansi =
        when (level) {
            Level.SEVERE -> Ansi.RED
            Level.WARNING -> Ansi.YELLOW
            Level.INFO -> Ansi.BLUE
            else -> grayColor
        }

}
