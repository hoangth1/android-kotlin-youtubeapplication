package framgia.com.video.youtubevideo.data.source.remote

import framgia.com.video.youtubevideo.data.source.VideoDataSource
import framgia.com.video.youtubevideo.data.source.network.Api
import framgia.com.video.youtubevideo.data.source.response.VideoResponse
import io.reactivex.Single

class VideoRemoteDataSource(var api: Api) : VideoDataSource.Remote {
    override fun getListPopularVideo(query: HashMap<String, String>): Single<VideoResponse> =
            api.getPopularVideo(query)

    override fun searchVideo(query: HashMap<String, String>): Single<VideoResponse> =
            api.searchVideo(query)
}
