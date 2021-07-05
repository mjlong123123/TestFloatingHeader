package com.dragon.testfloatingheader

import android.graphics.Canvas
import android.view.View
import android.view.View.MeasureSpec.AT_MOST
import android.view.View.MeasureSpec.EXACTLY
import androidx.core.view.get
import androidx.recyclerview.widget.RecyclerView
import com.dragon.testfloatingheader.databinding.Header1Binding

class FloatingHeaderDecoration(private val headerView: View) : RecyclerView.ItemDecoration() {
    val binding = Header1Binding.bind(headerView)

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        if (headerView.width != parent.width || headerView.height != parent.width) {
            headerView.measure(View.MeasureSpec.makeMeasureSpec(parent.width, EXACTLY), View.MeasureSpec.makeMeasureSpec(parent.height, AT_MOST))
            headerView.layout(0, 0, headerView.measuredWidth, headerView.measuredHeight)
        }
        if (parent.childCount > 0) {
            val child0 = parent[0]
            val holder0 = parent.getChildViewHolder(child0) as? BaseAdapter.BaseViewHolder
            val iFloatingHeader = (holder0?.baseItem as? IFloatingHeader)
            binding.groupTitle.text = iFloatingHeader?.headerTitle ?: "none"
            for (index in 1 until parent.childCount) {
                val childNextLine = parent[index]
                val holderNextLine = parent.getChildViewHolder(childNextLine) as? BaseAdapter.BaseViewHolder
                val iFloatingHeaderNextLine = (holderNextLine?.baseItem as? IFloatingHeader)
                if (iFloatingHeaderNextLine?.isHeader == true && childNextLine.top != child0.top) {
                    binding.root.translationY = (childNextLine.top.toFloat() - binding.root.height).coerceAtMost(0f)
                    c.save()
                    c.translate(0f, binding.root.translationY)
                    binding.root.draw(c)
                    c.restore()
                    return;
                }
            }
            binding.root.draw(c)
        }
    }
}