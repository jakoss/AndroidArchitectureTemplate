package pl.jsyty.architecturetemplate.core.networking.serializers

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.time.*

/**
 * Serializer that converts json string to [OffsetDateTime]
 */
object OffsetDateTimeSerializer : KSerializer<OffsetDateTime> {
    private val localZoneOffset: ZoneOffset by lazy { OffsetDateTime.now().offset }

    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("OffsetDateTime", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): OffsetDateTime {
        // we have to add :00Z because open meteo returns date time in format of: 2022-08-22T15:00
        val instant = Instant.parse(decoder.decodeString() + ":00Z")
        return instant.atOffset(localZoneOffset)
    }

    override fun serialize(encoder: Encoder, value: OffsetDateTime) {
        return encoder.encodeString(value.toInstant().toString())
    }
}
