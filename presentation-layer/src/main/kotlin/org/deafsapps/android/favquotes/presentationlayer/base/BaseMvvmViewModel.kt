package org.deafsapps.android.favquotes.presentationlayer.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.deafsapps.android.favquotes.domainlayer.base.BaseDomainLayerBridge
import org.deafsapps.android.favquotes.presentationlayer.domain.FailureVo

/**
 * This parametrized abstract class is intended to be extended by any app presentation-layer view-model which aims to be
 * integrated within the MVVM architecture pattern.
 *
 * @param S represents the state of the view, and must extend from [BaseState]
 * @property screenState the [StateFlow] which will be updated to notify the view
 *
 * @since 1.0
 */
@ExperimentalCoroutinesApi
abstract class BaseMvvmViewModel<T : BaseDomainLayerBridge, S : BaseState>(
    protected val bridge: T
) : ViewModel() {

    protected val _screenState: MutableStateFlow<ScreenState<S>> by lazy {
        MutableStateFlow(
            ScreenState.Idle
        )
    }
    val screenState: StateFlow<ScreenState<S>>
        get() = _screenState

    protected val _errorState: MutableStateFlow<FailureVo> by lazy { MutableStateFlow(FailureVo.Idle) }
    val errorState: StateFlow<FailureVo>
        get() = _errorState

}
