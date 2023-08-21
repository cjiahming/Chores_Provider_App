package tarc.edu.my.myapplication.data

import android.support.annotation.WorkerThread
import androidx.lifecycle.LiveData

class ReviewRepository(private val reviewDao: ReviewDao) {
    val readAllUser: LiveData<List<Review>> = reviewDao.getAllReview()



    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun add(review: Review) {
        reviewDao.insert(review)
    }

    fun getReviewInfo(id: Int): LiveData<Review> {
        return reviewDao.getReviewInfo(id)
    }
}