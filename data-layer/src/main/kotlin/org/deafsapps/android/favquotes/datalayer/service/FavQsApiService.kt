package org.deafsapps.android.favquotes.datalayer.service

import org.deafsapps.android.favquotes.datalayer.domain.QotdDto
import org.deafsapps.android.favquotes.datalayer.domain.QuoteDto
import retrofit2.Response
import retrofit2.http.GET

/**
 *
 */
interface FavQsApiService {

    /**
     *
     */
    @GET("/api/qotd")
    suspend fun fetchQuoteOfTheDay(): Response<QotdDto>

    /**
     *
     */
    @GET("/api/quotes")
    suspend fun fetchQuoteList(): Response<List<QuoteDto>>

}
