package com.example.mapd726_groupproject_team3_agriapp.Repository

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import com.example.mapd726_groupproject_team3_agriapp.DataModels.*
import com.example.mapd726_groupproject_team3_agriapp.RoomDB.AgroDao
import com.example.mapd726_groupproject_team3_agriapp.Utils.Constant
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepository @Inject constructor(val auth : FirebaseAuth, val db : FirebaseFirestore, val storage : FirebaseStorage, val agroDao: AgroDao)
{


    // SAVING USER DATA TO FIREBASE
    suspend fun saveUserDataToFirebase(collectionPath: String,data : CustomerModel): Boolean {
        var status : Boolean? = null
        db.collection(collectionPath).document(data.customerId).set(data).addOnSuccessListener {
            status = true
        }
            .addOnFailureListener{
                status = false
            }
        delay(1500)
        return status!!
    }


    private var _firebaseData = MutableLiveData<CustomerModel>()
    val firebaseData : LiveData<CustomerModel>
        get() = _firebaseData

    //GETTING USER DATA FROM FIREBASE
    suspend fun getUserDataFromFirebase(collectionPath: String,userNumber: String):LiveData<CustomerModel>{
        db.collection(collectionPath).whereEqualTo("customerNumber",userNumber).get().addOnSuccessListener {
            for (doc in it.documents)
            {
                _firebaseData.value = doc.toObject(CustomerModel::class.java)
            }
            }
        delay(2000)
        return firebaseData
    }


    // To Store Fetched User Data into RoomDB
    suspend fun insertUserDataIntoRoom(customerModel: CustomerModel) = agroDao.insertUserData(customerModel)

    // To get Stored User Data From RoomDB

    suspend fun getUserDataFromRoom(customerId: String) = agroDao.getUserData(customerId)

    //To delete UserData from Room Before Saving


    //GETTING USER DATA FROM FIREBASE, PARSING THEM AS LIVE DATA

    private val categories = MutableLiveData<ArrayList<CategoryModel>>()
    private val listCategories : LiveData<ArrayList<CategoryModel>>
        get() = categories

    fun getCategoriesFromFirebase(collectionPath: String, categoryList: ArrayList<CategoryModel>?): LiveData<ArrayList<CategoryModel>> {
        db.collection(collectionPath).get().addOnSuccessListener {
            categoryList?.clear()
            for (doc in it.documents) {
                val firebaseData = doc.toObject(CategoryModel::class.java)
                categoryList?.add(firebaseData!!)
                //Log.d("Fetched category", firebaseData.Category.toString())
            }
            categories.value = categoryList!!
        }
        return listCategories
    }

    // USER SIGNOUT
    fun signOut()
    {
        auth.signOut()
    }

    // INSERT USER ORDER INTO ROOM

    suspend fun insertOrder(orderModel: OrderModel) = agroDao.insertOrder(orderModel)


    // INSERTING UPDATES OF USER PROFILE
    fun updateUserProfileInfo(firstName: String, lastName: String, userProfile: String?,userId: String)
    {
        val data = HashMap<String,Any>()
        data.put("customerFirstName",firstName)
        data.put("customerLastName",lastName)
        data.put("customerImage",userProfile!!)

        db.collection("Users").document(userId).update(data)
    }

    suspend fun addWishlistProduct(item: WishlistModel) = agroDao.insertWishlistProducts(item)
    suspend fun removeWishlistProduct(item: String) = agroDao.removeWishlistProduct(item)
    fun getWishlistProducts() = agroDao.getWishlistProducts()
    fun updateWishlistInServer(customerId: String) {
        val data = HashMap<String,Any>()
        data.put("wishList",Constant.wishlistProductId)
        db.collection("Users").document(customerId).update(data)
    }

    fun fetchWishlistFromServer(customerId: String){
        db.collection("Users").document(customerId).get().addOnSuccessListener {snapshot->
            if(snapshot.exists())
            {

            }
        }

    }


}