package tarc.edu.my.myapplication.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class ReviewViewModel(application: Application) : AndroidViewModel(application) {
    var readAllReview: LiveData<List<Review>>
    private val repository: ReviewRepository
    var selectedIndex: Int = -1

    init {
        val reviewDao = ReviewDatabase.getDatabase(application).reviewDao()
        repository = ReviewRepository(reviewDao)
        readAllReview = repository.readAllUser
    }

    fun addReview(review: Review) = viewModelScope.launch {
        repository.add(review)
    }

    fun getReviewInfo(id: Int): LiveData<Review> {
        return repository.getReviewInfo(id)
    }
}