package framgia.com.video.youtubevideo.data.source.remote

import io.reactivex.Single
import framgia.com.video.youtubevideo.data.source.VideoDataSource
import framgia.com.video.youtubevideo.data.source.network.Api
import framgia.com.video.youtubevideo.data.source.response.VideoResponse

class VideoRemoteDataSource(var api: Api) : VideoDataSource.Remote {
    override fun getListPopularVideo(                                     chart: String, part: String,
                                     maxResult: Int, regironCode:
                                     String): Single<VideoResponse> =
            api.getPopularVideo(chart, regironCode, maxResult, part)
}
