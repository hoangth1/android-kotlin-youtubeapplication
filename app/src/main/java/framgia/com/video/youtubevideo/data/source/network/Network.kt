package framgia.com.video.youtubevideo.data.source.network

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
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
        return Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(httpClient.build()).build()
    }

    fun getApi(): Api = getRetrofit().create(Api::class.java)
}