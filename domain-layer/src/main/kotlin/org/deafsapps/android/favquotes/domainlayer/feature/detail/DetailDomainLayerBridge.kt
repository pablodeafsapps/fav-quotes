package org.deafsapps.android.favquotes.domainlayer.org.deafsapps.android.favquotes.domainlayer.feature.detail

import arrow.core.Either
import kotlinx.coroutines.CoroutineScope
import org.deafsapps.android.favquotes.domainlayer.DomainlayerContract
import org.deafsapps.android.favquotes.domainlayer.base.BaseDomainLayerBridge
import org.deafsapps.android.favquotes.domainlayer.domain.FailureBo
import org.deafsapps.android.favquotes.domainlayer.domain.QuoteBo
import org.deafsapps.android.favquotes.domainlayer.org.deafsapps.android.favquotes.domainlayer.usecase.FETCH_QUOTE_BY_ID_UC_TAG
import javax.inject.Inject
import javax.inject.Named

const val DETAIL_DOMAIN_BRIDGE_TAG = "detailDomainBridge"

/**
 *
 */
interface DetailDomainLayerBridge<out S> : BaseDomainLayerBridge {

    /**
     *
     */
    fun fetchQuoteById(scope: CoroutineScope, params: Int, onResult: (Either<FailureBo, S>) -> Unit = {})

}

/**
 *
 */
class DetailDomainLayerBridgeImpl @Inject constructor(
    @Named(FETCH_QUOTE_BY_ID_UC_TAG)
    private val fetchQuoteByIdUc: @JvmSuppressWildcards DomainlayerContract.Presentationlayer.UseCase<Int, QuoteBo>
) : DetailDomainLayerBridge<QuoteBo> {

    /**
     *
     */
    override fun fetchQuoteById(
        scope: CoroutineScope,
        params: Int,
        onResult: (Either<FailureBo, QuoteBo>) -> Unit
    ) {
        fetchQuoteByIdUc.invoke(scope = scope, params = params, onResult = onResult)
    }

}
