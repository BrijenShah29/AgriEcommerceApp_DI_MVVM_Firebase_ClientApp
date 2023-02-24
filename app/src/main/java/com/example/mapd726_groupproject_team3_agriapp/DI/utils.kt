package com.example.mapd726_groupproject_team3_agriapp.DI

import com.example.mapd726_groupproject_team3_agriapp.DataModels.ProductModel
import com.example.mapd726_groupproject_team3_agriapp.RoomDB.AgroDao
import com.example.mapd726_groupproject_team3_agriapp.RoomDB.AgroDatabase
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
/*
@Module
@InstallIn(SingletonComponent::class)
object utils {

    @Singleton
    @Provides
    fun providesFirebaseDocuments(db : FirebaseFirestore , collectionPath: String) = return db.collection(collectionPath).get().addOnSuccessListener { for(doc in it.documents){ val firebaseData = doc.toObject(ProductModel::class.java) } }
}

 */