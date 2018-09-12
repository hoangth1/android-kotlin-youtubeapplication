package framgia.com.video.youtubevideo.data.source.repository

import io.reactivex.Single
import framgia.com.video.youtubevideo.data.source.VideoDataSource
import framgia.com.video.youtubevideo.data.source.response.VideoResponse
import kotlin.math.max

class VideoRepository(val remote: VideoDataSource.Remote, val local: VideoDataSource.Local)
    : VideoDataSource.Remote, VideoDataSource.Local {
    override fun getListRelatedVideo(query: HashMap<String, String>): Single<VideoResponse> =
            remote.getListRelatedVideo(query)

    override fun getListPopularVideo(query: HashMap<String, String>): Single<VideoResponse> =
            remote.getListPopularVideo(query)
}
