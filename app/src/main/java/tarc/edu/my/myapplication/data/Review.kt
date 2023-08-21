package tarc.edu.my.myapplication.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "review")
data class Review(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    var title: String,
    var desc: String,
    var rate: String,
    var acceptance: String,
    var comment:String): Parcelable