package com.example.mapd726_groupproject_team3_agriapp.Adapter.HomePageAdapters

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.StrikethroughSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.mapd726_groupproject_team3_agriapp.DataModels.CategoryModel
import com.example.mapd726_groupproject_team3_agriapp.DataModels.ProductModel
import com.example.mapd726_groupproject_team3_agriapp.Fragments.HomeFragment
import com.example.mapd726_groupproject_team3_agriapp.R
import com.example.mapd726_groupproject_team3_agriapp.databinding.LayoutProductItemBinding
import dagger.hilt.android.internal.managers.FragmentComponentManager

class ProductAdapter(val context: Context): ListAdapter<ProductModel,ProductAdapter.ProductViewHolder>(
    diffUtil()) {


    inner class ProductViewHolder(val binding: LayoutProductItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ProductModel, context: Context) {
            binding.productTitle.text = item.productName
            binding.salePrice.text = "$ " + item.productSpecialPrice

            Glide.with(context).load(item.productCoverImg)
                .centerCrop()
                .into(binding.imageView)

            if(item.onSale == true)
            {
                binding.imageSale.visibility = View.VISIBLE
                binding.normalPrice.text = "$ " + item.productPrice
                var spanString = SpannableString(binding.normalPrice.text.toString())
                spanString.setSpan(StrikethroughSpan(),0,binding.normalPrice.text.lastIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                binding.normalPrice.setText(spanString)
            }else
            {
                binding.normalPrice.text = ""
                binding.normalPrice.visibility = View.INVISIBLE
                binding.imageSale.visibility = View.INVISIBLE
            }





            }

        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {

        val binding = LayoutProductItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return ProductViewHolder(binding)

    }


    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val item = getItem(position)

        holder.bind(item, context)

       holder.itemView.setOnClickListener {

            //Redirect to the product detail fragment

            val bundle = Bundle()
            bundle.putParcelable("product",item)
           // bundle.putString("productID",item.productId)
            val sender = (FragmentComponentManager.findActivity(holder.itemView.context) as Activity as FragmentActivity).supportFragmentManager
            sender.setFragmentResult("dataFromHome",bundle)


            Navigation.findNavController(holder.itemView).navigate(R.id.action_homeFragment_to_detailedProductFragment,bundle)

            // Add to Room Database for Previous Product history



        }

    }


    class diffUtil() : DiffUtil.ItemCallback<ProductModel>() {

        override fun areItemsTheSame(oldItem: ProductModel, newItem: ProductModel): Boolean {
          return  oldItem.productId == newItem.productId
        }


        override fun areContentsTheSame(oldItem: ProductModel, newItem: ProductModel): Boolean {
            return oldItem == newItem
        }


    }

}