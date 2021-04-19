package org.deafsapps.android.favquotes.domainlayer.org.deafsapps.android.favquotes.domainlayer.feature.main

import arrow.core.Either
import kotlinx.coroutines.CoroutineScope
import org.deafsapps.android.favquotes.domainlayer.DomainlayerContract
import org.deafsapps.android.favquotes.domainlayer.base.BaseDomainLayerBridge
import org.deafsapps.android.favquotes.domainlayer.domain.FailureBo
import org.deafsapps.android.favquotes.domainlayer.domain.QuoteBo
import org.deafsapps.android.favquotes.domainlayer.org.deafsapps.android.favquotes.domainlayer.usecase.FETCH_QUOTE_LIST_UC_TAG
import org.deafsapps.android.favquotes.domainlayer.org.deafsapps.android.favquotes.domainlayer.usecase.QUERY_QUOTE_LIST_UC_TAG
import javax.inject.Inject
import javax.inject.Named

const val MAIN_DOMAIN_BRIDGE_TAG = "mainDomainBridge"

/**
 *
 */
interface MainDomainLayerBridge<out S> : BaseDomainLayerBridge {

    /**
     *
     */
    fun queryQuoteList(scope: CoroutineScope, onResult: (Either<FailureBo, Boolean>) -> Unit = {})

    /**
     *
     */
    fun fetchQuoteList(scope: CoroutineScope, onResult: (Either<FailureBo, List<S>>) -> Unit = {})

}

/**
 *
 */
class MainDomainLayerBridgeImpl @Inject constructor(
    @Named(QUERY_QUOTE_LIST_UC_TAG)
    private val queryQuoteListUc: @JvmSuppressWildcards DomainlayerContract.Presentationlayer.UseCase<Any, Boolean>,
    @Named(FETCH_QUOTE_LIST_UC_TAG)
    private val fetchQuoteListUc: @JvmSuppressWildcards DomainlayerContract.Presentationlayer.FlowUseCase<Any, List<QuoteBo>>
) : MainDomainLayerBridge<QuoteBo> {

    /**
     *
     */
    override fun queryQuoteList(
        scope: CoroutineScope,
        onResult: (Either<FailureBo, Boolean>) -> Unit
    ) {
        queryQuoteListUc.invoke(scope = scope, onResult = onResult)
    }

    /**
     *
     */
    override fun fetchQuoteList(
        scope: CoroutineScope,
        onResult: (Either<FailureBo, List<QuoteBo>>) -> Unit
    ) {
        fetchQuoteListUc.invoke(scope = scope, onResult = onResult)
    }

}
