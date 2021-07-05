package com.dragon.testfloatingheader

import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil

/**
 * @author dragon
 */
class DifferDataSource() : BaseAdapter.BaseDataSource<BaseAdapter.BaseItem>() {
    private val itemCallback: DiffUtil.ItemCallback<BaseAdapter.BaseItem> = object : DiffUtil.ItemCallback<BaseAdapter.BaseItem>() {
        override fun areItemsTheSame(oldItem: BaseAdapter.BaseItem, newItem: BaseAdapter.BaseItem): Boolean {
            return oldItem.isSameItem(newItem)
        }

        override fun areContentsTheSame(oldItem: BaseAdapter.BaseItem, newItem: BaseAdapter.BaseItem): Boolean {
            return oldItem.isSameContent(newItem)
        }
    }

    private lateinit var differ: AsyncListDiffer<BaseAdapter.BaseItem>

    override fun attach(adapter: BaseAdapter<BaseAdapter.BaseItem>) {
        super.attach(adapter)
        differ = AsyncListDiffer(adapter, itemCallback)
    }

    override fun get(index: Int): BaseAdapter.BaseItem = differ.currentList[index]

    override fun size() = differ.currentList.size

    fun commitList(list: List<BaseAdapter.BaseItem>) {
        differ.submitList(list)
    }

    fun getItem(index: Int) = differ.currentList[index]

    fun indexOf(predicate: (BaseAdapter.BaseItem) -> Int) = differ.currentList.binarySearch(comparison = predicate)

    fun iterator() = differ.currentList.iterator()
}