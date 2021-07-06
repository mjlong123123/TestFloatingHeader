package com.dragon.testfloatingheader

import android.graphics.Canvas
import android.view.View
import android.view.View.MeasureSpec.AT_MOST
import android.view.View.MeasureSpec.EXACTLY
import androidx.core.view.get
import androidx.recyclerview.widget.RecyclerView

class FloatingHeaderDecorationExt(
    private val headerView: View,
    private val block: (BaseAdapter.BaseItem) -> Unit
) : RecyclerView.ItemDecoration() {

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        if (parent.childCount > 0) {
            //获取第一个可见item。
            val child0 = parent[0]
            //获取holder。
            val holder0 = parent.getChildViewHolder(child0) as? BaseAdapter.BaseViewHolder
            //获取实现接口IFloatingHeader 的item。
            //header内容绑定。
            holder0?.baseItem?.let {
                block.invoke(it)
            }
            //查找下一个header view
            val nextHeaderChild = findNextHeaderView(parent)
            if (nextHeaderChild == null) {
                //没找到的情况下显示在parent的顶部
                headerView.translationY = 0f
            } else {
                //float header默认显示在顶部，它有可能被向上推，所以它的translationY<=0。通过下一个header的位置计算它被推动的距离
                headerView.translationY = (nextHeaderChild.top.toFloat() - headerView.height).coerceAtMost(0f)
            }
        }
    }

    private fun findNextHeaderView(parent: RecyclerView): View? {
        for (index in 1 until parent.childCount) {
            val childNextLine = parent[index]
            val holderNextLine = parent.getChildViewHolder(childNextLine) as? BaseAdapter.BaseViewHolder
            val iFloatingHeaderNextLine = (holderNextLine?.baseItem as? IFloatingHeader)
            //查找下一个header的view
            if (iFloatingHeaderNextLine?.isHeader == true) {
                return childNextLine
            }
        }
        return null
    }
}