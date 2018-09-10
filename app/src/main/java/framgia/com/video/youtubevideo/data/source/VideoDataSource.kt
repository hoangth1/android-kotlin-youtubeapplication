package framgia.com.video.youtubevideo.data.source

import io.reactivex.Single
import framgia.com.video.youtubevideo.data.model.Video
import framgia.com.video.youtubevideo.data.source.response.VideoResponse

interface VideoDataSource {
    interface Local {

    }

    interface Remote {
        fun getListPopularVideo(chart: String,
                                part: String, maxResult: Int,
                                regironCode: String): Single<VideoResponse>
    }
}