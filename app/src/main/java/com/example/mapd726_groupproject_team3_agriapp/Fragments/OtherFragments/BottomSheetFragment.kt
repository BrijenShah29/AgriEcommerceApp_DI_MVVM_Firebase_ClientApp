package com.example.mapd726_groupproject_team3_agriapp.Fragments.OtherFragments



import android.app.Activity
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.StrikethroughSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import com.bumptech.glide.Glide
import com.example.mapd726_groupproject_team3_agriapp.DataModels.CartModel
import com.example.mapd726_groupproject_team3_agriapp.DataModels.ProductModel
import com.example.mapd726_groupproject_team3_agriapp.Fragments.CartFragment
import com.example.mapd726_groupproject_team3_agriapp.Fragments.DetailedProductFragment
import com.example.mapd726_groupproject_team3_agriapp.Fragments.ProductsFragment
import com.example.mapd726_groupproject_team3_agriapp.R
import com.example.mapd726_groupproject_team3_agriapp.Repository.ProductRepository
import com.example.mapd726_groupproject_team3_agriapp.ViewModel.ProductsViewModel
import com.example.mapd726_groupproject_team3_agriapp.databinding.FragmentBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.internal.managers.FragmentComponentManager
import kotlinx.coroutines.delay

@AndroidEntryPoint
class BottomSheetFragment : BottomSheetDialogFragment(){

    lateinit var binding : FragmentBottomSheetBinding

    private val viewModel by viewModels<ProductsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentBottomSheetBinding.inflate(layoutInflater)

        val product = requireArguments().getParcelable<ProductModel>("product")

        setBottomSheetData(product)

        val fm: FragmentManager = requireActivity().supportFragmentManager



        binding.viewProduct.setOnClickListener {

            val bundle = Bundle()
            bundle.putParcelable("product",product)
            val newFrame: Fragment = DetailedProductFragment()
         //   fm.setFragmentResult("dataFromHome",bundle)
          //  newFrame.arguments = bundle
            dismissAllowingStateLoss()


            val sender = (FragmentComponentManager.findActivity(requireContext()) as Activity as FragmentActivity).supportFragmentManager
            sender.setFragmentResult("dataFromHome",bundle)


            Navigation.findNavController(requireActivity(),R.id.fragmentContainer).navigate(R.id.action_productsFragment_to_detailedProductFragment,bundle)


          //  Navigation.findNavController(requireView()).navigate(R.id.action_bottomSheetFragment_to_detailedProductFragment,bundle)


            //fm.beginTransaction().remove(this).replace(com.example.mapd726_groupproject_team3_agriapp.R.id.bottomSheetFragment, newFrame).addToBackStack(null).commit()
           // fm.beginTransaction().remove(fm.fragments.last()).add(R.id.fragmentContainer,newFrame).commit()

         //   fragmentManager.beginTransaction().remove(fragmentManager.fragments.last()).add(R.id.fragmentContainer,SearchFragment()).addToBackStack(null).commit()

        }

        binding.addToCart.setOnClickListener {


            val newFrame: Fragment = CartFragment()
            val quantity = 1
            val data = CartModel(product!!.productName,product.productCoverImg,product.productCategory,product.productSubCategory,product.productId!!,product.productPrice,product.discountRate,product.onSale,product.productSpecialPrice,product.productScale,quantity,product.productSpecialPrice!!.toDouble())
            viewModel.insertCartProducts(data)
            Snackbar.make(requireView().rootView,"Product Added to Cart Successfully !!",Snackbar.LENGTH_SHORT).show()
            dismissAllowingStateLoss()

           //fm.beginTransaction().remove(this).replace(com.example.mapd726_groupproject_team3_agriapp.R.id.fragmentContainer, newFrame).addToBackStack(null).commit()
        }



        return binding.root
    }

    private fun setBottomSheetData(product: ProductModel?) {


        Glide.with(requireContext()).load(product!!.productCoverImg).centerCrop().into(binding.thumbImage)
        binding.productTitle.text = product!!.productName
        binding.salePrice.text = "$ "+ product.productSpecialPrice
           //requireArguments().getString("product").toString()

        binding.weightTxt.text =product.productScale

        var normalPrice = product.productPrice

        if(normalPrice!!.length>1) {


            var ss = SpannableString(normalPrice)
            ss.setSpan(StrikethroughSpan(),
                0,
                normalPrice.lastIndex,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            binding.normalPrice.text = ss
        }else
        {
            binding.normalPrice.visibility = View.INVISIBLE

        }
    }


}