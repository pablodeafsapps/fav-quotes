package org.deafsapps.android.favquotes.domainlayer.org.deafsapps.android.favquotes.domainlayer.usecase

import arrow.core.Either
import kotlinx.coroutines.flow.Flow
import org.deafsapps.android.favquotes.domainlayer.DomainlayerContract
import org.deafsapps.android.favquotes.domainlayer.DomainlayerContract.Datalayer.Companion.QUOTE_DATA_REPOSITORY_TAG
import org.deafsapps.android.favquotes.domainlayer.domain.FailureBo
import org.deafsapps.android.favquotes.domainlayer.domain.QuoteBo
import javax.inject.Inject
import javax.inject.Named

const val FETCH_QUOTE_LIST_UC_TAG = "fetchQuoteListUc"

/**
 *
 */
class FetchQuoteListUc @Inject constructor(
    @Named(QUOTE_DATA_REPOSITORY_TAG)
    private val dataRepository: @JvmSuppressWildcards DomainlayerContract.Datalayer.QuoteDataRepository<QuoteBo>
) : DomainlayerContract.Presentationlayer.FlowUseCase<Any, List<QuoteBo>> {

    override suspend fun run(params: Any?): Flow<Either<FailureBo, List<QuoteBo>>> =
        dataRepository.fetchQuoteList()

}
