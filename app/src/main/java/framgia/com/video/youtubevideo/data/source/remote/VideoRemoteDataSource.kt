package framgia.com.video.youtubevideo.data.source.remote

import framgia.com.video.youtubevideo.data.source.VideoDataSource
import framgia.com.video.youtubevideo.data.source.network.Api
import framgia.com.video.youtubevideo.data.source.response.VideoResponse
import io.reactivex.Single

class VideoRemoteDataSource(var api: Api) : VideoDataSource.Remote {
    override fun getListPopularVideo(chart: String, part: String,
                                     maxResult: Int, regironCode:
                                     String): Single<VideoResponse> =
            api.getPopularVideo(chart, regironCode, maxResult, part)
}
