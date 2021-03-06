package org.deafsapps.android.favquotes.domainlayer.org.deafsapps.android.favquotes.domainlayer.usecase

import arrow.core.Either
import org.deafsapps.android.favquotes.domainlayer.DomainlayerContract
import org.deafsapps.android.favquotes.domainlayer.DomainlayerContract.Datalayer.Companion.QUOTE_DATA_REPOSITORY_TAG
import org.deafsapps.android.favquotes.domainlayer.domain.FailureBo
import org.deafsapps.android.favquotes.domainlayer.domain.QuoteBo
import javax.inject.Inject
import javax.inject.Named

const val QUERY_QUOTE_LIST_UC_TAG = "queryQuoteListUc"

/**
 *
 */
class QueryQuoteListUc @Inject constructor(
    @Named(QUOTE_DATA_REPOSITORY_TAG)
    private val dataRepository: @JvmSuppressWildcards DomainlayerContract.Datalayer.QuoteDataRepository<QuoteBo>
) : DomainlayerContract.Presentationlayer.UseCase<Any, Boolean> {

    override suspend fun run(params: Any?): Either<FailureBo, Boolean> =
        dataRepository.queryQuoteList()

}
