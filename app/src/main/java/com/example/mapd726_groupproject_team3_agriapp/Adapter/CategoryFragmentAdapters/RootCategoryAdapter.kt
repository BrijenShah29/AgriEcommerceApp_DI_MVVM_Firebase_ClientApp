package com.example.mapd726_groupproject_team3_agriapp.Adapter.CategoryFragmentAdapters

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mapd726_groupproject_team3_agriapp.DataModels.CategoryModel
import com.example.mapd726_groupproject_team3_agriapp.R
import com.example.mapd726_groupproject_team3_agriapp.Utils.UserManager
import com.example.mapd726_groupproject_team3_agriapp.databinding.ItemRootcategoryLayoutBinding

class RootCategoryAdapter(
    val context: Context,
    val list: ArrayList<CategoryModel>,
    val userManager: UserManager
) : RecyclerView.Adapter<RootCategoryAdapter.rootCategoryViewHolder>() {

    inner class rootCategoryViewHolder(val binding : ItemRootcategoryLayoutBinding) : RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): rootCategoryViewHolder {
        val binding = ItemRootcategoryLayoutBinding.inflate(LayoutInflater.from(context),parent,false)
        return rootCategoryViewHolder(binding)
    }


    override fun onBindViewHolder(holder: rootCategoryViewHolder, position: Int) {
        holder.binding.rootCategory.text = list[position].Root
        Glide.with(context).load(list[position].Image).centerCrop().into(holder.binding.rootImage)

        holder.itemView.setOnClickListener {
            userManager.saveSelectedUserCategory(list[position].Root)
            val bundle = Bundle()

            bundle.putString("cat",list[position].Root)
            bundle.putString("img",list[position].Image)

            val sender = (holder.itemView.context as FragmentActivity).supportFragmentManager
            sender.setFragmentResult("dataFromRootCategory",bundle)

            findNavController(holder.itemView).navigate(R.id.action_categoryFragment_to_subCategoryFragment,bundle)

/*
            val intent = Intent(context, SubCategoryActivity :: class.java)
            intent.putExtra("cat",list[position].Root)
            intent.putExtra("img",list[position].Image)
            context.startActivity(intent)

 */
        }

    }


    override fun getItemCount(): Int {
        return list.size
    }
}