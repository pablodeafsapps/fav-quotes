package org.deafsapps.android.favquotes.presentationlayer.feature.main.view.state

import org.deafsapps.android.favquotes.presentationlayer.base.BaseState
import org.deafsapps.android.favquotes.presentationlayer.domain.QuoteVo

/**
 *
 */
sealed class MainState : BaseState() {
    class LoadQuoteList(val data: List<QuoteVo>) : MainState()
    class NavigateToDetailView(val id: Int) : MainState()
    class LogInfo(val msg: String) : MainState()
}
