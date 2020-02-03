package uk.co.jatra.retrofit2errors.api

import com.google.gson.GsonBuilder
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


const val BASE_URL = "https://uinames.com/"
const val BAD_URL = "http://ldjksfkjgslfkdgjldfskjglsdfkjglsdfkgj.com/"

class ApiAdapter {

    companion object {
        val adapter: Api by lazy {
            val retrofit = Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(rxAdapter)
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                .build()
            retrofit.create(Api::class.java)
        }

        private val okHttpClient: OkHttpClient by lazy {
            val builder = OkHttpClient.Builder()
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            builder.addInterceptor(httpLoggingInterceptor)
            builder.build()
        }

        //Do we need to specify the scheduler?
        private val rxAdapter: RxJava2CallAdapterFactory by lazy {
            RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io())
            RxJava2CallAdapterFactory.create()
        }
    }
}