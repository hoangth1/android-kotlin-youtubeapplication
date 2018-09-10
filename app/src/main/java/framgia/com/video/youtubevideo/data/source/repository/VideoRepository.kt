package framgia.com.video.youtubevideo.data.source.repository

import io.reactivex.Single
import framgia.com.video.youtubevideo.data.source.VideoDataSource
import framgia.com.video.youtubevideo.data.source.response.VideoResponse
import kotlin.math.max

class VideoRepository(val remote: VideoDataSource.Remote, val local: VideoDataSource.Local)
    : VideoDataSource.Remote, VideoDataSource.Local {
    override fun getListPopularVideo(chart: String
                                     , part: String, maxResult: Int,
                                     regironCode: String): Single<VideoResponse> =
            remote.getListPopularVideo(chart, part, maxResult, regironCode)
}