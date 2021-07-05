package com.dragon.testfloatingheader

import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.NO_ID

/**
 * @author dragon
 */
class BaseAdapter<out T : BaseAdapter.BaseItem>(private val dataSource: BaseDataSource<T>) : RecyclerView.Adapter<BaseAdapter.BaseViewHolder>() {
    init {
        dataSource.attach(this)
    }

    override fun getItemViewType(position: Int) = dataSource.get(position).viewType

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = BaseViewHolder(LayoutInflater.from(parent.context).inflate(viewType, parent, false))

    override fun getItemCount() = dataSource.size()

    override fun getItemId(position: Int) = dataSource.get(position).getStableId()

    fun getItem(position: Int) = dataSource.get(position)

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val item = dataSource.get(position)
        item.viewHolder = holder
        holder.baseItem = item
        item.bind(holder, position)
    }

    abstract class BaseItem {
        internal var viewHolder: BaseViewHolder? = null
        val availableHolder: BaseViewHolder?
            get() {
                return if (viewHolder?.baseItem == this)
                    viewHolder
                else
                    null
            }
        abstract val viewType: Int
        abstract fun bind(holder: BaseViewHolder, position: Int)
        abstract fun isSameItem(item: BaseItem): Boolean
        open fun isSameContent(item: BaseItem): Boolean {
            return isSameItem(item)
        }

        fun getStableId() = NO_ID
    }

    class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var baseItem: BaseItem? = null
        val views = SparseArray<View>(4)

        fun <V : View> findViewById(id: Int): V {
            var ret = views[id]
            if (ret == null) {
                ret = itemView.findViewById(id)
                checkNotNull(ret)
                views.put(id, ret)
            }
            return ret as V
        }

        fun textView(id: Int): TextView = findViewById(id)
        fun imageView(id: Int): ImageView = findViewById(id)
        fun checkBox(id: Int): CheckBox = findViewById(id)
    }

    abstract class BaseDataSource<T : BaseItem> {
        private var attachedAdapter: BaseAdapter<T>? = null
        open fun attach(adapter: BaseAdapter<T>) {
            attachedAdapter = adapter
        }

        abstract fun get(index: Int): T
        abstract fun size(): Int
    }
}