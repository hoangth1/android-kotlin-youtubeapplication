package framgia.com.video.youtubevideo.data.source.repository

import framgia.com.video.youtubevideo.data.model.Video
import io.reactivex.Single
import framgia.com.video.youtubevideo.data.source.VideoDataSource
import framgia.com.video.youtubevideo.data.source.response.VideoResponse
import kotlin.math.max

class VideoRepository(val remote: VideoDataSource.Remote, val local: VideoDataSource.Local)
    : VideoDataSource.Remote, VideoDataSource.Local {
    override fun getVideos(): Single<List<Video>> = local.getVideos()

    override fun getVideo(id: String): Single<Video> = local.getVideo(id)

    override fun insertVideo(video: Video) = local.insertVideo(video)

    override fun deleteVideo(video: Video) = local.deleteVideo(video)

    override fun searchVideo(query: HashMap<String, String>): Single<VideoResponse> =
            remote.searchVideo(query)

    override fun getListPopularVideo(query: HashMap<String, String>): Single<VideoResponse> =
            remote.getListPopularVideo(query)
}
