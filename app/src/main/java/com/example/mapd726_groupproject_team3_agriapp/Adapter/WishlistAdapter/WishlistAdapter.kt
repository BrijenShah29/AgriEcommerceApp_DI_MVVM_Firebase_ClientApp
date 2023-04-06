package com.example.mapd726_groupproject_team3_agriapp.Adapter.WishlistAdapter

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.StrikethroughSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mapd726_groupproject_team3_agriapp.DataModels.ProductModel
import com.example.mapd726_groupproject_team3_agriapp.R
import com.example.mapd726_groupproject_team3_agriapp.databinding.LayoutProductItemBinding
import dagger.hilt.android.internal.managers.FragmentComponentManager

class WishlistAdapter(val context: Context): ListAdapter<ProductModel, WishlistAdapter.WishlistViewHolder>(
    diffUtil()) {


    inner class WishlistViewHolder(val binding: LayoutProductItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ProductModel, context: Context) {
            binding.productTitle.text = item.productName
            binding.salePrice.text = "$ " + item.productSpecialPrice

            Glide.with(context).load(item.productCoverImg)
                .placeholder(context.getDrawable(R.drawable.user))
                .centerCrop()
                .override(300)
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
                binding.imageSale.visibility = View.INVISIBLE
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WishlistViewHolder {

        val binding = LayoutProductItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return WishlistViewHolder(binding)

    }


    override fun onBindViewHolder(holder: WishlistViewHolder, position: Int) {
        val item = getItem(position)

        holder.bind(item, context)

        holder.itemView.setOnClickListener {

            //Redirect to the product detail fragment

            val bundle = Bundle()
            bundle.putParcelable("product",item)
            // bundle.putString("productID",item.productId)
            val sender = (FragmentComponentManager.findActivity(holder.itemView.context) as Activity as FragmentActivity).supportFragmentManager
            sender.setFragmentResult("dataFromHome",bundle)


            Navigation.findNavController(holder.itemView).navigate(R.id.detailedProductFragment,bundle)

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
