package com.redeyesncode.pay2kart.base

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class BaseAdapter<T>(
    private val context: Context,
    private val layoutResId: Int,
    private val itemCount: Int
) : RecyclerView.Adapter<BaseAdapter<T>.ViewHolder>() {

    private var onBindData: ((ViewHolder, Int) -> Unit)? = null

    fun setOnBindDataListener(listener: (ViewHolder, Int) -> Unit) {
        onBindData = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(layoutResId, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        onBindData?.invoke(holder, position)
    }

    override fun getItemCount(): Int {
        return itemCount
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}