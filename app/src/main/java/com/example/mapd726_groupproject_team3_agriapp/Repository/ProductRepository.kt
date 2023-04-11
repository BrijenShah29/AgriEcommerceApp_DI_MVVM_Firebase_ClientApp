package com.example.mapd726_groupproject_team3_agriapp.Repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import com.example.mapd726_groupproject_team3_agriapp.DataModels.*
import com.example.mapd726_groupproject_team3_agriapp.RoomDB.AgroDao
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProductRepository @Inject constructor( val db : FirebaseFirestore, val agroDao: AgroDao) {


    //INSERTING PRODUCTS INTO ROOM
    suspend fun insertProducts(productModel: List<ProductModel>) = agroDao.insertProducts(productModel)


    //INSERTING SUB-CATEGORIES INTO ROOM
    suspend fun insertSubCategories(subCategoryModel : List<SubCategoryModel>) = agroDao.insertSubCategories(subCategoryModel)

    //GETTING SUB-CATEGORIES FROM ROOM DB
    fun getSubCategoryByCategory(category : String) = agroDao.getSubCategoryByCategory(category)

     fun getAllProducts() = agroDao.getProducts()


    // GETTING PRODUCTS BY SUB-CATEGORY
    fun getProductsBySubCategory(productSubCategory: String) = agroDao.getProductsBySubCategory(productSubCategory)

    //GETTING PRODUCTS BY CATEGORY
    fun getProductsByCategory(productCategory: String) = agroDao.getProductsByCategory(productCategory)


    // INSERTING CART PRODUCTS INTO ROOM
     suspend fun insertCartProducts(cartModel: CartModel) = agroDao.insertCartProducts(cartModel)

    // INSERTING RECENTLY VISITED PRODUCTS INTO ROOM
    suspend fun insertRecentlyVisitedProducts(recentlyVisitedModel: RecentlyVisitedModel) = agroDao.insertRecentlyVisitedProducts(recentlyVisitedModel)

    // INSERTING YOU MAY LIKE PRODUCTS
    suspend fun insertYouMayLikeProducts(youMayLikeModel: List<YouMayLikeModel>) = agroDao.insertYouMayLikeProducts(youMayLikeModel)

    // GETTING CART PRODUCTS FROM ROOM
    fun getCartProducts()  = agroDao.getCartProducts()

    // GETTING SINGLE PRODUCT IF EXISTS

    suspend fun getSingleProduct(id: String) = agroDao.getSingleProduct(id)

    //  GETTING RECENTLY VISITED PRODUCTS FROM ROOM

    fun getRecentlyVisitedProducts() = agroDao.getRecentlyVisitedProducts()

    // DELETING RECENTLY VISITED PRODUCTS PREVIOUS VISITS

    suspend fun removeOldRecentlyVisitedData() = agroDao.removeOldRecentlyVisitedData()

    // DELETING CART PRODUCTS FROM ROOM
    suspend fun deleteProduct(cartModel: CartModel) = agroDao.deleteProduct(cartModel)

    // FINDING IF PRODUCT ALREADY EXISTS

    suspend fun isExist(id : String) = agroDao.isExist(id)

    // GETTING PRODUCTS FROM SEARCH QUERY
    fun searchDatabase(searchQuery:String) = agroDao.searchDatabase(searchQuery)

    //UPDATING THE CART PRODUCTS FROM CART PAGE
    suspend fun updateCart(quantity : Int,totalAmount : Double , productId : String) = agroDao.updateCart(quantity,totalAmount,productId)

    // DELETE ALL CART ITEMS AFTER SUCCESSFUL PAYMENT FROM CUSTOMER
    suspend fun emptyTheCart() = agroDao.deleteProductsFromCart()
    fun getYouMayLikeProducts() = agroDao.getYouMayLikeProducts()

    suspend fun removeYouMayLikeProducts() = agroDao.removeYouMayLikeProducts()

}