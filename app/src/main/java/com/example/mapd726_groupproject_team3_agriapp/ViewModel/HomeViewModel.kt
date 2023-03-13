package com.example.mapd726_groupproject_team3_agriapp.ViewModel

import androidx.lifecycle.*
import com.example.mapd726_groupproject_team3_agriapp.DataModels.CategoryModel
import com.example.mapd726_groupproject_team3_agriapp.DataModels.ProductModel
import com.example.mapd726_groupproject_team3_agriapp.DataModels.Slider
import com.example.mapd726_groupproject_team3_agriapp.DataModels.SubCategoryModel
import com.example.mapd726_groupproject_team3_agriapp.Repository.FirebaseRepository
import com.example.mapd726_groupproject_team3_agriapp.Repository.ProductRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val firebaseRepository: FirebaseRepository, private val productRepository: ProductRepository) : ViewModel()
{

//SIGNOUT USER FROM CURRENT LOGIN
fun signOut() {
    firebaseRepository.signOut()
}

//INSERTING ALL PRODUCTS INTO ROOM DB AT INITIALIZATION


        // GETTING DATA OF SLIDER FROM REPOSITORY

        lateinit var sliderImages: LiveData<ArrayList<Slider>>
        fun getSliderImages(collectionPath: String, imageList: ArrayList<Slider>) {
            viewModelScope.launch { sliderImages = firebaseRepository.getSliderImages(collectionPath,imageList) }
        }


        // GETTING ALL CATEGORIES

        lateinit var categories: LiveData<ArrayList<CategoryModel>>
        fun getCategories(collectionPath: String, categoryList: ArrayList<CategoryModel>) {
                categories = firebaseRepository.getCategoriesFromFirebase(collectionPath, categoryList)
        }



    //GETTING PRODUCTS ON BASIS OF SELECTION OF SUB-CATEGORY (SUB-CATEGORY FRAGMENT) FROM ROOM


   lateinit var selectedCategoryProducts  : LiveData<List<ProductModel>>


    fun getSelectedCategoryProducts( productCategory : String){
        viewModelScope.launch {
            selectedCategoryProducts = productRepository.getProductsByCategory(productCategory).asLiveData()
        }
    }

    lateinit var selectedSubCategoryProducts  : LiveData<List<ProductModel>>

    fun getSelectedSubCategoryProducts( productSubCategory : String){
        viewModelScope.launch {
            selectedSubCategoryProducts = productRepository.getProductsBySubCategory(productSubCategory).asLiveData()
        }
    }

// GETTING PRODUCTS OF ALL VEGETABLE SEEDS PRODUCTS FROM ROOM DB

    lateinit var vegetableProducts: LiveData<List<ProductModel>>

    // GETTING PRODUCTS OF ALL FRUIT SEEDS PRODUCTS FROM ROOM DB

    lateinit var fruitProducts: LiveData<List<ProductModel>>

    // GETTING PRODUCTS OF HERBICIDES PRODUCTS FROM ROOM DB

   lateinit var herbicidesProducts: LiveData<List<ProductModel>>

    // GETTING PRODUCTS OF FUNGICIDES PRODUCTS FROM ROOM DB

   lateinit var fungicidesProducts: LiveData<List<ProductModel>>

    // GETTING PRODUCTS PESTICIDES PRODUCTS FROM ROOM DB

    lateinit var  pesticidesProducts: LiveData<List<ProductModel>>

    // GETTING PRODUCTS FERTILIZERS PRODUCTS FROM ROOM DB

    lateinit var fertilizersProducts: LiveData<List<ProductModel>>

    // GETTING PRODUCTS GROWTH PROMOTERS PRODUCTS FROM ROOM DB

    lateinit var  growthPromotersProducts: LiveData<List<ProductModel>>

    // GETTING PRODUCTS TOOLS & EQUIPMENTS PRODUCTS FROM ROOM DB

    lateinit var toolEquipmentsProducts: LiveData<List<ProductModel>>

    // GETTING PRODUCTS POTS & PLANTERS PRODUCTS FROM ROOM DB

   lateinit var potsPlantersProducts: LiveData<List<ProductModel>>


//GET SUB-CATEGORIES FOR VEGETABLE SEEDS CATEGORY FROM ROOM DB

   lateinit var vegetableSubCategories: LiveData<List<SubCategoryModel>>

// GETTING SUB-CATEGORY FOR FRUIT SEEDS CATEGORY

    lateinit var fruitSubCategories: LiveData<List<SubCategoryModel>>

    // GETTING SUB-CATEGORY FOR CROP PROTECTION CATEGORY

   lateinit var cropProtectionSubCategories: LiveData<List<SubCategoryModel>>


    // GETTING SUB-CATEGORY FOR CROP NUTRITION CATEGORY

  lateinit var cropNutritionSubCategories: LiveData<List<SubCategoryModel>>


init {

    viewModelScope.launch{
        vegetableProducts = productRepository.getProductsByCategory("Vegetable Seeds").asLiveData()
        fruitProducts = productRepository.getProductsByCategory("Fruit Seeds").asLiveData()
        herbicidesProducts = productRepository.getProductsByCategory("Herbicides").asLiveData()
        fungicidesProducts = productRepository.getProductsByCategory("Fungicides").asLiveData()
        pesticidesProducts = productRepository.getProductsByCategory("Pesticides").asLiveData()
        fertilizersProducts =  productRepository.getProductsByCategory("Fertilizers").asLiveData()
        growthPromotersProducts = productRepository.getProductsByCategory("Plant Growth Promoters").asLiveData()
        toolEquipmentsProducts = productRepository.getProductsByCategory("Tools").asLiveData()
        potsPlantersProducts = productRepository.getProductsByCategory("Pots and Planters").asLiveData()
    }

    viewModelScope.launch {
        vegetableSubCategories  = productRepository.getSubCategoryByCategory("Vegetable Seeds").asLiveData()
        fruitSubCategories = productRepository.getSubCategoryByCategory("Fruit Seeds").asLiveData()
        cropProtectionSubCategories = productRepository.getSubCategoryByCategory("Crop Protection").asLiveData()
        cropNutritionSubCategories  = productRepository.getSubCategoryByCategory("Crop Nutrition").asLiveData()

    }

}

}