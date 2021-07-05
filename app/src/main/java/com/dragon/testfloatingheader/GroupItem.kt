package com.dragon.testfloatingheader

class GroupItem(val title:String):BaseAdapter.BaseItem(),IFloatingHeader {

    override val viewType: Int
        get() = R.layout.header_1

    override val isHeader: Boolean
        get() = true
    override val headerTitle: String
        get() = title

    override fun bind(holder: BaseAdapter.BaseViewHolder, position: Int) {
        holder.textView(R.id.groupTitle).text = title
    }

    override fun isSameItem(item: BaseAdapter.BaseItem): Boolean {
        return (item as? GroupItem)?.title == title
    }
}