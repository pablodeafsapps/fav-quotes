package org.deafsapps.android.favquotes.domainlayer.usecase

import arrow.core.Either
import org.deafsapps.android.favquotes.domainlayer.DomainlayerContract
import org.deafsapps.android.favquotes.domainlayer.DomainlayerContract.Datalayer.Companion.QUOTE_DATA_REPOSITORY_TAG
import org.deafsapps.android.favquotes.domainlayer.domain.FailureBo
import org.deafsapps.android.favquotes.domainlayer.domain.QuoteBo
import javax.inject.Inject
import javax.inject.Named

const val FETCH_RANDOM_QUOTE_UC_TAG = "fetchRandomQuoteUc"

/**
 *
 */
class FetchRandomQuoteUc @Inject constructor(
    @Named(QUOTE_DATA_REPOSITORY_TAG)
    private val dataRepository: @JvmSuppressWildcards DomainlayerContract.Datalayer.QuoteDataRepository<QuoteBo>
) : DomainlayerContract.Presentationlayer.UseCase<Any, QuoteBo> {

    override suspend fun run(params: Any?): Either<FailureBo, QuoteBo> =
        dataRepository.fetchRandomQuote()

}
