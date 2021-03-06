package framgia.com.video.youtubevideo.data.source.network

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import framgia.com.video.youtubevideo.data.source.response.VideoResponse
import retrofit2.http.QueryMap

interface Api {

    @GET("videos")
    fun getPopularVideo(@QueryMap query: HashMap<String, String>): Single<VideoResponse>

    @GET("search")
    fun searchVideo(@QueryMap query: HashMap<String, String>): Single<VideoResponse>

    companion object {
        const val BASE_URL = "https://www.googleapis.com/youtube/v3/"
        const val KEY = "AIzaSyBiFmKOzifMjsphlfEIdTpBoVMzQJG5Uu8"
        const val PARAM_KEY = "key"
        const val PARAM_CHART = "chart"
        const val CHART_MOST_POPULAR = "mostPopular"
        const val PARAM_MAX_RESULT = "maxResults"
        const val MAX_RESULT = 10
        const val REGION_CODE_V = "VN"
        const val PARAM_PART = "part"
        const val PART_SNIPPET = "snippet"
        const val PART_STATISTICS = "statistics"
        const val PARAM_REGION_CODE = "regionCode"
        const val PARAM_TYPE = "type"
        const val TYPE_VIDEO = "video"
        const val PARAM_RELATED_VIDEO_ID = "relatedToVideoId"
        const val PARAM_QUERY = "q"
        const val PARAM_PAGE_TOKEN = "pageToken"
    }
}
