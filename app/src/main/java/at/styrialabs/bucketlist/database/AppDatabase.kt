package at.styrialabs.bucketlist.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import at.styrialabs.bucketlist.domain.BucketListElement
import at.styrialabs.bucketlist.domain.Converters
import at.styrialabs.bucketlist.repository.BucketListElementRepository

@Database(entities = [BucketListElement::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase: RoomDatabase(){
    companion object {
        const val DATABASE_NAME = "database"
    }

    abstract fun bucketListElementRepository(): BucketListElementRepository
}