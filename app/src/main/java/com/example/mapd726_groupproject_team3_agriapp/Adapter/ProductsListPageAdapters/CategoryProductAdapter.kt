package com.example.mapd726_groupproject_team3_agriapp.Adapter.ProductsListPageAdapters

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.StrikethroughSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.navigation.Navigation
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mapd726_groupproject_team3_agriapp.DataModels.ProductModel
import com.example.mapd726_groupproject_team3_agriapp.DataModels.WishlistModel
import com.example.mapd726_groupproject_team3_agriapp.Fragments.OtherFragments.BottomSheetFragment
import com.example.mapd726_groupproject_team3_agriapp.R
import com.example.mapd726_groupproject_team3_agriapp.Utils.Constant
import com.example.mapd726_groupproject_team3_agriapp.ViewModel.UserViewModel
import com.example.mapd726_groupproject_team3_agriapp.databinding.ItemProductListingLayoutBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.internal.managers.FragmentComponentManager

class CategoryProductAdapter(val context: Context, val userViewModel: UserViewModel) : ListAdapter<ProductModel,CategoryProductAdapter.CategoryProductViewHolder>(DiffUtil()){

    inner class CategoryProductViewHolder(val binding: ItemProductListingLayoutBinding): RecyclerView.ViewHolder(binding.root)
    {
        fun bind(context: Context, item: ProductModel, userViewModel: UserViewModel) {

            Glide.with(context).load(item.productCoverImg).placeholder(context.getDrawable(R.drawable.user)).centerCrop().into(binding.imageView)
            binding.productTitle.text = item.productName
            binding.salePrice.text = "$ " + item.productSpecialPrice

            if(item.onSale == true)
            {
                binding.imageSale.visibility = VISIBLE
                binding.normalPrice.text = "$ " + item.productPrice
                var ss = SpannableString(binding.normalPrice.text.toString())
                ss.setSpan(StrikethroughSpan(),0,binding.normalPrice.text.lastIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                binding.normalPrice.setText(ss)
            }else
            {
                binding.normalPrice.text = ""
                binding.imageSale.visibility = INVISIBLE
            }

            //SETTING UP IMAGE ON CLICK LISTENER
            binding.imageView.setOnClickListener {
                val bundle = Bundle()
                bundle.putParcelable("product",item)
                // bundle.putString("productID",item.productId)
                val sender = (FragmentComponentManager.findActivity(itemView.context) as Activity as FragmentActivity).supportFragmentManager
                sender.setFragmentResult("dataFromHome",bundle)
                Navigation.findNavController(itemView).navigate(R.id.action_productsFragment_to_detailedProductFragment,bundle)
            }

            //SETTING UP QUICK ACTION BUTTON ON CLICK LISTENER
            binding.quickAction.setOnClickListener {
                Log.d("Quick Action called",item.productName.toString())

               // FragmentComponentManager.findActivity(itemView.context) as Activity as FragmentActivity
                //val sender = (itemView.context as FragmentActivity).supportFragmentManager
                val sender = ( FragmentComponentManager.findActivity(itemView.context) as Activity as FragmentActivity).supportFragmentManager
                val bottomSheetFragment = BottomSheetFragment()

                val bundle = Bundle()
                bundle.putParcelable("product",item)



                // SENDING BUNDLE TO BOTTOM SHEET FRAGMENT
                bottomSheetFragment.arguments = bundle
                bottomSheetFragment.show(sender,bottomSheetFragment.tag)
            }

            binding.wishlistCheckbox.setOnCheckedChangeListener { compoundButton, b ->
                val data = WishlistModel(
                    item.productName,
                    item.productDescription,
                    item.productCoverImg,
                    item.productCategory,
                    item.productSubCategory,
                    item.productId,
                    item.productPrice!!,
                    item.discountRate,
                    item.stock,
                    item.onSale,
                    item.productSpecialPrice,
                    item.productScale,
                    item.productImages)

                if(compoundButton.isChecked)
                {
                    // add to room db wishlist , sync with server on onStart
                    Constant.wishlist.add(data)
                    Constant.wishlistProductId.add(data.productId)
                    userViewModel.addProductIntoWishlist(data)

                    Snackbar.make(itemView,"Product has been added to your Wishlist",Snackbar.LENGTH_SHORT).show()
                }
                else
                {
                    Constant.wishlist.remove(data)
                    Constant.wishlistProductId.remove(data.productId)
                    userViewModel.removeProductIntoWishlist(item.productId)
                    Snackbar.make(itemView,"Product has been removed from your Wishlist",Snackbar.LENGTH_SHORT).show()
                }
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryProductViewHolder {
        val binding = ItemProductListingLayoutBinding.inflate(LayoutInflater.from(context),parent,false)
        return CategoryProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryProductViewHolder, position: Int) {
        val item  = getItem(position)
        holder.bind(context,item,userViewModel)
    }

    class DiffUtil : androidx.recyclerview.widget.DiffUtil.ItemCallback<ProductModel>(){

        override fun areItemsTheSame(oldItem: ProductModel, newItem: ProductModel): Boolean {
            return oldItem.productId == newItem.productId
        }


        override fun areContentsTheSame(oldItem: ProductModel, newItem: ProductModel): Boolean {
           return oldItem == newItem
        }

    }
}