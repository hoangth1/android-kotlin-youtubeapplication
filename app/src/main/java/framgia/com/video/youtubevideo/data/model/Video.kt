package framgia.com.video.youtubevideo.data.model

import android.os.Parcelable
import com.google.gson.*
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.lang.reflect.Type

@Parcelize
data class Video(
        var mId: String = "",
        @SerializedName("snippet")
        var mSnipper: Snippet = Snippet(),
        @SerializedName("statistics")
        var statistics: Statistics
) : Parcelable {
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
