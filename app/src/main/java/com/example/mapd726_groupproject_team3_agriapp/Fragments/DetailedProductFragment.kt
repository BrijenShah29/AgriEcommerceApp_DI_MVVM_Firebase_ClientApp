package com.example.mapd726_groupproject_team3_agriapp.Fragments

import android.app.Activity
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.StrikethroughSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.mapd726_groupproject_team3_agriapp.Adapter.HomePageAdapters.ImageSliderAdapter
import com.example.mapd726_groupproject_team3_agriapp.DataModels.CartModel
import com.example.mapd726_groupproject_team3_agriapp.DataModels.ProductModel
import com.example.mapd726_groupproject_team3_agriapp.DataModels.Slider
import com.example.mapd726_groupproject_team3_agriapp.Fragments.OtherFragments.BottomSheetFragment
import com.example.mapd726_groupproject_team3_agriapp.R
import com.example.mapd726_groupproject_team3_agriapp.ViewModel.ProductsViewModel
import com.example.mapd726_groupproject_team3_agriapp.databinding.FragmentDetailedProductBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.internal.managers.FragmentComponentManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
@AndroidEntryPoint
class DetailedProductFragment : Fragment() {

    lateinit var binding : FragmentDetailedProductBinding
    lateinit var bottomSheetFragment: BottomSheetFragment
    var quantity : Int = 1


    private val viewModel by viewModels<ProductsViewModel>()

    val sliderList = ArrayList<Slider>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDetailedProductBinding.inflate(layoutInflater)

        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val product = requireArguments().getParcelable<ProductModel>("product")

        setProductFromPreviousSource(product)

        // PRODUCT QUANTITY PLUS MINUS METHOD

        binding.addLessQuantityButton.setOnClickListener {
            if(quantity!! <= 1)
            {
                quantity = 1
                binding.productCount.text == quantity.toString()

            }
            else
            {
                quantity -= 1
                binding.productCount.text = quantity.toString()
            }
        }
        binding.addMoreQuantityButton.setOnClickListener {
            if(quantity>=10)
            {
                quantity = 10
                binding.productCount.text = quantity.toString()
            }
            else
            {
                quantity+=1
                binding.productCount.text=quantity.toString()
            }
        }

        // ADD TO CART ON CLICK METHOD


        binding.addToCart.setOnClickListener {

            cartAction(product, it)
        }









        // SETTING UP PRODUCT DESCRIPTION HIDE SHOW METHOD

        binding.descriptionRelative.setOnClickListener {
            if(binding.productDescriptionText.isVisible)
            {
                binding.productDescriptionText.visibility = View.GONE
                binding.imgDrop.setImageResource(R.drawable.ic_baseline_expand_more_24)
            }
            else{
                binding.productDescriptionText.visibility = View.VISIBLE
                binding.imgDrop.setImageResource(R.drawable.expand_less)
            }
        }





        return binding.root
    }



    private fun  setProductFromPreviousSource(it: ProductModel?) {

            binding.productDescriptionText.text = it!!.productDescription
            binding.productCount.text = quantity.toString()
            binding.productTitle.text = it.productName
            binding.salePrice.text = "$ " + it.productSpecialPrice
            sliderList.add(Slider(it.productCoverImg))
            for(images in it.productImages)
            {
                sliderList.add(Slider(images))
            }
                binding.imageSlider.setSliderAdapter(ImageSliderAdapter(requireContext(),sliderList))

            if(it.onSale == true)
            {
                binding.imageSale.visibility = View.VISIBLE
                binding.normalPrice.text = "$ " + it.productPrice
                var spanString = SpannableString(binding.normalPrice.text.toString())
                spanString.setSpan(StrikethroughSpan(),0,binding.normalPrice.text.lastIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                binding.normalPrice.text = spanString
            }
            else
            {
                binding.imageSale.visibility = View.GONE
                binding.normalPrice.text=""
            }
        }


    private fun cartAction(product: ProductModel?, it : View) {

        viewModel.isExists(product?.productId!!)

        if(viewModel.selectedProduct!= null){

            // UPDATE QUANTITY and PRICE ONLY

        } else {

        var totalAmount = product.productSpecialPrice!!.toDouble() * quantity.toDouble()
            addToCart(product, totalAmount, it)
        }

    }

    private fun addToCart(product: ProductModel, totalAmount: Double, view : View) {

        val data = CartModel(product.productName,product.productCoverImg,product.productCategory,product.productSubCategory,product.productId!!,product.productPrice,product.discountRate,product.onSale,product.productSpecialPrice,product.productScale,quantity,totalAmount)
        lifecycleScope.launch(Dispatchers.IO){
            viewModel.insertCartProducts(data)
        }

        Snackbar.make(view,"Product Added to Cart Successfully !!", Snackbar.LENGTH_SHORT).show()
      // val sender = (FragmentComponentManager.findActivity(requireContext()) as Activity as FragmentActivity).supportFragmentManager


    }

}