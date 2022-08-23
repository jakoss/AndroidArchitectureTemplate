package pl.jsyty.architecturetemplate.infrastructure.mapping

import java.time.*

class DateConversionMapper {
    fun asLocalDateTime(offsetDateTime: OffsetDateTime?): LocalDateTime? =
        offsetDateTime?.toLocalDateTime()

    fun asOffsetDateTime(localDateTime: LocalDateTime?): OffsetDateTime? =
        localDateTime?.atOffset(localZoneOffset)

    companion object {
        val localZoneOffset: ZoneOffset by lazy { OffsetDateTime.now().offset }
    }
}
