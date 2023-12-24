package org.akanework.riviere.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import org.akanework.riviere.R
import org.akanework.riviere.ui.data.HolderTypes

class HomePresetAdapter (
    private val cardData: MutableList<HolderTypes.PresetType>,
    private val context: Context
) : RecyclerView.Adapter<HomePresetAdapter.ViewHolder> ()  {

    // private val prefs = PreferenceManager.getDefaultSharedPreferences(context)

    inner class ViewHolder(view: View) :
        RecyclerView.ViewHolder(view) {
        val desc: TextView = view.findViewById(R.id.desc)
        val icon: ImageView = view.findViewById(R.id.indicator)
        val value: TextView = view.findViewById(R.id.value)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(
                    when (viewType) {
                        0 -> R.layout.preset_card_layout
                        1 -> R.layout.preset_card_add
                        2 -> R.layout.preset_card_layout
                        else -> throw IllegalArgumentException()
                    },
                    parent,
                    false
                )
        )
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0 && !cardData[position].isBlock) {
            0
        } else if (position > 0 && !cardData[position].isBlock) {
            2
        } else {
            1
        }
    }

    override fun getItemCount(): Int = cardData.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.value.text = (cardData[position].defVal ?: "").toString()
        holder.desc.text = cardData[position].desc ?: ""
        holder.icon.setImageDrawable(ContextCompat.getDrawable(
            context,
            cardData[position].icon ?: R.drawable.ic_local_cafe
        ))
    }

    /*

    fun updateList(prefs: SharedPreferences) {
        val dumpList = StoreUtils.dumpExpenseList(prefs).toMutableList()
        if (dumpList.isEmpty()) {
            val initializeHorizontalCard = HolderTypes.HorizontalCardData(
                context.getString(R.string.quick_start),
                context.getString(R.string.add_your_expense_type),
                context.getString(R.string.first_expense_type),
                R.drawable.ic_add,
                true
            )
            dumpList.add(initializeHorizontalCard)
        }
        val diffResult = DiffUtil.calculateDiff(DiffCallback(cardData, dumpList))
        cardData.clear()
        cardData.addAll(dumpList)
        diffResult.dispatchUpdatesTo(this)
    }

    private class DiffCallback(
        private val oldList: MutableList<HolderTypes.HorizontalCardData>,
        private val newList: MutableList<HolderTypes.HorizontalCardData>,
    ) : DiffUtil.Callback() {
        override fun getOldListSize() = oldList.size

        override fun getNewListSize() = newList.size

        override fun areItemsTheSame(
            oldItemPosition: Int,
            newItemPosition: Int,
        ) = oldList[oldItemPosition] == newList[newItemPosition]

        override fun areContentsTheSame(
            oldItemPosition: Int,
            newItemPosition: Int,
        ) = oldList[oldItemPosition] == newList[newItemPosition]
    }

     */
}