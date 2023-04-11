package com.example.mapd726_groupproject_team3_agriapp.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.mapd726_groupproject_team3_agriapp.Adapter.HomePageAdapters.CategoryAdapter
import com.example.mapd726_groupproject_team3_agriapp.Adapter.HomePageAdapters.ImageSliderAdapter
import com.example.mapd726_groupproject_team3_agriapp.Adapter.HomePageAdapters.ProductAdapter
import com.example.mapd726_groupproject_team3_agriapp.Adapter.HomePageAdapters.HomePageSubCategoryAdapters.SubCategoryDisplayAdapter
import com.example.mapd726_groupproject_team3_agriapp.Adapter.RecentlyVisitedProductsAdapter.RecentlyVisitedAdapter
import com.example.mapd726_groupproject_team3_agriapp.DataModels.*
import com.example.mapd726_groupproject_team3_agriapp.Utils.Constant
import com.example.mapd726_groupproject_team3_agriapp.Utils.UserManager
import com.example.mapd726_groupproject_team3_agriapp.ViewModel.HomeViewModel
import com.example.mapd726_groupproject_team3_agriapp.ViewModel.ProductsViewModel
import com.example.mapd726_groupproject_team3_agriapp.ViewModel.UserViewModel
import com.example.mapd726_groupproject_team3_agriapp.databinding.FragmentHomeBinding
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding : FragmentHomeBinding
    private var categoryList = ArrayList<CategoryModel>()
    @Inject
    lateinit var userManager : UserManager

//    private var productList = ArrayList<ProductModel>()
//    private var vegetableProductList = ArrayList<ProductModel>()
//    private var fruitProductList = ArrayList<ProductModel>()
//    private var herbicidesProductList = ArrayList<ProductModel>()
//    private var pesticidesProductList = ArrayList<ProductModel>()
//    private var fungicidesProductList = ArrayList<ProductModel>()
//    private var fertilizerProductList = ArrayList<ProductModel>()
//    private var growthPromoterProductList = ArrayList<ProductModel>()
//    private var toolsProductList = ArrayList<ProductModel>()
//    private var potsAndPlantersProductList = ArrayList<ProductModel>()


    private var imageList = ArrayList<Slider>()
//    private var vegetableSubCategoryList = ArrayList<SubCategoryModel>()
//    private var fruitSubCategoryList = ArrayList<SubCategoryModel>()
//    private var cropProtectionSubCategoryList = ArrayList<SubCategoryModel>()
//    private var cropNutritionSubCategoryList = ArrayList<SubCategoryModel>()
//



    private val viewModel by viewModels<HomeViewModel>()
    private val viewModelProducts by viewModels<ProductsViewModel>()
    private val userViewModel by viewModels<UserViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentHomeBinding.inflate(layoutInflater)


        // GETTING ALL PRODUCTS FROM FIREBASE TO ROOM
        // INSERTING ALL PRODUCTS INTO ROOM
       // viewModelProducts.firebaseProducts.value?.let { viewModelProducts.insertProducts(it) }
//       viewModelProducts.firebaseProducts.observe(viewLifecycleOwner, Observer {
//           viewModelProducts.insertProducts(it)
//       })

       // viewModelProducts.firebaseSubCategories.value?.let { viewModelProducts.insertSubCategories(it) }
//      viewModelProducts.firebaseSubCategories.observe(viewLifecycleOwner, Observer {
//            viewModelProducts.insertSubCategories(it)
//      })






// GETTING SLIDER IMAGES
        val sliderAdapter  = binding.imageSlider
        viewModel.getSliderImages("Slider",imageList)
        viewModel.sliderImages.observe(viewLifecycleOwner, Observer {
            sliderAdapter.setSliderAdapter(ImageSliderAdapter(requireContext(),it))
            sliderAdapter.setIndicatorAnimation(IndicatorAnimationType.WORM)
        })

// GETTING CATEGORIES FROM FIREBASE

        val categoryAdapter = CategoryAdapter(requireContext(),userManager)
        binding.categoryRecycler.adapter = categoryAdapter
     //   binding.categoryRecycler.isNestedScrollingEnabled = false
        binding.categoryRecycler.setHasFixedSize(true)
        viewModel.getCategories("Categories",categoryList)
        viewModel.categories.observe(viewLifecycleOwner, Observer {
            categoryAdapter.submitList(it)
        })



