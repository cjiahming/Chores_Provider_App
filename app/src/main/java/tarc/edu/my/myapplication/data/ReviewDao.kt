package tarc.edu.my.myapplication.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ReviewDao {
    @Query("SELECT * FROM review ORDER BY id ASC")
    fun getAllReview(): LiveData<List<Review>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(review: Review)

    @Query("SELECT * FROM review WHERE id = :id")
    fun getReviewInfo(id: Int): LiveData<Review>
}