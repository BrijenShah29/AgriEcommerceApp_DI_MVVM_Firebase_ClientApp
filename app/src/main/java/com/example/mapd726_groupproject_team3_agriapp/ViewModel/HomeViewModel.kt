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

//INSERTING ALL PRODUCTS INTO ROOM DB AT INITIALIZATION
    init {
            productRepository.insertAllProductsIntoDB("Products")



    }



        // GETTING DATA OF SLIDER FROM REPOSITORY

        private var _sliderImages = MutableLiveData<ArrayList<Slider>>()

        val sliderImages: LiveData<ArrayList<Slider>>
        get() = _sliderImages

        fun getSliderImages(collectionPath: String, imageList: ArrayList<Slider>) {

            viewModelScope.launch {
                _sliderImages = firebaseRepository.getSliderImages(collectionPath,
                    imageList) as MutableLiveData<ArrayList<Slider>>
            }
        }


        // GETTING ALL CATEGORIES

        private var _categories = MutableLiveData<ArrayList<CategoryModel>>()

        val categories: LiveData<ArrayList<CategoryModel>>
        get() = _categories

        fun getCategories(collectionPath: String, categoryList: ArrayList<CategoryModel>) {

            viewModelScope.launch {
                _categories = firebaseRepository.getCategoriesFromFirebase(collectionPath,
                    categoryList) as MutableLiveData<ArrayList<CategoryModel>>
            }
        }

        //GET SUB-CATEGORIES FOR VEGETABLE SEEDS CATEGORY

        private var _vegetableSubCategories = MutableLiveData<ArrayList<SubCategoryModel>>()

        val vegetableSubCategories: LiveData<ArrayList<SubCategoryModel>>
        get() = _vegetableSubCategories
        fun getVegetableSubCategories(
            collectionPath: String,
            field: String,
            productCategory: String,
            subCategoryList: ArrayList<SubCategoryModel>
        ) {
            viewModelScope.launch {
                _vegetableSubCategories = firebaseRepository.getSubCategoriesFromFirebase(
                    collectionPath,
                    field,
                    productCategory,
                    subCategoryList) as MutableLiveData<ArrayList<SubCategoryModel>>

            }
        }

// GETTING SUB-CATEGORY FOR FRUIT SEEDS CATEGORY

        private var _fruitSubCategories = MutableLiveData<ArrayList<SubCategoryModel>>()

        val fruitSubCategories: LiveData<ArrayList<SubCategoryModel>>
        get() = _fruitSubCategories
        fun getFruitSubCategories(
            collectionPath: String,
            field: String,
            productCategory: String,
            subCategoryList: ArrayList<SubCategoryModel>
        ) {
            viewModelScope.launch {
                _fruitSubCategories =
                    firebaseRepository.getSubCategoriesFromFirebase(collectionPath,
                        field,
                        productCategory,
                        subCategoryList) as MutableLiveData<ArrayList<SubCategoryModel>>
            }
        }

