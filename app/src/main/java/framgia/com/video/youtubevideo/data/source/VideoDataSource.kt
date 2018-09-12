package framgia.com.video.youtubevideo.data.source

import io.reactivex.Single
import framgia.com.video.youtubevideo.data.model.Video
import framgia.com.video.youtubevideo.data.source.response.VideoResponse

interface VideoDataSource {
    interface Local {

    }

    interface Remote {
        fun getListPopularVideo(query: HashMap<String, String>): Single<VideoResponse>
        fun getListRelatedVideo(query: HashMap<String, String>): Single<VideoResponse>
    }
}
