package framgia.com.video.youtubevideo.data.source.local

import framgia.com.video.youtubevideo.data.model.Video
import framgia.com.video.youtubevideo.data.source.VideoDataSource
import framgia.com.video.youtubevideo.data.source.local.database.VideoDAO
import io.reactivex.Single

class VideoLocalDataSource(val videoDAO: VideoDAO) : VideoDataSource.Local {
    override fun getVideos(): Single<List<Video>> = videoDAO.getVideos()
    override fun getVideo(id: String) = videoDAO.getVideo(id)

    override fun insertVideo(video: Video) = videoDAO.insertVideo(video)

    override fun deleteVideo(video: Video) = videoDAO.deleteVideo(video)
}
