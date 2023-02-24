package com.example.mapd726_groupproject_team3_agriapp.Adapter.CartAdapters

import android.content.Context
import android.text.SpannableString
import android.text.Spanned
import android.text.style.StrikethroughSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mapd726_groupproject_team3_agriapp.DataModels.CartModel
import com.example.mapd726_groupproject_team3_agriapp.Repository.ProductRepository
import com.example.mapd726_groupproject_team3_agriapp.RoomDB.AgroDao
import com.example.mapd726_groupproject_team3_agriapp.ViewModel.ProductsViewModel
import com.example.mapd726_groupproject_team3_agriapp.databinding.CartCardBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import javax.inject.Inject

class CartAdapter(var context: Context, val viewModel: ProductsViewModel) : ListAdapter<CartModel,CartAdapter.CartViewHolder>(Diffutil()) {

    private var cartTotal : Double = 0.0
    inner class CartViewHolder(val binding: CartCardBinding) : RecyclerView.ViewHolder(binding.root){



        fun bind(item: CartModel, context: Context) {

            binding.productTitle.text = item.productName
            binding.productWeight.text = item.productScale
            binding.salePrice.text = "$ " + item.productSpecialPrice
            binding.tvCount.text = item.productQuantity.toString()

            cartTotal+= item.totalAmount!!

            Glide.with(context).load(item.productCoverImg)
                .centerCrop()
                .into(binding.thumbImage)

            if(item.onSale == true)
            {
                binding.normalPrice.text = "$ " + item.productPrice
                var spanString = SpannableString(binding.normalPrice.text.toString())
                spanString.setSpan(StrikethroughSpan(),0,binding.normalPrice.text.lastIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                binding.normalPrice.setText(spanString)
            }else
            {
                binding.normalPrice.visibility = View.INVISIBLE
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = CartCardBinding.inflate(LayoutInflater.from(context),parent,false)
        return CartViewHolder(binding)
    }


    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {

        val item = getItem(position)
        holder.bind(item,context)

        holder.binding.deleteProductButton.setOnClickListener {
            viewModel.deleteProduct(item)
            Snackbar.make(it,"Item Removed From Cart !!", Snackbar.LENGTH_LONG).show()
        }

    }

    private fun deleteProduct() {

    }

    class Diffutil() : DiffUtil.ItemCallback<CartModel>() {

        override fun areItemsTheSame(oldItem: CartModel, newItem: CartModel): Boolean {
            return oldItem.productId == newItem.productId
        }

        override fun areContentsTheSame(oldItem: CartModel, newItem: CartModel): Boolean {
            return oldItem == newItem
        }
    }
}