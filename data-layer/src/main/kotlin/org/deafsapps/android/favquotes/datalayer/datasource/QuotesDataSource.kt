package org.deafsapps.android.favquotes.datalayer.datasource

import arrow.core.Either
import arrow.core.right
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import org.deafsapps.android.favquotes.datalayer.domain.toBo
import org.deafsapps.android.favquotes.datalayer.domain.toListWrapperBo
import org.deafsapps.android.favquotes.datalayer.domain.toQuoteBo
import org.deafsapps.android.favquotes.datalayer.service.FavQsApiService
import org.deafsapps.android.favquotes.datalayer.utils.retrofitSafeCall
import org.deafsapps.android.favquotes.domainlayer.domain.FailureBo
import org.deafsapps.android.favquotes.domainlayer.domain.QuoteBo
import retrofit2.Retrofit
import javax.inject.Inject

private const val USER_TOKEN = "Token token=f76bc4b192ea1366f4125e32d6a0c951"

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
    suspend fun queryQuoteList(): Either<FailureBo, Boolean>

    /**
     *
     */
    suspend fun fetchQuoteById(id: Int): Either<FailureBo, QuoteBo>

    /**
     *
     */
    suspend fun fetchQuoteList(): Flow<Either<FailureBo, List<QuoteBo>>>
}

/**
 *
 */
@ExperimentalCoroutinesApi
class FavQsDataSource @Inject constructor(private val retrofit: Retrofit) : QuotesDataSource {

    private val quoteList: ConflatedBroadcastChannel<Either<FailureBo, List<QuoteBo>>> by lazy { ConflatedBroadcastChannel() }

    override suspend fun fetchRandomQuote(): Either<FailureBo, QuoteBo> =
        retrofit.create(FavQsApiService::class.java).fetchQuoteOfTheDay()
            .retrofitSafeCall(transform = { it.toQuoteBo() })

    override suspend fun queryQuoteList(): Either<FailureBo, Boolean> =
        retrofit.create(FavQsApiService::class.java)
            .fetchPublicQuotes(USER_TOKEN)
            .retrofitSafeCall(transform = { it.toListWrapperBo() })
            .also { it.orNull()?.let { quoteList.offer(it.quoteList.right()) } }
            .map { it.quoteList.isNotEmpty() }

    override suspend fun fetchQuoteById(id: Int): Either<FailureBo, QuoteBo> =
        retrofit.create(FavQsApiService::class.java)
            .fetchPublicQuoteById(
                token = USER_TOKEN,
                id = id
            ).retrofitSafeCall(transform = { it.toBo() })

    @FlowPreview
    override suspend fun fetchQuoteList(): Flow<Either<FailureBo, List<QuoteBo>>> = quoteList.asFlow()

}
