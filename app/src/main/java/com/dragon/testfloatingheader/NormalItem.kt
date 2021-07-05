package com.dragon.testfloatingheader

class NormalItem(val title:String, val groupTitle:String):BaseAdapter.BaseItem(),IFloatingHeader {
    override val viewType: Int
        get() = R.layout.item_1
    override val isHeader: Boolean
        get() = false
    override val headerTitle: String
        get() = groupTitle

    override fun bind(holder: BaseAdapter.BaseViewHolder, position: Int) {
        holder.textView(R.id.titleView).text = title
    }

    override fun isSameItem(item: BaseAdapter.BaseItem): Boolean {
        return (item as? NormalItem)?.title == title
    }
}