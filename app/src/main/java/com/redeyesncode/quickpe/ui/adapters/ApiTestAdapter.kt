package com.redeyesncode.quickpe.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.redeyesncode.quickpe.data.ResponseApiTest
import com.redeyesncode.quickpe.databinding.ItemLoanPackageBinding

class ApiTestAdapter(var context:Context,var data:ArrayList<ResponseApiTest.Category>):RecyclerView.Adapter<ApiTestAdapter.MyViewholder> (){

    lateinit var binding:ItemLoanPackageBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewholder {
        binding = ItemLoanPackageBinding.inflate(LayoutInflater.from(context))

        return MyViewholder(binding)
    }

    override fun onBindViewHolder(holder: MyViewholder, position: Int) {
        val category = data.get(position)
        holder.binding.apply {
            binding.tvAmount.text = category.name
            binding.tvTenure.text = category.id
        }

    }

    override fun getItemCount(): Int {
        return data.size
    }

    class MyViewholder(var binding:ItemLoanPackageBinding):RecyclerView.ViewHolder(binding.root)
}