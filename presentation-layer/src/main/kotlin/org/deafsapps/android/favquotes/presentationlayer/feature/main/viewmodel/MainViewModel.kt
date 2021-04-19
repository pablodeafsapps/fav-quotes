package org.deafsapps.android.favquotes.presentationlayer.feature.main.viewmodel

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.deafsapps.android.favquotes.domainlayer.domain.FailureBo
import org.deafsapps.android.favquotes.domainlayer.domain.QuoteBo
import org.deafsapps.android.favquotes.domainlayer.org.deafsapps.android.favquotes.domainlayer.feature.main.MAIN_DOMAIN_BRIDGE_TAG
import org.deafsapps.android.favquotes.domainlayer.org.deafsapps.android.favquotes.domainlayer.feature.main.MainDomainLayerBridge
import org.deafsapps.android.favquotes.presentationlayer.base.BaseMvvmViewModel
import org.deafsapps.android.favquotes.presentationlayer.base.ScreenState
import org.deafsapps.android.favquotes.presentationlayer.domain.QuoteVo
import org.deafsapps.android.favquotes.presentationlayer.domain.boToVoFailure
import org.deafsapps.android.favquotes.presentationlayer.domain.toVoList
import org.deafsapps.android.favquotes.presentationlayer.feature.main.view.state.MainState
import javax.inject.Inject
import javax.inject.Named

const val MAIN_VIEW_MODEL_TAG = "mainViewModel"

/**
 *
 */
@ExperimentalCoroutinesApi
class MainViewModel @Inject constructor(
    @Named(MAIN_DOMAIN_BRIDGE_TAG)
    bridge: @JvmSuppressWildcards MainDomainLayerBridge<QuoteBo>
) : BaseMvvmViewModel<MainDomainLayerBridge<QuoteBo>, MainState>(bridge = bridge) {

    /**
     *
     */
    fun onViewCreated() {
        _screenState.value = ScreenState.Loading
        bridge.queryQuoteList(
            scope = viewModelScope,
            onResult = {
                it.fold(::handleError, ::handleQueryQuoteListSuccess)
            }
        )
    }

    /**
     *
     */
    fun onViewResumed() {
        bridge.fetchQuoteList(
            scope = viewModelScope,
            onResult = {
                it.fold(::handleError, ::handleFetchQuoteListSuccess)
            }
        )
    }

    /**
     *
     */
    fun onEndOfScrollReached() {
        _screenState.value = ScreenState.Loading
        bridge.queryQuoteList(
            scope = viewModelScope,
            onResult = {
                it.fold(::handleError, ::handleQueryQuoteListSuccess)
            }
        )
    }

    /**
     *
     */
    fun onQuoteItemSelected(item: QuoteVo) {
        _screenState.value = ScreenState.Render(MainState.NavigateToDetailView(id = item.id))
    }

    private fun handleFetchQuoteListSuccess(data: List<QuoteBo>) {
        _screenState.value = ScreenState.Render(MainState.LoadQuoteList(data = data.toVoList()))
    }

    private fun handleQueryQuoteListSuccess(hasQueried: Boolean) {
        val msg = if (hasQueried) {
            "Data queried!"
        } else {
            "Query performned, but no data returned"
        }
        _screenState.value = ScreenState.Render(MainState.LogInfo(msg = msg))
    }

    private fun handleError(failure: FailureBo) {
        _errorState.value = failure.boToVoFailure()
    }

}
