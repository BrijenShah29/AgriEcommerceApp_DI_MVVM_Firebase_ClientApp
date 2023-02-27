package com.example.mapd726_groupproject_team3_agriapp.ViewModel

import androidx.lifecycle.*
import com.example.mapd726_groupproject_team3_agriapp.DataModels.CartModel
import com.example.mapd726_groupproject_team3_agriapp.DataModels.ProductModel
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



// TO CHECK WEATHER THE PRODUCT EXISTS OR NOT
   var selectedProduct : CartModel? = null
    fun isExists(id : String){
        viewModelScope.launch(Dispatchers.IO) {
            selectedProduct = productRepository.isExist(id)
         }

    }

    // TO GET ALL THE PRODUCTS FROM FIREBASE

    private var _firebaseProducts = productRepository.getAllProductsForDB("Products")

    val firebaseProducts : LiveData<List<ProductModel>>
        get() = _firebaseProducts

    // TO ADD ALL THE PRODUCTS INTO PRODUCT ROOM

    fun insertProducts(productList : List<ProductModel>){

        viewModelScope.launch(Dispatchers.IO) {
            delay(2000)
            productRepository.insertProducts(productList!!)
        }
    }


    // TO ADD THE PRODUCT INTO CART ROOM

    fun insertCartProducts(cartModel: CartModel) {

        viewModelScope.launch {
            productRepository.insertCartProducts(cartModel)
        }
    }

    //TO GET ALL THE CART PRODUCTS

    private var _cartProduct = MutableLiveData<List<CartModel>>()

    val cartProducts =  productRepository.getCartProducts().asLiveData()

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


}
