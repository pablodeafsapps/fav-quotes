package org.deafsapps.android.favquotes.datalayer.repository

import arrow.core.Either
import arrow.core.left
import org.deafsapps.android.favquotes.datalayer.datasource.ConnectivityDataSource
import org.deafsapps.android.favquotes.datalayer.datasource.QuotesDataSource
import org.deafsapps.android.favquotes.domainlayer.DomainlayerContract
import org.deafsapps.android.favquotes.domainlayer.domain.FailureBo
import org.deafsapps.android.favquotes.domainlayer.domain.QuoteBo

object Repository : DomainlayerContract.Datalayer.QuoteDataRepository<QuoteBo> {

    lateinit var connectivityDataSource: ConnectivityDataSource
    lateinit var quotesDataSource: QuotesDataSource

    override suspend fun fetchRandomQuote(): Either<FailureBo, QuoteBo> =
        try {
            connectivityDataSource.checkNetworkConnectionAvailability().takeIf { it }?.let {
                quotesDataSource.fetchRandomQuote()
            } ?: run {
                FailureBo.NoConnection.left()
            }
        } catch (e: Exception) {
            FailureBo.Unknown.left()
        }

    override suspend fun fetchQuoteList(): Either<FailureBo, List<QuoteBo>> =
        try {
            connectivityDataSource.checkNetworkConnectionAvailability().takeIf { it }?.let {
                quotesDataSource.fetchQuoteList().map { it.quoteList }
            } ?: run {
                FailureBo.NoConnection.left()
            }
        } catch (e: Exception) {
            FailureBo.Unknown.left()
        }

    override suspend fun fetchQuoteById(id: Int): Either<FailureBo, QuoteBo> =
        try {
            connectivityDataSource.checkNetworkConnectionAvailability().takeIf { it }?.let {
                quotesDataSource.fetchQuoteById(id = id)
            } ?: run {
                FailureBo.NoConnection.left()
            }
        } catch (e: Exception) {
            FailureBo.Unknown.left()
        }

    override suspend fun fetchQuoteListByPage(page: Int): Either<FailureBo, List<QuoteBo>> {
        TODO("Not yet implemented")
    }

    override suspend fun checkQuoteAsFav(quoteId: Int): Either<FailureBo, QuoteBo> {
        TODO("Not yet implemented")
    }

    override suspend fun uncheckQuoteAsFav(quoteId: Int): Either<FailureBo, QuoteBo> {
        TODO("Not yet implemented")
    }

    override suspend fun fetchFavouriteQuotes(): Either<FailureBo, QuoteBo> {
        TODO("Not yet implemented")
    }

}
