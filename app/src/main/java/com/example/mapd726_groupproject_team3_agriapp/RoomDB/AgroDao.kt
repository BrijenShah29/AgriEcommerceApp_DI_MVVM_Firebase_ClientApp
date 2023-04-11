package com.example.mapd726_groupproject_team3_agriapp.RoomDB

import androidx.room.*
import com.example.mapd726_groupproject_team3_agriapp.DataModels.*
import kotlinx.coroutines.flow.Flow

@Dao
interface AgroDao {


    // TO INSERT ALL THE PRODUCTS INTO DATABASE
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProducts(productModel: List<ProductModel>)

    // TO INSERT PRODUCTS INTO CART
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCartProducts(cartModel: CartModel)

    //TO INSERT SUBCATEGORIES INTO DATABASE
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSubCategories(subCategoryModel : List<SubCategoryModel>)


    // TO INSERT RECENTLY VISITED PRODUCTS
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecentlyVisitedProducts(recentlyVisitedModel: RecentlyVisitedModel)


    // TO INSERT LIKED PRODUCTS
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWishlistProducts(wishlistModel: WishlistModel)

    // TO INSERT USERDATA IF IT EXISTS
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserData(customerModel: CustomerModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrder(orderModel: OrderModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertYouMayLikeProducts(youMayLikeModel: List<YouMayLikeModel>)

    @Query("SELECT * FROM products")
    fun getProducts() : Flow<List<ProductModel>>

    @Query("SELECT * FROM products WHERE productCategory LIKE :productCat ORDER BY productName ASC")
    fun getProductsByCategory(productCat : String) : Flow<List<ProductModel>>

    @Query("SELECT * FROM products WHERE productSubCategory LIKE :productSubCat ORDER BY productName ASC")
    fun getProductsBySubCategory(productSubCat : String) : Flow<List<ProductModel>>

    // TO GET SUB CATEGORIES BY CATEGORY

    @Query("SELECT * FROM subCategoryTable WHERE Category LIKE :category OR root LIKE :category")
    fun getSubCategoryByCategory(category : String) : Flow<List<SubCategoryModel>>


    // TO GET ALL CART PRODUCTS
    @Query("SELECT * FROM cartProductTable")
    fun getCartProducts() : Flow<List<CartModel>>


    //TO GET ALL WISHLIST PRODUCTS
    @Query("SELECT * FROM wishlistTable")
    fun getWishlistProducts() : Flow<List<WishlistModel>>

    //TO GET SINGLE PRODUCT FROM PRODUCTS TABLE
    @Query("SELECT * FROM products WHERE productId = :id")
    suspend fun getSingleProduct(id: String) : ProductModel

    //TO GET PRODUCTS FROM SEARCH
    @Query("SELECT * FROM products WHERE productName LIKE :searchQuery OR productSubCategory LIKE :searchQuery")
    fun searchDatabase(searchQuery : String) : Flow<List<ProductModel>>

    //TO GET USER DATA FROM ROOM DB
    @Query("SELECT * FROM CustomerDataTable WHERE customerId Like :customerId LIMIT 1")
    suspend fun getUserData(customerId: String) : CustomerModel


    //TO GET ORDERS
    @Query("SELECT * FROM orderTable ORDER BY orderedDate DESC")
    fun getOrders() : Flow<List<OrderModel>>


    // TO DELETE PRODUCT FROM CART
    @Delete
   suspend fun deleteProduct(cartModel: CartModel)

// TO DELETE ALL CUSTOMER DATA

   // @Query("DELETE FROM CustomerDataTable")
   // suspend fun deleteUserData(customerModel: CustomerModel)

   // EMPTY THE CART TABLE AFTER SUCCESSFUL PAYMENT
   @Query("DELETE FROM cartProductTable")
   suspend fun deleteProductsFromCart()


   // TO CHECK IF PRODUCT ALREADY EXISTS IN DB
    @Query("SELECT * FROM cartProductTable WHERE productId = :id")
    suspend fun isExist(id : String) : CartModel

    // UPDATE CART PRODUCTS FROM CART PAGE (QUANTITY)

    @Query("UPDATE cartProductTable SET productQuantity = :quantity, totalAmount =:totalAmount WHERE productId LIKE :productId")
    suspend fun updateCart(quantity : Int,totalAmount : Double , productId : String)

    @Query("DELETE FROM RecentlyVisitedProductTable WHERE id NOT IN (SELECT id FROM RecentlyVisitedProductTable ORDER BY id DESC LIMIT 10)")
    suspend fun removeOldRecentlyVisitedData()

    // TO GET PRODUCTS FROM RECENTLY VISITED TABLE
    @Query("SELECT * FROM RecentlyVisitedProductTable ORDER BY id DESC LIMIT 10 ")
    fun getRecentlyVisitedProducts() : Flow<List<RecentlyVisitedModel>>

    @Query("SELECT * FROM YouMayLikeProductTable ORDER BY productId DESC LIMIT 10")
    fun getYouMayLikeProducts() : Flow<List<YouMayLikeModel>>

    @Query("DELETE FROM wishlistTable WHERE productId = :productId")
    suspend fun removeWishlistProduct(productId: String)

    @Query("DELETE FROM RecentlyVisitedProductTable WHERE id NOT IN (SELECT id FROM YouMayLikeProductTable ORDER BY id DESC LIMIT 10)")
    suspend fun removeYouMayLikeProducts()



}



