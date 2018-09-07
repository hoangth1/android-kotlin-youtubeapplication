package youtubeapplication.kotlin.com.android_kotlin_youtubeapplication.data.source.remote

import io.reactivex.Single
import youtubeapplication.kotlin.com.android_kotlin_youtubeapplication.data.source.VideoDataSource
import youtubeapplication.kotlin.com.android_kotlin_youtubeapplication.data.source.network.Api
import youtubeapplication.kotlin.com.android_kotlin_youtubeapplication.data.source.response.VideoResponse

class VideoRemoteDataSource(var api: Api) : VideoDataSource.Remote {
    override fun getListPopularVideo(key: String,
                                     chart: String, part: String,
                                     maxResult: Int, regironCode:
                                     String): Single<VideoResponse> =
            api.getPopularVideo(key, chart, regironCode, maxResult, part)
}
