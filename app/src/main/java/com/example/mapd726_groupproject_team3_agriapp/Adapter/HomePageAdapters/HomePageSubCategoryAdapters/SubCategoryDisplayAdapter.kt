package com.example.mapd726_groupproject_team3_agriapp.Adapter.HomePageAdapters.HomePageSubCategoryAdapters

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mapd726_groupproject_team3_agriapp.DataModels.SubCategoryModel
import com.example.mapd726_groupproject_team3_agriapp.R
import com.example.mapd726_groupproject_team3_agriapp.databinding.LayoutCategoryItemBinding
import dagger.hilt.android.internal.managers.FragmentComponentManager

class SubCategoryDisplayAdapter (var context: Context) : ListAdapter<SubCategoryModel,SubCategoryDisplayAdapter.SubCategoryDisplayAdapter>(diffUtil())  {

    inner class SubCategoryDisplayAdapter(val binding: LayoutCategoryItemBinding)
        :RecyclerView.ViewHolder(binding.root){

            fun bind(item: SubCategoryModel, context: Context) {
                binding.textView.text = item.SubCategory.toString()
                Glide.with(context).load(item.Image).centerCrop().into(binding.imageView)

                itemView.setOnClickListener {

                    val bundle = Bundle()
                    bundle.putString("products",item.SubCategory.toString())

                    val sender = (FragmentComponentManager.findActivity(itemView.context) as Activity as FragmentActivity).supportFragmentManager
                    sender.setFragmentResult("fromHomePage",bundle)

                    Navigation.findNavController(itemView)
                        .navigate(R.id.action_homeFragment_to_productsFragment,bundle)
                }
            }
        }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubCategoryDisplayAdapter {
        val binding = LayoutCategoryItemBinding.inflate(LayoutInflater.from(context),parent,false)
        return SubCategoryDisplayAdapter(binding)
    }


    override fun onBindViewHolder(holder: SubCategoryDisplayAdapter, position: Int) {

        val item = getItem(position)
        holder.bind(item,context)

    }
    class diffUtil : DiffUtil.ItemCallback<SubCategoryModel>(){

        override fun areItemsTheSame(
            oldItem: SubCategoryModel,
            newItem: SubCategoryModel,
        ): Boolean {
            return oldItem.categoryId == newItem.categoryId
        }


        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: SubCategoryModel, newItem: SubCategoryModel): Boolean {
            return oldItem == newItem
        }

    }
}