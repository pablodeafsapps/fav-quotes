package org.deafsapps.android.favquotes.presentationlayer.feature.main.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.deafsapps.android.favquotes.presentationlayer.base.BaseViewHolder
import org.deafsapps.android.favquotes.presentationlayer.databinding.QuoteItemBinding
import org.deafsapps.android.favquotes.presentationlayer.domain.QuoteVo
import org.deafsapps.android.favquotes.presentationlayer.feature.main.view.viewholder.QuoteItemViewHolder

/**
 * A [RecyclerView.Adapter] which is in charge of handling joke data to properly render them.
 *
 * @property [itemList] A list of [QuoteVo] type which represents each joke
 * @property [onItemSelected] A callback which allows to perform an action over a [QuoteVo]
 */
class QuoteListAdapter(
    private var itemList: List<QuoteVo>,
    private val onItemSelected: (QuoteVo) -> Unit
) : RecyclerView.Adapter<BaseViewHolder<QuoteVo, QuoteVo>>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<QuoteVo, QuoteVo> {
        val itemBinding = QuoteItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return QuoteItemViewHolder(itemBinding = itemBinding)
    }

    override fun getItemCount(): Int = itemList.size

    override fun onBindViewHolder(holder: BaseViewHolder<QuoteVo, QuoteVo>, position: Int) {
        holder.onBind(
            item = itemList[position],
            callback = { item -> onItemSelected.invoke(item) }
        )
    }

    /**
     * Updates the data displayed by the adapter
     *
     * @param [newData] A list with the new data to update
     */
    fun updateData(newData: List<QuoteVo>) {
        itemList = newData
        notifyDataSetChanged()
    }

}
