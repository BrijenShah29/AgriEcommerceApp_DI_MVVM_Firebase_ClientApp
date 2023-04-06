package com.example.mapd726_groupproject_team3_agriapp.Adapter.CheckoutPageItemAdapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.Glide
import com.example.mapd726_groupproject_team3_agriapp.DataModels.CartModel
import com.example.mapd726_groupproject_team3_agriapp.databinding.ItemCheckoutProductsLayoutBinding

class CheckoutProductAdapter(val context : Context) : ListAdapter<CartModel,CheckoutProductAdapter.CheckoutProductViewHolder>(DiffUtilCheckoutAdapter()) {

    inner class CheckoutProductViewHolder(val binding: ItemCheckoutProductsLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
            fun bind(item: CartModel, context: Context) {
                binding.cartBadgeText.text = item.productQuantity.toString()
                binding.productScale.text = item.productScale.toString()
                binding.productTitle.text = item.productName.toString()
                binding.TotalAmountPerProduct.text = String.format("$ %.2f", item.totalAmount)
                Glide.with(context).load(item.productCoverImg)
                    .centerCrop()
                    .override(300)
                    .into(binding.productImage)
            }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CheckoutProductViewHolder {

        val binding = ItemCheckoutProductsLayoutBinding.inflate(LayoutInflater.from(context), parent, false)
        return CheckoutProductViewHolder(binding)
    }


    override fun onBindViewHolder(holder: CheckoutProductViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, context)
    }
    class DiffUtilCheckoutAdapter() : DiffUtil.ItemCallback<CartModel>(){

        override fun areItemsTheSame(oldItem: CartModel, newItem: CartModel): Boolean {
            return oldItem.productId == newItem.productId &&
                    oldItem.productQuantity == newItem.productQuantity
        }
        override fun areContentsTheSame(oldItem: CartModel, newItem: CartModel): Boolean {
           return oldItem == newItem
        }

    }

}