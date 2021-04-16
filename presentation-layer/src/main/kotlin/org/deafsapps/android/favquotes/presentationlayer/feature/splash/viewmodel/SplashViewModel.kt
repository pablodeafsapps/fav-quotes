package org.deafsapps.android.favquotes.presentationlayer.feature.splash.viewmodel

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.deafsapps.android.favquotes.domainlayer.domain.FailureBo
import org.deafsapps.android.favquotes.domainlayer.domain.QuoteBo
import org.deafsapps.android.favquotes.domainlayer.feature.splash.SPLASH_DOMAIN_BRIDGE_TAG
import org.deafsapps.android.favquotes.domainlayer.feature.splash.SplashDomainLayerBridge
import org.deafsapps.android.favquotes.presentationlayer.base.BaseMvvmViewModel
import org.deafsapps.android.favquotes.presentationlayer.base.ScreenState
import org.deafsapps.android.favquotes.presentationlayer.domain.boToVoFailure
import org.deafsapps.android.favquotes.presentationlayer.domain.toVo
import org.deafsapps.android.favquotes.presentationlayer.feature.splash.view.state.SplashState
import javax.inject.Inject
import javax.inject.Named

const val SPLASH_VIEW_MODEL_TAG = "splashViewModel"

/**
 *
 */
@ExperimentalCoroutinesApi
class SplashViewModel @Inject constructor(
    @Named(SPLASH_DOMAIN_BRIDGE_TAG)
    bridge: @JvmSuppressWildcards SplashDomainLayerBridge<QuoteBo>
) : BaseMvvmViewModel<SplashDomainLayerBridge<QuoteBo>, SplashState>(bridge = bridge) {

    /**
     *
     */
    fun onViewResumed() {
        _screenState.value = ScreenState.Loading
        bridge.fetchRandomQuote(
            scope = viewModelScope,
            onResult = {
                it.fold(::handleError, ::handleSuccess)
            }
        )
    }

    /**
     *
     */
    fun onHintSelected() {
        _screenState.value = ScreenState.Render(SplashState.NavigateToMainView)
    }

    private fun handleSuccess(quote: QuoteBo) {
        _screenState.value = ScreenState.Render(SplashState.LoadQuote(quote = quote.toVo()))
    }

    private fun handleError(failure: FailureBo) {
        _errorState.value = failure.boToVoFailure()
        _screenState.value = ScreenState.Render(SplashState.NavigateToMainView)
    }

}
