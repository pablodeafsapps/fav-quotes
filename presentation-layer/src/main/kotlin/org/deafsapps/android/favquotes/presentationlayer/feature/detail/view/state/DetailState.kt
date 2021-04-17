package org.deafsapps.android.favquotes.presentationlayer.feature.detail.view.state

import org.deafsapps.android.favquotes.presentationlayer.base.BaseState
import org.deafsapps.android.favquotes.presentationlayer.domain.QuoteVo

/**
 *
 */
sealed class DetailState : BaseState() {
    class LoadQuoteItem(val data: QuoteVo) : DetailState()
}
