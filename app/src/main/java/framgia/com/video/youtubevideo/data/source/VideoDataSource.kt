package framgia.com.video.youtubevideo.data.source

import io.reactivex.Single
import framgia.com.video.youtubevideo.data.model.Video
import framgia.com.video.youtubevideo.data.source.response.VideoResponse

interface VideoDataSource {
    interface Local {
        fun getVideos(): Single<List<Video>>
        fun getVideo(id: String): Single<Video>
        fun insertVideo(video: Video): Long
        fun deleteVideo(video: Video)
    }

    interface Remote {
        fun getListPopularVideo(query: HashMap<String, String>): Single<VideoResponse>
        fun searchVideo(query: HashMap<String, String>): Single<VideoResponse>
    }
}
