package youtubeapplication.kotlin.com.android_kotlin_youtubeapplication.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Snippet(
        @SerializedName("publishedAt")
        var mPublishedAt: String = "",
        @SerializedName("channelId")
        var mChannelId: String = "",
        @SerializedName("title")
        var mTitle: String = "",
        @SerializedName("description")
        var mDescription: String = "",
        @SerializedName("thumbnails")
        var mThumbnails: Thumnail = Thumnail(),
        @SerializedName("channelTitle")
        var mChannelTitle: String = ""
) : Parcelable
