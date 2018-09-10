package framgia.com.video.youtubevideo.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class High(
        @SerializedName("url")
        var mUrl: String = ""
) : Parcelable
