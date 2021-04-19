package org.deafsapps.android.favquotes.domainlayer.org.deafsapps.android.favquotes.domainlayer.usecase

import arrow.core.Either
import arrow.core.left
import org.deafsapps.android.favquotes.domainlayer.DomainlayerContract
import org.deafsapps.android.favquotes.domainlayer.DomainlayerContract.Datalayer.Companion.QUOTE_DATA_REPOSITORY_TAG
import org.deafsapps.android.favquotes.domainlayer.domain.ErrorMessage
import org.deafsapps.android.favquotes.domainlayer.domain.FailureBo
import org.deafsapps.android.favquotes.domainlayer.domain.QuoteBo
import javax.inject.Inject
import javax.inject.Named

const val FETCH_QUOTE_BY_ID_UC_TAG = "fetchQuoteByIdUc"

/**
 *
 */
class FetchQuoteByIdUc @Inject constructor(
    @Named(QUOTE_DATA_REPOSITORY_TAG)
    private val dataRepository: @JvmSuppressWildcards DomainlayerContract.Datalayer.QuoteDataRepository<QuoteBo>
) : DomainlayerContract.Presentationlayer.UseCase<Int, QuoteBo> {

    override suspend fun run(params: Int?): Either<FailureBo, QuoteBo> =
        params?.let { p ->
            dataRepository.fetchQuoteById(id = p)
        } ?: run {
            FailureBo.InputParamsError(msg = ErrorMessage.ERROR_QUOTE_ID_NOT_VALID).left()
        }

}
