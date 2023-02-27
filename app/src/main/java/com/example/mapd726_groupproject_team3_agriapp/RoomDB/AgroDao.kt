package com.example.mapd726_groupproject_team3_agriapp.RoomDB

import androidx.lifecycle.MutableLiveData
import androidx.room.*
import com.example.mapd726_groupproject_team3_agriapp.DataModels.CartModel
import com.example.mapd726_groupproject_team3_agriapp.DataModels.ProductModel
import kotlinx.coroutines.flow.Flow

@Dao
interface AgroDao {


    // TO INSERT ALL THE PRODUCTS INTO DATABASE
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProducts(productModel: List<ProductModel>)

    // TO INSERT PRODUCTS INTO CART
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCartProducts(cartModel: CartModel)


    // TO INSERT RECENTLY VISITED PRODUCTS
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecentlyVisitedProducts(productModel: ProductModel)

    // TO INSERT LIKED PRODUCTS
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWishlistProducts(productModel: ProductModel)




    @Query("SELECT * FROM products")
    fun getProducts() : Flow<List<ProductModel>>

    @Query("SELECT * FROM products ORDER BY productCategory LIKE :productCat ASC")
    fun getProductsByCategory(productCat : String) : Flow<List<ProductModel>>

    @Query("SELECT * FROM products ORDER BY productSubCategory LIKE :productSubCat ASC")
    fun getProductsBySubCategory(productSubCat : String) : Flow<List<ProductModel>>

    // TO GET ALL CART PRODUCTS

    @Query("SELECT * FROM cartProductTable")
    fun getCartProducts() : Flow<List<CartModel>>


    //TO GET PRODUCTS FROM SEARCH
    @Query("SELECT * FROM products WHERE productName LIKE :searchQuery OR productSubCategory LIKE :searchQuery")
    fun searchDatabase(searchQuery : String) : Flow<List<ProductModel>>

    // TO DELETE PRODUCT FROM CART
    @Delete
   suspend fun deleteProduct(cartModel: CartModel)

    @Query("SELECT * FROM cartProductTable WHERE productId = :id")
    suspend fun isExist(id : String) : CartModel

}

