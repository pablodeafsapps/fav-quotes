package org.deafsapps.android.favquotes.datalayer.datasource

import arrow.core.Either
import org.deafsapps.android.favquotes.datalayer.domain.toBoList
import org.deafsapps.android.favquotes.datalayer.domain.toQuoteBo
import org.deafsapps.android.favquotes.datalayer.service.FavQsApiService
import org.deafsapps.android.favquotes.datalayer.utils.retrofitSafeCall
import org.deafsapps.android.favquotes.domainlayer.domain.FailureBo
import org.deafsapps.android.favquotes.domainlayer.domain.QuoteBo
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
    suspend fun fetchQuoteList(): Either<FailureBo, List<QuoteBo>>

}

/**
 *
 */
class FavQsDataSource @Inject constructor(private val retrofit: Retrofit) : QuotesDataSource {

    private var quoteList: Either<FailureBo, List<QuoteBo>>? = null

    override suspend fun fetchRandomQuote(): Either<FailureBo, QuoteBo> =
        retrofit.create(FavQsApiService::class.java).fetchQuoteOfTheDay()
            .retrofitSafeCall(transform = { it.toQuoteBo() })

    override suspend fun fetchQuoteList(): Either<FailureBo, List<QuoteBo>> =
        retrofit.create(FavQsApiService::class.java).fetchQuoteList()
            .retrofitSafeCall(transform = { it.toBoList() }).also { data -> quoteList = data }

}