//GETTING SUB-CATEGORIES OF VEGETABLE SEEDS FROM ROOM DB

        val vegetableSubCategoriesAdapter = SubCategoryDisplayAdapter(requireContext())
        binding.subCategoryVegetableRecycler.adapter = vegetableSubCategoriesAdapter
     //  binding.subCategoryVegetableRecycler.isNestedScrollingEnabled = false
        binding.subCategoryVegetableRecycler.setHasFixedSize(true)
        viewModel.vegetableSubCategories.observe(viewLifecycleOwner, Observer {
            vegetableSubCategoriesAdapter.submitList(it)
        })


// GETTING SUB-CATEGORIES OF FRUIT SEEDS

        val fruitSubCategoriesAdapter = SubCategoryDisplayAdapter(requireContext())
        binding.subCategoryFruitRecycler.adapter = fruitSubCategoriesAdapter
    //   binding.subCategoryFruitRecycler.isNestedScrollingEnabled = false
        binding.subCategoryFruitRecycler.setHasFixedSize(true)
        viewModel.fruitSubCategories.observe(viewLifecycleOwner, Observer {
            fruitSubCategoriesAdapter.submitList(it)
        })



// GETTING SUB-CATEGORIES OF CROP PROTECTION

        val cropProtectionSubCategoriesAdapter = SubCategoryDisplayAdapter(requireContext())
        binding.subCategoryCropProtectionRecycler.adapter = cropProtectionSubCategoriesAdapter
    //   binding.subCategoryCropProtectionRecycler.isNestedScrollingEnabled = false
        binding.subCategoryCropProtectionRecycler.setHasFixedSize(true)
        viewModel.cropProtectionSubCategories.observe(viewLifecycleOwner, Observer {
            cropProtectionSubCategoriesAdapter.submitList(it)
        })

// GETTING SUB-CATEGORIES OF CROP NUTRITION

        val cropNutritionSubCategoriesAdapter = SubCategoryDisplayAdapter(requireContext())
        binding.subCategoryCropNutritionRecycler.adapter = cropNutritionSubCategoriesAdapter
    //   binding.subCategoryCropNutritionRecycler.isNestedScrollingEnabled = false
        binding.subCategoryCropNutritionRecycler.setHasFixedSize(true)
        viewModel.cropNutritionSubCategories.observe(viewLifecycleOwner, Observer {
            cropNutritionSubCategoriesAdapter.submitList(it)
        })




//  GETTING ALL VEGETABLE SEEDS PRODUCTS
        val  vegetableProductsAdapter =  ProductAdapter(requireContext())
        binding.VegetableProductsRecycler.adapter = vegetableProductsAdapter
    //    binding.VegetableProductsRecycler.isNestedScrollingEnabled = false
        binding.VegetableProductsRecycler.setHasFixedSize(true)
        viewModel.vegetableProducts.observe(viewLifecycleOwner, Observer {
            vegetableProductsAdapter.submitList(it)
        })



// GETTING ALL FRUIT SEEDS PRODUCTS

        val fruitProductsAdapter = ProductAdapter(requireContext())
        binding.FruitProductsRecycler.adapter = fruitProductsAdapter
     //   binding.FruitProductsRecycler.isNestedScrollingEnabled = false
        binding.FruitProductsRecycler.setHasFixedSize(true)
        viewModel.fruitProducts.observe(viewLifecycleOwner, Observer {
            fruitProductsAdapter.submitList(it)
        })


// GETTING ALL HERBICIDES CROP PROTECTOR PRODUCTS

        val herbicideProductsAdapter = ProductAdapter(requireContext())
        binding.CropProtectionHerbicidesProductsRecycler.adapter = herbicideProductsAdapter
     //   binding.CropProtectionHerbicidesProductsRecycler.isNestedScrollingEnabled = false
        binding.CropProtectionHerbicidesProductsRecycler.setHasFixedSize(true)
       // viewModel.getHerbicideProducts("Products","productCategory","Herbicides", herbicidesProductList)
        viewModel.herbicidesProducts.observe(viewLifecycleOwner, Observer {
            herbicideProductsAdapter.submitList(it)
        })


// GETTING ALL FUNGICIDES CROP PROTECTOR PRODUCTS

        val fungicidesProductsAdapter = ProductAdapter(requireContext())
        binding.CropProtectionFungicidesProductsRecycler.adapter = fungicidesProductsAdapter
    //    binding.CropProtectionFungicidesProductsRecycler.isNestedScrollingEnabled = false
        binding.CropProtectionFungicidesProductsRecycler.setHasFixedSize(true)

        viewModel.fungicidesProducts.observe(viewLifecycleOwner, Observer {
            fungicidesProductsAdapter.submitList(it)
        })

