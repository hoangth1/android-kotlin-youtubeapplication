package framgia.com.video.youtubevideo.data.source.network

import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import framgia.com.video.youtubevideo.data.model.Video
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object Network {


    private fun getRetrofit(): Retrofit {
        val httpClient: OkHttpClient.Builder = OkHttpClient.Builder()
        httpClient.addInterceptor { chain ->
            var request = chain.request()
            val url = request
                    .url()
                    .newBuilder().addQueryParameter(Api.PARAM_KEY, Api.KEY)
                    .build()
            request = request.newBuilder().url(url).build()
            chain.proceed(request)
        }
        val gson = GsonBuilder().registerTypeAdapter(Video::class.java, Video.DataStateDeserializer).create()
        return Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(httpClient.build()).build()
    }

    fun getApi(): Api = getRetrofit().create(Api::class.java)
}