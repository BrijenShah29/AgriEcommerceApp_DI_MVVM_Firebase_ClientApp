package com.example.mapd726_groupproject_team3_agriapp.Repository

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mapd726_groupproject_team3_agriapp.DI.FirebaseModule
import com.example.mapd726_groupproject_team3_agriapp.DataModels.CategoryModel
import com.example.mapd726_groupproject_team3_agriapp.DataModels.ProductModel
import com.example.mapd726_groupproject_team3_agriapp.DataModels.Slider
import com.example.mapd726_groupproject_team3_agriapp.DataModels.SubCategoryModel
import com.example.mapd726_groupproject_team3_agriapp.RoomDB.AgroDao
import com.google.android.gms.tasks.Task

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import org.jetbrains.annotations.NotNull
import javax.inject.Inject

class FirebaseRepository @Inject constructor(val auth : FirebaseAuth, val db : FirebaseFirestore, val storage : FirebaseStorage, val agroDao: AgroDao) {



    suspend fun getSliderImages(collectionPath: String, imageList: ArrayList<Slider>): MutableLiveData<ArrayList<Slider>> {
        val images = MutableLiveData<ArrayList<Slider>>()
        db.collection(collectionPath).get()
            .addOnSuccessListener {
                imageList.clear()
                for (doc in it.documents) {
                    val firebaseData = doc.toObject(Slider::class.java)
                    imageList.add(firebaseData!!)
                    Log.d("Fetched Images", imageList.toString())
                }
                images.value = imageList
            }
        return images

    }

    suspend fun getCategoriesFromFirebase(collectionPath: String, categoryList: ArrayList<CategoryModel>?): MutableLiveData<ArrayList<CategoryModel>> {
         val categories = MutableLiveData<ArrayList<CategoryModel>>()
        db.collection(collectionPath)
            .get().addOnSuccessListener {
                categoryList?.clear()
                for (doc in it.documents) {
                    val firebaseData = doc.toObject(CategoryModel::class.java)
                    categoryList?.add(firebaseData!!)
                    //Log.d("Fetched category", firebaseData.Category.toString())
                }
                categories.value = categoryList!!
            }
        return categories
    }


   suspend fun getProductsFromFirebase(collectionPath: String, field: String, productCategory: String, productList : ArrayList<ProductModel>?) : MutableLiveData<ArrayList<ProductModel>>{
       val list = MutableLiveData<ArrayList<ProductModel>>()
        db.collection(collectionPath).whereEqualTo(field, productCategory)
            .get().addOnSuccessListener {
                productList?.clear()
                for (doc in it.documents) {
                    val firebaseData = doc.toObject(ProductModel::class.java)
                    productList?.add(firebaseData!!)
                }

                list.value = productList!!

            }
        return list


    }


    suspend fun getSubCategoriesFromFirebase(collectionPath: String, field: String,  productCategory: String, subCategoryList: ArrayList<SubCategoryModel>) : MutableLiveData<ArrayList<SubCategoryModel>> {
        val subCategory = MutableLiveData<ArrayList<SubCategoryModel>>()
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

        return subCategory
    }

    suspend fun getSingleProductFromFirebase(collectionPath: String, productID: String) :MutableLiveData<ProductModel> {
        var product = MutableLiveData<ProductModel>()
        db.collection(collectionPath).document(productID)
            .get().addOnSuccessListener {
                product.value = it.toObject(ProductModel::class.java)
            }
        return product

    }

}