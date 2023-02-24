package com.example.mapd726_groupproject_team3_agriapp.Adapter.HomePageAdapters.HomePageSubCategoryAdapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mapd726_groupproject_team3_agriapp.DataModels.CategoryModel
import com.example.mapd726_groupproject_team3_agriapp.databinding.LayoutCategoryItemBinding


class SubCategoryCropProtectionAdapter (var context: Context, val limit : Int = 3) : ListAdapter<CategoryModel,SubCategoryCropProtectionAdapter.SubCategoryCropProtectionViewHolder>(DiffUtil())  {

    inner class SubCategoryCropProtectionViewHolder(val binding: LayoutCategoryItemBinding)
        :RecyclerView.ViewHolder(binding.root){
            fun bind(item: CategoryModel, context: Context) {

              binding.textView.text = item.Category
                Glide.with(context).load(item.Image).centerCrop()
                    .into(binding.imageView)

            }
        }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubCategoryCropProtectionViewHolder {
        return SubCategoryCropProtectionViewHolder(LayoutCategoryItemBinding.inflate(LayoutInflater.from(context),parent,false))
    }


    override fun onBindViewHolder(holder: SubCategoryCropProtectionViewHolder, position: Int) {
        val item = getItem(position)

        holder.bind(item,context)


    }
    class DiffUtil : androidx.recyclerview.widget.DiffUtil.ItemCallback<CategoryModel>() {

        override fun areItemsTheSame(oldItem: CategoryModel, newItem: CategoryModel): Boolean {
            return oldItem.Category == newItem.Category
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: CategoryModel, newItem: CategoryModel): Boolean {
            return oldItem == newItem
        }
    }


}