package at.styrialabs.bucketlist.repository

import androidx.lifecycle.LiveData
import androidx.room.*
import at.styrialabs.bucketlist.domain.BucketListElement

@Dao
interface BucketListElementRepository {
    @Insert
    suspend fun insertAll(vararg product: BucketListElement)

    @Update
    suspend fun updateAll(vararg countList: BucketListElement)

    @Delete
    suspend fun deleteAll(vararg product: BucketListElement)

    @Query("SELECT * FROM bucketlistelement")
    fun getAll(): LiveData<List<BucketListElement>>

}