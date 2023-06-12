package pl.jsyty.architecturetemplate.infrastructure.mapping

import java.time.*

/**
 * In our case the [OffsetDateTime] we're getting from the api represents time in UTC.
 * We can convert that to [LocalDateTime] just by moving that by the offset of current zone to UTC ([localZoneOffset] field stores that offset).
 */
interface DateTimeConverters {
    fun asOffsetDateTime(source: LocalDateTime): OffsetDateTime = source.atOffset(localZoneOffset)

    companion object {
        val localZoneOffset: ZoneOffset by lazy { OffsetDateTime.now().offset }
    }
}
