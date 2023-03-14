package com.example.mapd726_groupproject_team3_agriapp.Fragments

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
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
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.mapd726_groupproject_team3_agriapp.Adapter.HomePageAdapters.ImageSliderAdapter
import com.example.mapd726_groupproject_team3_agriapp.Adapter.RecentlyVisitedProductsAdapter.RecentlyVisitedAdapter
import com.example.mapd726_groupproject_team3_agriapp.DataModels.CartModel
import com.example.mapd726_groupproject_team3_agriapp.DataModels.ProductModel
import com.example.mapd726_groupproject_team3_agriapp.DataModels.RecentlyVisitedModel
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
    var allowRefresh = true


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

        // Adding product to previously visited Table

        viewModel.insertRecentlyVisitedProducts(RecentlyVisitedModel(product?.productName,product?.productCoverImg,product!!.productId,product?.productPrice,product?.discountRate,product?.onSale,product?.productSpecialPrice))

        // SHOWING RECENTLY VISITED PRODUCTS
        val adapter = RecentlyVisitedAdapter(requireContext(),viewModel)
        binding.previousVisitedProductsRecycler.adapter = adapter
        binding.previousVisitedProductsRecycler.setHasFixedSize(true)
        viewModel.recentlyVisitedProducts.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)

        })


        // PRODUCT QUANTITY PLUS MINUS METHOD

        binding.addLessQuantityButton.setOnClickListener {
            quantity = 1
            if(quantity!! <= 1)
            {
                quantity = 1
                binding.productCount.text = quantity.toString()

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

       // FIRST WE HAVE TO CHECK IF PRODUCT IS IN CART OR NOT.. IF IT IS IN CART, WE UPDATE IT ELSE WE ADD IT AS NEW ITEM


        viewModel.isExists(product?.productId!!)

        Handler(Looper.myLooper()!!).postDelayed(Runnable {

        if(viewModel.selectedProduct!= null){

            // UPDATE QUANTITY and PRICE ONLY

            val selectedProductQty = viewModel.selectedProduct?.productQuantity
            quantity += selectedProductQty!!
            if(quantity > 10){
                quantity = 10
                var totalAmount = product.productSpecialPrice!!.toDouble() * quantity.toDouble()
                updateCart(product, totalAmount, it)
            }
            else
            {
                var totalAmount = product.productSpecialPrice!!.toDouble() * quantity.toDouble()
                updateCart(product, totalAmount, it)
            }

        } else {

        var totalAmount = product.productSpecialPrice!!.toDouble() * quantity.toDouble()
            addToCart(product, totalAmount, it)
        }

    },300)

    }

    private fun updateCart(product: ProductModel, totalAmount: Double, it: View) {

        viewModel.updateCart(quantity,totalAmount,product.productId)
        Snackbar.make(it, "Cart Updated Successfully !!", Snackbar.LENGTH_SHORT).show()

    }

    private fun addToCart(product: ProductModel, totalAmount: Double, view : View) {

        val data = CartModel(product.productName,product.productCoverImg,product.productCategory,product.productSubCategory,product.productId!!,product.productPrice,product.discountRate,product.onSale,product.productSpecialPrice,product.productScale,quantity,totalAmount)
        lifecycleScope.launch(Dispatchers.IO){
            viewModel.insertCartProducts(data)
        }

        Snackbar.make(view,"Product Added to Cart Successfully !!", Snackbar.LENGTH_SHORT).show()
        quantity = 1
      // val sender = (FragmentComponentManager.findActivity(requireContext()) as Activity as FragmentActivity).supportFragmentManager


    }

    override fun onPause() {
        super.onPause()
        //(FragmentComponentManager.findActivity(requireContext()) as Activity as FragmentActivity).supportFragmentManager.beginTransaction().remove(DetailedProductFragment()).add(R.id.fragmentContainer,DetailedProductFragment()).commit()
          //  .remove(DetailedProductFragment()).attach(this).commit()
        (FragmentComponentManager.findActivity(requireContext()) as Activity as FragmentActivity).supportFragmentManager.beginTransaction().detach(DetailedProductFragment()).attach(this).commit()
    }


    override fun onResume() {
        super.onResume()
       // (FragmentComponentManager.findActivity(requireContext()) as Activity as FragmentActivity).supportFragmentManager.beginTransaction().detach(DetailedProductFragment()).attach(this).commit()

        if (allowRefresh)
        {
            allowRefresh = false
            (FragmentComponentManager.findActivity(requireContext()) as Activity as FragmentActivity).supportFragmentManager.beginTransaction().detach(DetailedProductFragment()).attach(this).commit()
        }

    }

}