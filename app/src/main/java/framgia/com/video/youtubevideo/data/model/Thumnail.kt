package framgia.com.video.youtubevideo.data.model

import android.arch.persistence.room.Embedded
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Thumnail(
        @Embedded
        @SerializedName("high")
        var mHight: High = High()
) : Parcelable
