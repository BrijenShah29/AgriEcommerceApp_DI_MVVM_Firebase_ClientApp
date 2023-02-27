package com.example.mapd726_groupproject_team3_agriapp.Repository

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import com.example.mapd726_groupproject_team3_agriapp.DataModels.CartModel
import com.example.mapd726_groupproject_team3_agriapp.DataModels.CategoryModel
import com.example.mapd726_groupproject_team3_agriapp.DataModels.ProductModel
import com.example.mapd726_groupproject_team3_agriapp.RoomDB.AgroDao
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProductRepository @Inject constructor( val db : FirebaseFirestore, val agroDao: AgroDao) {



    fun getAllProductsForDB(collectionPath: String): MutableLiveData<List<ProductModel>> {
        var list = MutableLiveData<List<ProductModel>>()
        val productList = ArrayList<ProductModel>()
            db.collection(collectionPath).get().addOnSuccessListener {

                for (doc in it.documents) {
                    val firebaseData = doc.toObject(ProductModel::class.java)
                    productList?.add(firebaseData!!)
                }
                list.value = productList
            }
        return list!!
    }

    suspend fun insertProducts(productModel: List<ProductModel>) = agroDao.insertProducts(productModel)

     fun getAllProducts() = agroDao.getProducts()

    fun getProductsBySubCategory(productSubCategory: String) = agroDao.getProductsBySubCategory(productSubCategory)

    fun getProductsByCategory(productCategory: String) = agroDao.getProductsByCategory(productCategory)


    // INSERTING CART PRODUCTS INTO ROOM
     suspend fun insertCartProducts(cartModel: CartModel) = agroDao.insertCartProducts(cartModel)


    // GETTING CART PRODUCTS FROM ROOM
    fun getCartProducts()  = agroDao.getCartProducts()


    // DELETING CART PRODUCTS FROM ROOM
    suspend fun deleteProduct(cartModel: CartModel) = agroDao.deleteProduct(cartModel)

    // FINDING IF PRODUCT ALREADY EXISTS

    suspend fun isExist(id : String) = agroDao.isExist(id)

    // GETTING PRODUCTS FROM SEARCH QUERY
    fun searchDatabase(searchQuery:String) = agroDao.searchDatabase(searchQuery)



}