package uk.co.jatra.retrofit2errors.api

import com.google.gson.GsonBuilder
import io.reactivex.schedulers.Schedulers.io
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


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
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build()
        }

        //Specifying the scheduler means we don't have to when making the actual calls.
        private val rxAdapter: RxJava2CallAdapterFactory by lazy {
            RxJava2CallAdapterFactory.createWithScheduler(io())
        }
    }
}