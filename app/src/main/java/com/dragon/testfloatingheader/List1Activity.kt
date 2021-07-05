package com.dragon.testfloatingheader

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dragon.testfloatingheader.databinding.ActivityList1Binding

class List1Activity : AppCompatActivity() {
    lateinit var binding: ActivityList1Binding
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
    lateinit var floatingHeaderDecoration:FloatingHeaderDecoration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityList1Binding.inflate(layoutInflater)
        setContentView(binding.root)
        floatingHeaderDecoration = FloatingHeaderDecoration(layoutInflater.inflate(R.layout.header_1,null))

        binding.recyclerView.adapter = adapter
        binding.recyclerView.addItemDecoration(floatingHeaderDecoration)
        dataSource.commitList(datas)
    }
}