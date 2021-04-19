package org.deafsapps.android.favquotes.presentationlayer.feature.main.view.viewholder

import org.deafsapps.android.favquotes.presentationlayer.base.BaseViewHolder
import org.deafsapps.android.favquotes.presentationlayer.databinding.QuoteItemBinding
import org.deafsapps.android.favquotes.presentationlayer.domain.QuoteVo

/**
 * Holds the widgets composing the view which depicts character data
 *
 * It extends parametrized [BaseViewHolder] which constraints the data types handled.
 */
class QuoteItemViewHolder(val itemBinding: QuoteItemBinding) :
    BaseViewHolder<QuoteVo, QuoteVo>(itemBinding.root) {

    override fun onBind(item: QuoteVo, callback: (QuoteVo) -> Unit) {
        (item as? QuoteVo)?.let { quote ->
            with(itemBinding) {
                tvAuthor.text = quote.author
                tvQuote.text = quote.body
                tvFavs.text = quote.favoritesCount.toString()
                tvUpvotes.text = quote.upvotesCount.toString()
                tvDownvotes.text = quote.downvotesCount.toString()
                root.setOnClickListener { callback(item) }
            }
        }
    }

}
