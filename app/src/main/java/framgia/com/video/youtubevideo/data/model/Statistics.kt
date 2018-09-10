package framgia.com.video.youtubevideo.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Statistics(
        @SerializedName("viewCount")
        var viewCount: String
) : Parcelable
