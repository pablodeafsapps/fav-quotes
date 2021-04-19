package org.deafsapps.android.favquotes.datalayer.service

import org.deafsapps.android.favquotes.datalayer.domain.QotdDto
import org.deafsapps.android.favquotes.datalayer.domain.QuoteDto
import org.deafsapps.android.favquotes.datalayer.domain.QuoteListWrapperDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

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
    suspend fun fetchPublicQuotes(@Header("Authorization") token: String): Response<QuoteListWrapperDto>

    /**
     *
     */
    @GET("/api/quotes/{id}")
    suspend fun fetchPublicQuoteById(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): Response<QuoteDto>

}
