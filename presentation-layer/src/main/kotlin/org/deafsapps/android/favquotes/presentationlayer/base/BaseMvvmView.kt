package org.deafsapps.android.favquotes.presentationlayer.base

import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.deafsapps.android.favquotes.domainlayer.base.BaseDomainLayerBridge
import org.deafsapps.android.githubbrowser.presentationlayer.base.BaseMvvmViewModel

/**
 * This parametrized interface is intended to be implemented by any app presentation-layer view which aims to be
 * integrated within the MVVM architecture pattern.
 *
 * @param T represents the ViewModel, and thus it must extend from @see{BaseMvvmViewModel}
 * @param S represents the bridge to the domain layer, and must extend from @see{BaseDomainLayerBridge}
 * @param U represents the state of the view, and must extend from @see{BaseState}
 * @param V represents the error which might be triggered by the ViewModel
 * @property viewModel a reference to the [BaseMvvmViewModel] associated to this view
 *
 * @since 1.0
 */
@ExperimentalCoroutinesApi
interface BaseMvvmView<T : BaseMvvmViewModel<S, U>, S : BaseDomainLayerBridge, U : BaseState, V> {

    val viewModel: T

    /**
     * Handles the possible state values
     *
     * @param renderState the actual state, extending from U
     */
    fun processRenderState(renderState: U)

    /**
     * Handles the possible error values
     *
     * @param error the error itself
     */
    fun processRenderError(error: V)

    /**
     * Init viewModel
     */
    fun initModel()

}
