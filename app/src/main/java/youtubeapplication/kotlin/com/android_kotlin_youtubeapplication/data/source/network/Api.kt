package youtubeapplication.kotlin.com.android_kotlin_youtubeapplication.data.source.network

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import youtubeapplication.kotlin.com.android_kotlin_youtubeapplication.data.source.response.VideoResponse

interface Api {
    @Headers("User-key: key=AIzaSyBiFmKOzifMjsphlfEIdTpBoVMzQJG5Uu8")
    @GET("videos")
    fun getPopularVideo(@Query(PARAM_CHART) char: String,
                        @Query(PARAM_REGION_CODE) regionCode: String,
                        @Query(PARAM_MAX_RESULT) maxResult: Int,
                        @Query(PARAM_PART) part: String): Single<VideoResponse>

    companion object {
        const val BASE_URL = "https://www.googleapis.com/youtube/v3/"
        const val KEY = "AIzaSyBiFmKOzifMjsphlfEIdTpBoVMzQJG5Uu8"
        const val PARAM_KEY = "key"
        const val PARAM_CHART = "chart"
        const val CHART_MOST_POPULAR = "mostPopular"
        const val PARAM_MAX_RESULT = "maxResults"
        const val MAX_RESULT = 20
        const val REGION_CODE_V = "VN"
        const val PARAM_PART = "part"
        const val PART_SNIPPET = "snippet"
        const val PARAM_REGION_CODE = "regionCode"
    }
}