// GETTING ALL PESTICIDES CROP PROTECTOR PRODUCTS

        val pesticidesProductsAdapter = ProductAdapter(requireContext())
        binding.CropProtectionPesticidesProductsRecycler.adapter = pesticidesProductsAdapter
   //     binding.CropProtectionPesticidesProductsRecycler.isNestedScrollingEnabled = false
        binding.CropProtectionPesticidesProductsRecycler.setHasFixedSize(true)
        viewModel.pesticidesProducts.observe(viewLifecycleOwner, Observer {
            pesticidesProductsAdapter.submitList(it)
        })

// GETTING ALL FERTILIZERS CROP NUTRITION PRODUCTS

        val fertilizerProductsAdapter = ProductAdapter(requireContext())
        binding.CropNutritionFertilizersProductsRecycler.adapter = fertilizerProductsAdapter
     //   binding.CropProtectionPesticidesProductsRecycler.isNestedScrollingEnabled = false
        binding.CropNutritionFertilizersProductsRecycler.setHasFixedSize(true)
        viewModel.fertilizersProducts.observe(viewLifecycleOwner, Observer {
            fertilizerProductsAdapter.submitList(it)
        })

// GETTING ALL GROWTH PROMOTER NUTRITION PRODUCTS

        val growthPromoterAdapter = ProductAdapter(requireContext())
        binding.CropNutritionGrowthPromotersProductsRecycler.adapter = growthPromoterAdapter
     //   binding.CropNutritionGrowthPromotersProductsRecycler.isNestedScrollingEnabled = false
        binding.CropNutritionGrowthPromotersProductsRecycler.setHasFixedSize(true)
        viewModel.growthPromotersProducts.observe(viewLifecycleOwner, Observer {
            growthPromoterAdapter.submitList(it)
        })

// GETTING ALL TOOLS AND EQUIPMENTS PRODUCTS

        val toolsAdapter = ProductAdapter(requireContext())
        binding.ToolsNEquipmentRecycler.adapter = toolsAdapter
      //  binding.ToolsNEquipmentRecycler.isNestedScrollingEnabled = false
        binding.ToolsNEquipmentRecycler.setHasFixedSize(true)
        viewModel.toolEquipmentsProducts.observe(viewLifecycleOwner, Observer {
            toolsAdapter.submitList(it)
        })

        // GETTING ALL POTS AND PLANTERS PRODUCTS

        val potsNPlantersAdapter = ProductAdapter(requireContext())
        binding.PotsNPlantersRecycler.adapter = potsNPlantersAdapter
       // binding.PotsNPlantersRecycler.isNestedScrollingEnabled = false
        binding.PotsNPlantersRecycler.setHasFixedSize(true)
        viewModel.potsPlantersProducts.observe(viewLifecycleOwner, Observer {
            potsNPlantersAdapter.submitList(it)
        })

    // GETTING ALL RECENTLY VISITED PRODUCTS
        val recentlyVisitedProductAdapter = RecentlyVisitedAdapter(requireContext(),viewModelProducts)
        binding.previousVisitedProductsRecycler.adapter = recentlyVisitedProductAdapter
        binding.previousVisitedProductsRecycler.setHasFixedSize(true)
        viewModelProducts.recentlyVisitedProducts.observe(viewLifecycleOwner,Observer{
            recentlyVisitedProductAdapter.submitList(it)
            if(!it.isNullOrEmpty())
            {
                viewModelProducts.removeOldRecentlyVisitedData()
            }
        })

        // GETTING ALL WISHLIST PRODUCTS
        if(Constant.wishlistProductId.isNotEmpty())
        {
            for(data in Constant.wishlistProductId)
            {
                // fetch individual products from products Table and parse into Wishlists
                viewModelProducts.getSingleWishlistProduct(data)
                viewModelProducts.wishlistSingleProduct.observe(viewLifecycleOwner, Observer { products->
                    val wishlistData = WishlistModel(
                        products.productName,
                        products.productDescription,
                        products.productCoverImg,
                        products.productCategory,
                        products.productSubCategory!!,
                        products.productId,
                        products.productPrice,
                        products.discountRate,
                        products.stock!!,
                        products.onSale!!,
                        products.productSpecialPrice,
                        products.productScale,
                        products.productImages
                    )
                    Constant.wishlist.add(wishlistData)
                    // add these products into RoomDB
                    userViewModel.addProductIntoWishlist(wishlistData)
                })



            }
        }
        return binding.root
    }



}