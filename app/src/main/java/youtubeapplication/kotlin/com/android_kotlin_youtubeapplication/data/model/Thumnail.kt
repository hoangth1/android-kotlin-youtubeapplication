package youtubeapplication.kotlin.com.android_kotlin_youtubeapplication.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class Thumnail(
        @SerializedName("high")
        var mHight: High
) : Parcelable
