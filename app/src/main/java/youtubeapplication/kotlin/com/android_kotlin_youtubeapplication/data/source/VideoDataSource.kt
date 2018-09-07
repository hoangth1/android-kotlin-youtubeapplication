package youtubeapplication.kotlin.com.android_kotlin_youtubeapplication.data.source

import io.reactivex.Single
import youtubeapplication.kotlin.com.android_kotlin_youtubeapplication.data.model.Video
import youtubeapplication.kotlin.com.android_kotlin_youtubeapplication.data.source.response.VideoResponse

interface VideoDataSource {
    interface Local {

    }

    interface Remote {
        fun getListPopularVideo(key: String, chart: String,
                                part: String, maxResult: Int,
                                regironCode: String): Single<VideoResponse>
    }
}
