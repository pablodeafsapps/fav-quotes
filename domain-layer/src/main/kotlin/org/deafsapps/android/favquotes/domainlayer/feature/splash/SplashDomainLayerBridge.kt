package org.deafsapps.android.favquotes.domainlayer.feature.splash

import arrow.core.Either
import kotlinx.coroutines.CoroutineScope
import org.deafsapps.android.favquotes.domainlayer.DomainlayerContract
import org.deafsapps.android.favquotes.domainlayer.base.BaseDomainLayerBridge
import org.deafsapps.android.favquotes.domainlayer.domain.FailureBo
import org.deafsapps.android.favquotes.domainlayer.domain.QuoteBo
import org.deafsapps.android.favquotes.domainlayer.usecase.FETCH_RANDOM_QUOTE_UC_TAG
import javax.inject.Inject
import javax.inject.Named

const val SPLASH_DOMAIN_BRIDGE_TAG = "splashDomainBridge"

/**
 *
 */
interface SplashDomainLayerBridge<out S> : BaseDomainLayerBridge {

    /**
     *
     */
    fun fetchRandomQuote(scope: CoroutineScope, onResult: (Either<FailureBo, S>) -> Unit = {})

}

/**
 *
 */
class SplashDomainLayerBridgeImpl @Inject constructor(
    @Named(FETCH_RANDOM_QUOTE_UC_TAG)
    private val fetchRandomQuoteUc: @JvmSuppressWildcards DomainlayerContract.Presentationlayer.UseCase<Any, QuoteBo>
) : SplashDomainLayerBridge<QuoteBo> {

    /**
     *
     */
    override fun fetchRandomQuote(
        scope: CoroutineScope,
        onResult: (Either<FailureBo, QuoteBo>) -> Unit
    ) {
        fetchRandomQuoteUc.invoke(scope = scope, onResult = onResult)
    }

}
