package youtubeapplication.kotlin.com.android_kotlin_youtubeapplication.data.source.response

import com.google.gson.annotations.SerializedName
import youtubeapplication.kotlin.com.android_kotlin_youtubeapplication.data.model.Video

class VideoResponse(
        @SerializedName("items")
        var mListVideo: List<Video>
)
