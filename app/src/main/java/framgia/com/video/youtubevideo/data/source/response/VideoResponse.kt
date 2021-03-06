package framgia.com.video.youtubevideo.data.source.response

import com.google.gson.annotations.SerializedName
import framgia.com.video.youtubevideo.data.model.Video

class VideoResponse(
        @SerializedName("nextPageToken")
        var nextPageToken: String = "",
        @SerializedName("items")
        var mListVideo: List<Video>
)
