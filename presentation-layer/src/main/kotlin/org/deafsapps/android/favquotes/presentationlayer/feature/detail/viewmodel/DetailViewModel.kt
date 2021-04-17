package org.deafsapps.android.favquotes.presentationlayer.feature.detail.viewmodel

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.deafsapps.android.favquotes.domainlayer.domain.FailureBo
import org.deafsapps.android.favquotes.domainlayer.domain.QuoteBo
import org.deafsapps.android.favquotes.domainlayer.org.deafsapps.android.favquotes.domainlayer.feature.detail.DETAIL_DOMAIN_BRIDGE_TAG
import org.deafsapps.android.favquotes.domainlayer.org.deafsapps.android.favquotes.domainlayer.feature.detail.DetailDomainLayerBridge
import org.deafsapps.android.favquotes.presentationlayer.base.BaseMvvmViewModel
import org.deafsapps.android.favquotes.presentationlayer.base.ScreenState
import org.deafsapps.android.favquotes.presentationlayer.domain.boToVoFailure
import org.deafsapps.android.favquotes.presentationlayer.domain.toVo
import org.deafsapps.android.favquotes.presentationlayer.feature.detail.view.state.DetailState
import javax.inject.Inject
import javax.inject.Named

const val DETAIL_VIEW_MODEL_TAG = "detailViewModel"

/**
 *
 */
@ExperimentalCoroutinesApi
class DetailViewModel @Inject constructor(
    @Named(DETAIL_DOMAIN_BRIDGE_TAG)
    bridge: @JvmSuppressWildcards DetailDomainLayerBridge<QuoteBo>
) : BaseMvvmViewModel<DetailDomainLayerBridge<QuoteBo>, DetailState>(bridge = bridge) {

    /**
     *
     */
    fun onViewCreated(data: Int) {
        _screenState.value = ScreenState.Loading
        bridge.fetchQuoteById(
            scope = viewModelScope,
            params = data,
            onResult = {
                it.fold(::handleError, ::handleSuccess)
            }
        )
    }

    private fun handleSuccess(data: QuoteBo) {
        _screenState.value = ScreenState.Render(DetailState.LoadQuoteItem(data = data.toVo()))
    }

    private fun handleError(failure: FailureBo) {
        _errorState.value = failure.boToVoFailure()
    }

}
