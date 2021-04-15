package org.deafsapps.android.favquotes.presentationlayer.feature.splash.view.state

import org.deafsapps.android.favquotes.presentationlayer.base.BaseState
import org.deafsapps.android.favquotes.presentationlayer.domain.QuoteVo

/**
 *
 */
sealed class SplashState : BaseState() {
    class LoadQuote(val quote: QuoteVo) : SplashState()
    object NavigateToMainView : SplashState()
}
