package framgia.com.video.youtubevideo.data.model

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import android.os.Parcelable
import com.google.gson.*
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.lang.reflect.Type

@Parcelize
@Entity(tableName = "video")
data class Video(
        @PrimaryKey(autoGenerate = false)
        var mId: String = "",
        @Embedded
        @SerializedName("snippet")
        var mSnipper: Snippet = Snippet(),
        @Embedded
        @SerializedName("statistics")
        var statistics: Statistics
) : Parcelable {
    @Ignore
    @SerializedName("id")
    var videoId: Any? = null

    object DataStateDeserializer : JsonDeserializer<Video> {
        override fun deserialize(json: JsonElement?,
                                 typeOfT: Type?,
                                 context: JsonDeserializationContext?): Video {
            val video: Video = Gson().fromJson(json, Video::class.java)
            val jsonObject: JsonObject? = json?.asJsonObject;
            if (jsonObject != null && jsonObject.has("id")) {
                val element = jsonObject.get("id").let {
                    when {
                        it.isJsonPrimitive -> video.mId = it.asString
                        else -> video.mId = it.asJsonObject.get("videoId").asString
                    }
                }
            }
            return video
        }
    }
}
