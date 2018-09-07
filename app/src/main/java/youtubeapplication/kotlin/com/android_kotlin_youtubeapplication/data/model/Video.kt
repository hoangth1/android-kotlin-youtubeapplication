package youtubeapplication.kotlin.com.android_kotlin_youtubeapplication.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class Video(
        @SerializedName("id")
        var mId: String,
        @SerializedName("snippet")
        var mSnipper:Snippet

) : Parcelable