// GETTING SUB-CATEGORY FOR CROP PROTECTION CATEGORY

        private var _cropProtectionSubCategories = MutableLiveData<ArrayList<SubCategoryModel>>()

        val cropProtectionSubCategories: LiveData<ArrayList<SubCategoryModel>>
        get() = _cropProtectionSubCategories
        fun getCropProtectionSubCategories(
            collectionPath: String,
            field: String,
            productCategory: String,
            subCategoryList: ArrayList<SubCategoryModel>
        ) {
            viewModelScope.launch {
                _cropProtectionSubCategories = firebaseRepository.getSubCategoriesFromFirebase(
                    collectionPath,
                    field,
                    productCategory,
                    subCategoryList) as MutableLiveData<ArrayList<SubCategoryModel>>
            }
        }


        // GETTING SUB-CATEGORY FOR CROP NUTRITION CATEGORY

        private var _cropNutritionSubCategories = MutableLiveData<ArrayList<SubCategoryModel>>()

        val cropNutritionSubCategories: LiveData<ArrayList<SubCategoryModel>>
        get() = _cropNutritionSubCategories
        fun getCropNutritionSubCategories(
            collectionPath: String,
            field: String,
            productCategory: String,
            subCategoryList: ArrayList<SubCategoryModel>
        ) {
            viewModelScope.launch{
                _cropNutritionSubCategories = firebaseRepository.getSubCategoriesFromFirebase(
                    collectionPath,
                    field,
                    productCategory,
                    subCategoryList) as MutableLiveData<ArrayList<SubCategoryModel>>
            }
        }


        // GETTING PRODUCTS OF ALL VEGETABLE SEEDS

        private var _vegetableProducts = MutableLiveData<ArrayList<ProductModel>>()

        val vegetableProducts: LiveData<ArrayList<ProductModel>>
            get() = _vegetableProducts

        fun getVegetableProducts(productList: ArrayList<ProductModel>) {
            viewModelScope.launch {

           // vegetableProducts = productRepository.getProductsByCategory("Vegetable Seeds").asLiveData()


                _vegetableProducts = firebaseRepository.getProductsFromFirebase("Products", "productCategory", "Vegetable Seeds", productList) as MutableLiveData<ArrayList<ProductModel>>
            }
        }

        // GETTING PRODUCTS OF ALL FRUIT SEEDS

        private var _fruitProducts = MutableLiveData<ArrayList<ProductModel>>()
        val fruitProducts: LiveData<ArrayList<ProductModel>>
        get() = _fruitProducts

        fun getFruitProducts(
            collectionPath: String,
            field: String,
            productCategory: String,
            productList: ArrayList<ProductModel>
        ) {
            viewModelScope.launch{
                _fruitProducts = firebaseRepository.getProductsFromFirebase(collectionPath,
                    field,
                    productCategory,
                    productList) as MutableLiveData<ArrayList<ProductModel>>
            }
        }

        // GETTING PRODUCTS OF HERBICIDES

        private var _herbicidesProducts = MutableLiveData<ArrayList<ProductModel>>()

        val herbicidesProducts: LiveData<ArrayList<ProductModel>>
        get() = _herbicidesProducts
        fun getHerbicideProducts(
            collectionPath: String,
            field: String,
            productCategory: String,
            productList: ArrayList<ProductModel>
        ) {
            viewModelScope.launch {
                _herbicidesProducts = firebaseRepository.getProductsFromFirebase(collectionPath,
                    field,
                    productCategory,
                    productList) as MutableLiveData<ArrayList<ProductModel>>
            }
        }


        // GETTING PRODUCTS OF FUNGICIDES

        private var _fungicidesProducts = MutableLiveData<ArrayList<ProductModel>>()

        val fungicidesProducts: LiveData<ArrayList<ProductModel>>
        get() = _fungicidesProducts
        fun getFungicidesProducts(
            collectionPath: String,
            field: String,
            productCategory: String,
            productList: ArrayList<ProductModel>
        ) {
            viewModelScope.launch {
                _fungicidesProducts = firebaseRepository.getProductsFromFirebase(collectionPath,
                    field,
                    productCategory,
                    productList) as MutableLiveData<ArrayList<ProductModel>>
            }
        }

        // GETTING PRODUCTS PESTICIDES

        private var _pesticidesProducts = MutableLiveData<ArrayList<ProductModel>>()

        val pesticidesProducts: LiveData<ArrayList<ProductModel>>
        get() = _pesticidesProducts
        fun getPesticidesProducts(
            collectionPath: String,
            field: String,
            productCategory: String,
            productList: ArrayList<ProductModel>
        ) {
            viewModelScope.launch{
                _pesticidesProducts = firebaseRepository.getProductsFromFirebase(collectionPath,
                    field,
                    productCategory,
                    productList) as MutableLiveData<ArrayList<ProductModel>>
            }
        }

        // GETTING PRODUCTS FERTILIZERS

        private var _fertilizersProducts = MutableLiveData<ArrayList<ProductModel>>()

        val fertilizersProducts: LiveData<ArrayList<ProductModel>>
        get() = _pesticidesProducts
        fun getFertilizersProducts(
            collectionPath: String,
            field: String,
            productCategory: String,
            productList: ArrayList<ProductModel>
        ) {
            viewModelScope.launch {
                _fertilizersProducts = firebaseRepository.getProductsFromFirebase(collectionPath,
                    field,
                    productCategory,
                    productList) as MutableLiveData<ArrayList<ProductModel>>
            }
        }

        // GETTING PRODUCTS GROWTH PROMOTERS

        private var _growthPromotersProducts = MutableLiveData<ArrayList<ProductModel>>()

        val growthPromotersProducts: LiveData<ArrayList<ProductModel>>
        get() = _growthPromotersProducts
        fun getGrowthPromotersProducts(
            collectionPath: String,
            field: String,
            productCategory: String,
            productList: ArrayList<ProductModel>
        ) {
            viewModelScope.launch {
                _growthPromotersProducts =
                    firebaseRepository.getProductsFromFirebase(collectionPath,
                        field,
                        productCategory,
                        productList) as MutableLiveData<ArrayList<ProductModel>>
            }
        }

        // GETTING PRODUCTS TOOLS & EQUIPMENTS

        private var _toolsEquipmentsProducts = MutableLiveData<ArrayList<ProductModel>>()

        val toolEquipmentsProducts: LiveData<ArrayList<ProductModel>>
        get() = _toolsEquipmentsProducts
        fun getToolEquipmentsProducts(
            collectionPath: String,
            field: String,
            productCategory: String,
            productList: ArrayList<ProductModel>
        ) {
            viewModelScope.launch {
                _toolsEquipmentsProducts =
                    firebaseRepository.getProductsFromFirebase(collectionPath,
                        field,
                        productCategory,
                        productList) as MutableLiveData<ArrayList<ProductModel>>
            }
        }

        // GETTING PRODUCTS POTS & PLANTERS

        private var _potsPlantersProducts = MutableLiveData<ArrayList<ProductModel>>()

        val potsPlantersProducts: LiveData<ArrayList<ProductModel>>
        get() = _potsPlantersProducts

        fun getPotsPlantersProducts(
            collectionPath: String,
            field: String,
            productCategory: String,
            productList: ArrayList<ProductModel>
        ) {
            viewModelScope.launch{
                _potsPlantersProducts = firebaseRepository.getProductsFromFirebase(collectionPath,
                    field,
                    productCategory,
                    productList) as MutableLiveData<ArrayList<ProductModel>>
            }
        }


    //GETTING PRODUCTS ON BASIS OF SELECTION OF SUB-CATEGORY (SUB-CATEGORY FRAGMENT)

    private var _selectedSubCategoryProducts  = MutableLiveData<ArrayList<ProductModel>>()
    val selectedSubCategoryProducts  : LiveData<ArrayList<ProductModel>>
        get() = _selectedSubCategoryProducts

    fun getSelectedSubCategoryProducts(collectionPath : String,field : String, productCategory : String  ,productList : ArrayList<ProductModel>){
        viewModelScope.launch {
            _selectedSubCategoryProducts = firebaseRepository.getProductsFromFirebase(collectionPath,field,productCategory,productList) as MutableLiveData<ArrayList<ProductModel>>
            // firebaseRepository.getProductsFromFirebase(collectionPath, field, productCategory,productList)
        }

    }





}