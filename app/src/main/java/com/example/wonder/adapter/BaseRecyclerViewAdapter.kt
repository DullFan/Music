package com.example.wonder.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

/**
 * 通用RecyclerView适配器
 */
abstract class BaseRecyclerViewAdapter<T>(var layoutId: Int, _dataList: List<T>, _index: Int = 0) :
    RecyclerView.Adapter<RecyclerViewViewHolder>() {
    var dataList = _dataList
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var index = _index
        set(value) {
            notifyItemChanged(field)
            field = value
            notifyItemChanged(index)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewViewHolder {
        val viewBinding: ViewDataBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            layoutId,
            parent,
            false
        )

        return RecyclerViewViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: RecyclerViewViewHolder, position: Int) {
        onBind(holder.viewDataBinding,dataList[position],position)
    }

    abstract fun onBind(rvDataBinding: ViewDataBinding, data: T, position: Int)

    override fun getItemCount(): Int {
        return dataList.size
    }
}

class RecyclerViewViewHolder(val viewDataBinding: ViewDataBinding) :
    RecyclerView.ViewHolder(viewDataBinding.root)