package com.example.mapd726_groupproject_team3_agriapp.Repository

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mapd726_groupproject_team3_agriapp.DI.FirebaseModule
import com.example.mapd726_groupproject_team3_agriapp.DataModels.*
import com.example.mapd726_groupproject_team3_agriapp.RoomDB.AgroDao
import com.google.android.gms.tasks.Task

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import kotlinx.coroutines.*
import org.jetbrains.annotations.NotNull
import javax.inject.Inject

class FirebaseRepository @Inject constructor(val auth : FirebaseAuth, val db : FirebaseFirestore, val storage : FirebaseStorage, val agroDao: AgroDao) {

init {


    // GETTING PRODUCTS FROM FIREBASE AND FIRING INSERT QUERY IN INIT
    val productsList = ArrayList<ProductModel>()
    db.collection("Products").get().addOnSuccessListener {
        for (doc in it.documents) {
            val firebaseData = doc.toObject(ProductModel::class.java)
            productsList?.add(firebaseData!!)
        }
        if(productsList.size > 1 || productsList.isNotEmpty()){

            CoroutineScope(Dispatchers.IO).launch {
                delay(2000)
                agroDao.insertProducts(productsList)
            }
        }

    }

    // GETTING SUB CATEGORIES FROM FIREBASE AND INSERTING IT IN ROOM AT INIT

    val subCategoryList = ArrayList<SubCategoryModel>()
    db.collection("SubCategories").get().addOnSuccessListener {
        for (doc in it.documents) {
            val firebaseData = doc.toObject(SubCategoryModel::class.java)
            subCategoryList?.add(firebaseData!!)
        }
        if(subCategoryList.size > 1 || subCategoryList.isNotEmpty()){

            CoroutineScope(Dispatchers.IO).launch {
                delay(2000)
                agroDao.insertSubCategories(subCategoryList)
            }
        }

    }


}

// GETTING SLIDER IMAGES FROM FIREBASE

    private val sliders = MutableLiveData<ArrayList<Slider>>()
    private val listSliders : LiveData<ArrayList<Slider>>
        get() = sliders
    fun getSliderImages(collectionPath: String, imageList: ArrayList<Slider>): LiveData<ArrayList<Slider>> {
        db.collection(collectionPath).get()
            .addOnSuccessListener {
                imageList.clear()
                for (doc in it.documents) {
                    val firebaseData = doc.toObject(Slider::class.java)
                    imageList.add(firebaseData!!)
                    Log.d("Fetched Images", imageList.toString())
                }
                sliders.value = imageList
            }
        return listSliders

    }



    //GETTING CATEGORIES FROM FIREBASE, PARSING THEM AS LIVE DATA

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


    // SETTING USER DATA TO FIREBASE FIRE STORE



    // FETCHING USER ORDERS FROM DATABASE

    private val orders = MutableLiveData<ArrayList<OrderModel>>()
    private val listOrders : LiveData<ArrayList<OrderModel>>
        get() = orders
    fun getOrdersForCurrentUsers(customerId:String) : LiveData<ArrayList<OrderModel>> {
        val orderList = ArrayList<OrderModel>()
        db.collection("customer").whereEqualTo("customerId",customerId).get().addOnSuccessListener {
            orderList.clear()
            for(doc in it.documents){
                val firebaseData = doc.toObject(OrderModel::class.java)
                if (firebaseData != null) {
                    orderList.add(firebaseData)
                }
            }
            orders.value = orderList
        }
        return listOrders
    }

    private val _cancelOrder = MutableLiveData<String>()
    private val cancelOrder : LiveData<String>
        get() =_cancelOrder
    fun cancelOrder(orderId: String): LiveData<String> {
        val data = HashMap<String,Any>()
        data["status"] = "Cancelled"
        db.collection("Orders").document(orderId).update(data).addOnSuccessListener {
            _cancelOrder.value = "Success"
        }.addOnFailureListener {
            _cancelOrder.value = "Failed"
        }
        return cancelOrder
    }


}








/*
    private var _list = MutableLiveData<List<SubCategoryModel>>()
    val list : LiveData<List<SubCategoryModel>>
        get() = _list
    fun getAllSubCategoriesFromFirebase(collectionPath : String): LiveData<List<SubCategoryModel>> {
        val productList = ArrayList<SubCategoryModel>()
        db.collection(collectionPath).get().addOnSuccessListener {
            for (doc in it.documents) {
                val firebaseData = doc.toObject(SubCategoryModel::class.java)
                productList?.add(firebaseData!!)
            }
            _list.value = productList
        }
        return list

    }

    private var _productList = MutableLiveData<List<ProductModel>>()
    val productList : LiveData<List<ProductModel>>
        get() = _productList
    fun getAllProductsForDB(collectionPath: String): LiveData<List<ProductModel>> {

        val productsList = ArrayList<ProductModel>()
        db.collection(collectionPath).get().addOnSuccessListener {
            for (doc in it.documents) {
                val firebaseData = doc.toObject(ProductModel::class.java)
                productsList?.add(firebaseData!!)
            }
            _productList.value = productsList
        }
        return productList
    }


    //GETTING SUB-CATEGORIES FROM FIREBASE, PARSING THEM AS LIVE DATA


    private val subCategory = MutableLiveData<ArrayList<SubCategoryModel>>()
    private val listSubCategory : LiveData<ArrayList<SubCategoryModel>>
        get() = subCategory
    fun getSubCategoriesFromFirebase(collectionPath: String, field: String,  productCategory: String, subCategoryList: ArrayList<SubCategoryModel>) : LiveData<ArrayList<SubCategoryModel>> {

        db.collection(collectionPath).whereEqualTo(field, productCategory)
            .get().addOnSuccessListener {
                subCategoryList?.clear()
                for (doc in it.documents) {
                    var firebaseData = doc.toObject(SubCategoryModel::class.java)
                    subCategoryList?.add(firebaseData!!)
                    //Log.d("Fetched category", firebaseData.Category.toString())
                }
                subCategory.value = subCategoryList
            }

        return listSubCategory
    }




    //GETTING PRODUCTS FROM FIREBASE, PARSING THEM AS LIVE DATA
   private val products = MutableLiveData<ArrayList<ProductModel>>()
    private val listProducts : LiveData<ArrayList<ProductModel>>
        get() = products

    fun getProductsFromFirebase(collectionPath: String, field: String, productCategory: String, productList : ArrayList<ProductModel>?) : LiveData<ArrayList<ProductModel>> {

        db.collection(collectionPath).whereEqualTo(field, productCategory)
            .get().addOnSuccessListener {
                productList?.clear()
                for (doc in it.documents) {
                    val firebaseData = doc.toObject(ProductModel::class.java)
                    productList?.add(firebaseData!!)
                }
                products.value = productList!!
            }
        return listProducts


    }



 */


