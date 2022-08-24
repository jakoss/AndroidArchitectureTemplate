@file:Suppress("unused")

package pl.jsyty.architecturetemplate.infrastructure.extensions

import java.time.*
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

fun LocalDate.localisedDate(formatStyle: FormatStyle = FormatStyle.SHORT): String {
    return this.format(DateTimeFormatter.ofLocalizedDate(formatStyle))
}

fun LocalDateTime.localisedDate(formatStyle: FormatStyle = FormatStyle.SHORT): String {
    return this.format(DateTimeFormatter.ofLocalizedDate(formatStyle))
}

fun LocalTime.localisedTime(formatStyle: FormatStyle = FormatStyle.SHORT): String {
    return this.format(DateTimeFormatter.ofLocalizedTime(formatStyle))
}

fun LocalDateTime.localisedTime(formatStyle: FormatStyle = FormatStyle.SHORT): String {
    return this.format(DateTimeFormatter.ofLocalizedTime(formatStyle))
}

fun LocalDateTime.localisedDateTime(formatStyle: FormatStyle = FormatStyle.SHORT): String {
    return this.format(DateTimeFormatter.ofLocalizedDateTime(formatStyle))
}
