package org.deafsapps.android.favquotes.datalayer.datasource

import arrow.core.Either
import org.deafsapps.android.favquotes.datalayer.domain.toListWrapperBo
import org.deafsapps.android.favquotes.datalayer.domain.toQuoteBo
import org.deafsapps.android.favquotes.datalayer.service.FavQsApiService
import org.deafsapps.android.favquotes.datalayer.utils.retrofitSafeCall
import org.deafsapps.android.favquotes.domainlayer.domain.FailureBo
import org.deafsapps.android.favquotes.domainlayer.domain.QuoteBo
import org.deafsapps.android.favquotes.domainlayer.domain.QuoteListWrapperBo
import retrofit2.Retrofit
import javax.inject.Inject

/**
 *
 */
interface QuotesDataSource {

    companion object {
        const val QUOTES_DATA_SOURCE_TAG = "quotesDataSource"
        const val API_BASE_URL = "https://favqs.com"
    }

    /**
     *
     */
    suspend fun fetchRandomQuote(): Either<FailureBo, QuoteBo>

    /**
     *
     */
    suspend fun fetchQuoteList(): Either<FailureBo, QuoteListWrapperBo>

}

/**
 *
 */
class FavQsDataSource @Inject constructor(private val retrofit: Retrofit) : QuotesDataSource {

    private var quoteList: Either<FailureBo, QuoteListWrapperBo>? = null

    override suspend fun fetchRandomQuote(): Either<FailureBo, QuoteBo> =
        retrofit.create(FavQsApiService::class.java).fetchQuoteOfTheDay()
            .retrofitSafeCall(transform = { it.toQuoteBo() })

    override suspend fun fetchQuoteList(): Either<FailureBo, QuoteListWrapperBo> =
        retrofit.create(FavQsApiService::class.java)
            .fetchPublicQuotes("Token token=f76bc4b192ea1366f4125e32d6a0c951")
            .retrofitSafeCall(transform = { it.toListWrapperBo() })
            .also { data -> quoteList = data }

}
