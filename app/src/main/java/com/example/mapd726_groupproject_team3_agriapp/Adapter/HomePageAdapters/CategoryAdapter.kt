package com.example.mapd726_groupproject_team3_agriapp.Adapter.HomePageAdapters

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.mapd726_groupproject_team3_agriapp.DataModels.CategoryModel
import com.example.mapd726_groupproject_team3_agriapp.R
import dagger.hilt.android.internal.managers.FragmentComponentManager


class CategoryAdapter(var context: Context) :
    ListAdapter<CategoryModel, CategoryAdapter.CategoryViewHolder>(diffutil()) {

    inner class CategoryViewHolder(view : View) : RecyclerView.ViewHolder(view){
        var txtBox = view.findViewById<TextView>(R.id.textView)
        var img = view.findViewById<ImageView>(R.id.imageView)

        fun bind(item : CategoryModel, context: Context) {

            txtBox.text = item.Category
            Glide.with(context).load(item.Image)
                .centerCrop()
                .into(img)




        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_category_item, parent, false))
    }


    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item,context)

        holder.itemView.setOnClickListener {

            //  val intent = Intent(context, ProductsActivity :: class.java)
            //    intent.putExtra("products",item.Category)
            //  context.startActivity(intent)


            val bundle = Bundle()
            bundle.putString("products",item.Category)
            bundle.putString("field","productCategory")

            val sender = (FragmentComponentManager.findActivity(holder.itemView.context) as Activity as FragmentActivity).supportFragmentManager
            sender.setFragmentResult("fromHomePage",bundle)

            Navigation.findNavController(holder.itemView)
                .navigate(R.id.action_homeFragment_to_productsFragment,bundle)

        }



    }

    class diffutil() : DiffUtil.ItemCallback<CategoryModel>(){

        override fun areItemsTheSame(oldItem: CategoryModel, newItem: CategoryModel): Boolean {
            return oldItem.Category == newItem.Category
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: CategoryModel, newItem: CategoryModel): Boolean {
            return oldItem == newItem
        }


    }
}