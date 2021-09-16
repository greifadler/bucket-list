package at.styrialabs.bucketlist.domain

import androidx.room.TypeConverter
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.*

class Converters {
    companion object {
        val DATE_TIME_FORMAT: DateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")
    }

    //Date
    @TypeConverter
    fun fromTimestampToDate(value: Long?): LocalDateTime? {
        return value?.let { LocalDateTime.ofInstant(Instant.ofEpochMilli(value), ZoneOffset.UTC) }
    }

    @TypeConverter
    fun fromDateToTimestamp(date: LocalDateTime?): Long? {
        return date?.atZone(ZoneOffset.UTC)?.toInstant()?.toEpochMilli()
    }

    //UUID
    @TypeConverter
    fun fromUUIDToString(uuid: UUID): String {
        return uuid.toString()
    }

    @TypeConverter
    fun fromStringToUUID(string: String): UUID {
        return UUID.fromString(string)
    }
}