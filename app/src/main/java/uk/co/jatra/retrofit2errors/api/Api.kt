package uk.co.jatra.retrofit2errors.api

import io.reactivex.Single
import retrofit2.http.GET
import uk.co.jatra.retrofit2errors.data.Name
import java.util.*

interface Api {
    @GET("api")
    fun getNames(): Single<Name>

    @GET("api")
    fun wrongType(): Single<BadFish>

    @GET("api")
    fun defineReturnToBeABaseTypeWhenItIsAnObject(): Single<String>

    @GET("api")
    fun defineReturnToBeAListWhenItIsnt(): Single<List<String>>

    @GET("madeuppath")
    fun notFound(): Single<Name>

    @GET("api?maxlen=-1&region=neverneverland&amount=-1")
    fun badRequest(): Single<Name>
}

data class BadFish (
    val date: Date
)