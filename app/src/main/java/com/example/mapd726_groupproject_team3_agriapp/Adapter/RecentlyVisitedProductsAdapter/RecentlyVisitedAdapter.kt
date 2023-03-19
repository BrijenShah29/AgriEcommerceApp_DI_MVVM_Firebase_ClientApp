package com.example.mapd726_groupproject_team3_agriapp.Adapter.RecentlyVisitedProductsAdapter

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.SpannableString
import android.text.Spanned
import android.text.style.StrikethroughSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mapd726_groupproject_team3_agriapp.DataModels.ProductModel
import com.example.mapd726_groupproject_team3_agriapp.DataModels.RecentlyVisitedModel
import com.example.mapd726_groupproject_team3_agriapp.R
import com.example.mapd726_groupproject_team3_agriapp.ViewModel.ProductsViewModel
import com.example.mapd726_groupproject_team3_agriapp.databinding.LayoutProductItemBinding
import dagger.hilt.android.internal.managers.FragmentComponentManager

class RecentlyVisitedAdapter(val context: Context, val viewModel: ProductsViewModel) : androidx.recyclerview.widget.ListAdapter<RecentlyVisitedModel,RecentlyVisitedAdapter.RecentlyVisitedProductViewHolder>(RecentlyDiffUtil()) {

    var product: ProductModel? = null

    inner class RecentlyVisitedProductViewHolder(val binding: LayoutProductItemBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(item: RecentlyVisitedModel, context: Context) {

            Glide.with(context).load(item.productCoverImg).centerCrop().into(binding.imageView)
            binding.productTitle.text = item.productName
            binding.salePrice.text = "$ " + item.productSpecialPrice

            if(item.onSale == true)
            {
                binding.imageSale.visibility = View.VISIBLE
                binding.normalPrice.text = "$ " + item.productPrice
                var ss = SpannableString(binding.normalPrice.text.toString())
                ss.setSpan(StrikethroughSpan(),0,binding.normalPrice.text.lastIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                binding.normalPrice.setText(ss)
            }else
            {
                binding.normalPrice.text = ""
                binding.imageSale.visibility = View.INVISIBLE
            }
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecentlyVisitedProductViewHolder {
        val binding = LayoutProductItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return RecentlyVisitedProductViewHolder(binding)
    }


    override fun onBindViewHolder(holder: RecentlyVisitedProductViewHolder, position: Int) {
        val item = getItem(position)

        holder.bind(item, context)

        holder.itemView.setOnClickListener {

            //Redirect to the product detail fragment

            // FETCH PRODUCT FROM ROOM AND PARSE IT WITH PARCELABLE
            viewModel.getSingleProduct(item.productId)



            sendToNextScreen(holder.itemView)

        }
    }

    private fun sendToNextScreen(view: View) {

        Handler(Looper.myLooper()!!).postDelayed(Runnable {
            product = viewModel.selectedSingleProduct
            if(product !=null) {
                val bundle = Bundle()
                bundle.putParcelable("product", product)
                // bundle.putString("productID",item.productId)
                val sender =
                    (FragmentComponentManager.findActivity(view.context) as Activity as FragmentActivity).supportFragmentManager
                sender.setFragmentResult("dataFromHome", bundle)

                Navigation.findNavController(view)
                    .navigate(R.id.detailedProductFragment, bundle)
            }
            else
            {
                Toast.makeText(context, "Something Went Wrong !!", Toast.LENGTH_SHORT).show()
                sendToNextScreen(view)
            }
        },300)

    }

    class RecentlyDiffUtil() : DiffUtil.ItemCallback<RecentlyVisitedModel>() {

        override fun areItemsTheSame(
            oldItem: RecentlyVisitedModel,
            newItem: RecentlyVisitedModel,
        ): Boolean {
            return oldItem.productId == newItem.productId &&
                oldItem.id == newItem.id &&
                oldItem.productName == newItem.productName &&
                    oldItem.productSpecialPrice == newItem.productSpecialPrice

        }

        override fun areContentsTheSame(
            oldItem: RecentlyVisitedModel,
            newItem: RecentlyVisitedModel,
        ): Boolean {
           return oldItem == newItem
        }


    }
}