package com.dragon.testfloatingheader

import android.graphics.Canvas
import android.view.View
import android.view.View.MeasureSpec.AT_MOST
import android.view.View.MeasureSpec.EXACTLY
import androidx.core.view.get
import androidx.recyclerview.widget.RecyclerView
import com.dragon.testfloatingheader.databinding.Header1Binding

class FloatingHeaderDecoration(private val headerView: View) : RecyclerView.ItemDecoration() {
    private val binding = Header1Binding.bind(headerView)

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        //headerView没有被添加到view的描画系统，所以这里需要主动测量和布局。
        if (headerView.width != parent.width) {
            //测量时控件宽度按照parent的宽度设置确切的大小，控件的高度按照最大不超过parent的高度。
            headerView.measure(View.MeasureSpec.makeMeasureSpec(parent.width, EXACTLY), View.MeasureSpec.makeMeasureSpec(parent.height, AT_MOST))
            //默认布局位置在parent的顶部位置。
            headerView.layout(0, 0, headerView.measuredWidth, headerView.measuredHeight)
        }

        if (parent.childCount > 0) {
            //获取第一个可见item。
            val child0 = parent[0]
            //获取holder。
            val holder0 = parent.getChildViewHolder(child0) as? BaseAdapter.BaseViewHolder
            //获取实现接口IFloatingHeader 的item。
            val iFloatingHeader = (holder0?.baseItem as? IFloatingHeader)
            //header内容绑定。
            binding.groupTitle.text = iFloatingHeader?.headerTitle ?: "none"
            //查找下一个header view
            val nextHeaderChild = findNextHeaderView(parent)
            if (nextHeaderChild == null) {
                //没找到的情况下显示在parent的顶部
                binding.root.draw(c)
            } else {
                //float header默认显示在顶部，它有可能被向上推，所以它的translationY<=0。通过下一个header的位置计算它被推动的距离
                val translationY = (nextHeaderChild.top.toFloat() - binding.root.height).coerceAtMost(0f)
                c.save()
                c.translate(0f, translationY)
                binding.root.draw(c)
                c.restore()
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