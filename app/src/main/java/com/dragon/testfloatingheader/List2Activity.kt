package com.dragon.testfloatingheader

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dragon.testfloatingheader.databinding.ActivityList2Binding

class List2Activity : AppCompatActivity() {
    lateinit var binding: ActivityList2Binding
    val dataSource = DifferDataSource()
    val adapter = BaseAdapter(dataSource)
    val datas = mutableListOf<BaseAdapter.BaseItem>().apply {
        for (groupIndex in 0 until 10) {
            val groupTitle = "Group $groupIndex"
            add(GroupItem(groupTitle))
            for (index in 0 until 20) {
                add(NormalItem("Group $groupIndex item $index", groupTitle))
            }
        }
    }
    lateinit var floatingHeaderDecoration: FloatingHeaderDecorationExt

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityList2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        floatingHeaderDecoration = FloatingHeaderDecorationExt(binding.floatingHeaderLayout.root) { baseItem ->
            when (baseItem) {
                is GroupItem -> {
                    binding.floatingHeaderLayout.groupTitle.text = baseItem.headerTitle
                    binding.floatingHeaderLayout.root.setOnClickListener { Toast.makeText(this, "点击float header ${baseItem.headerTitle}", Toast.LENGTH_LONG).show() }
                }
                is NormalItem -> {
                    binding.floatingHeaderLayout.groupTitle.text = baseItem.headerTitle
                }
            }
        }

        binding.recyclerView.adapter = adapter
        binding.recyclerView.addItemDecoration(floatingHeaderDecoration)
        dataSource.commitList(datas)
    }
}