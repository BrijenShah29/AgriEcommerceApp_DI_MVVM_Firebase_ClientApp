package com.example.mapd726_groupproject_team3_agriapp.ViewModel

import androidx.lifecycle.*
import com.example.mapd726_groupproject_team3_agriapp.DataModels.*
import com.example.mapd726_groupproject_team3_agriapp.Repository.FirebaseRepository
import com.example.mapd726_groupproject_team3_agriapp.Repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(private val firebaseRepository: FirebaseRepository,  private val productRepository: ProductRepository): ViewModel() {


    //GETTING PRODUCTS ON BASIS OF SELECTION OF SUB-CATEGORY (SUB-CATEGORY FRAGMENT)

    //GETTING SINGLE PRODUCT FROM ROOM

    var selectedSingleProduct : ProductModel? = null

    fun getSingleProduct(id: String) {
        viewModelScope.launch {
            selectedSingleProduct = productRepository.getSingleProduct(id)
        }

    }


// TO CHECK WEATHER THE PRODUCT EXISTS OR NOT
   var selectedProduct : CartModel? = null
    fun isExists(id : String)
    {
        viewModelScope.launch(Dispatchers.IO) {
            selectedProduct = productRepository.isExist(id)
         }
    }


    // TO ADD THE PRODUCT INTO CART ROOM

    fun insertCartProducts(cartModel: CartModel) {
        viewModelScope.launch {
            productRepository.insertCartProducts(cartModel)
        }
    }

    //TO GET ALL THE CART PRODUCTS

   // private var _cartProduct = MutableLiveData<List<CartModel>>()
    val cartProducts =  productRepository.getCartProducts().asLiveData()



    // DELETING PRODUCT FROM CART
    fun deleteProduct(cartModel : CartModel) {
        viewModelScope.launch(Dispatchers.IO) {
            delay(1000)
            productRepository.deleteProduct(cartModel)
        }
    }

    // SEARCHING DATA AND RETRIEVING FROM ROOM
   // private var _searchedProducts = MutableLiveData<List<ProductModel>>()

    lateinit var searchedProducts : LiveData<List<ProductModel>>
    fun searchDatabase(searchQuery:String){
        searchedProducts = productRepository.searchDatabase(searchQuery).asLiveData()

    }

    // UPDATING THE CART PRODUCTS

    fun updateCart(quantity : Int,totalAmount : Double, productId : String){
        viewModelScope.launch(Dispatchers.IO) {
            productRepository.updateCart(quantity,totalAmount,productId)
        }
    }


    // fun insertSubCategories(list: List<SubCategoryModel>) {

    //  viewModelScope.launch { if(list.isNotEmpty()){ delay(1000) productRepository.insertSubCategories(list)}}}



    // TO GET ALL THE PRODUCTS FROM FIREBASE FOR ROOM DB

    //private var _firebaseProducts = productRepository.getAllProductsForDB("Products")
    // lateinit var firebaseProducts : LiveData<List<ProductModel>>
    // get() = productRepository.productList



    // TO ADD ALL THE PRODUCTS INTO PRODUCT ROOM

    fun insertProducts(list: List<ProductModel>) {

        viewModelScope.launch {
            if(list.isNotEmpty() && list.size > 10) {
                delay(1000)
                productRepository.insertProducts(list)
            }
        }
    }

    // REMOVING RECENTLY VISITED PRODUCTS AFTER EXCEEDING LIMIT OF 10
    fun removeOldRecentlyVisitedData(){
        viewModelScope.launch(Dispatchers.IO) {

            productRepository.removeOldRecentlyVisitedData()
        }

    }


    // ADDING SUCCESSFUL ORDERS INTO DAO




    // EMPTY THE CART AFTER SUCCESSFUL PAYMENT FROM CUSTOMER
    fun emptyTheCart()
    {
        viewModelScope.launch(Dispatchers.IO) {
            productRepository.emptyTheCart()
        }
    }


    //TO GET ALL THE RECENTLY VISITED PRODUCTS

    lateinit var recentlyVisitedProducts: LiveData<List<RecentlyVisitedModel>>


    // TO ADD THE PRODUCT INTO RECENTLY VISITED PRODUCTS ROOM TABLE

    fun insertRecentlyVisitedProducts(recentlyVisitedModel: RecentlyVisitedModel) {
        viewModelScope.launch(Dispatchers.IO) {
            productRepository.insertRecentlyVisitedProducts(recentlyVisitedModel)
        }
    }

    init {
        viewModelScope.launch {
            recentlyVisitedProducts =  productRepository.getRecentlyVisitedProducts().asLiveData()
        }

    }



}
