package at.styrialabs.bucketlist.domain

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity
data class BucketListElement (
    @PrimaryKey(autoGenerate = true) val id: Long = 0,

    val text : String,
    val createdAt : LocalDateTime,
    val doneAt : LocalDateTime? = null
    )
