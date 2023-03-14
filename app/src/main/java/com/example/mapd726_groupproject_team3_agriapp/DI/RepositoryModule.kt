package com.example.mapd726_groupproject_team3_agriapp.DI

import com.example.mapd726_groupproject_team3_agriapp.Repository.FirebaseRepository
import com.example.mapd726_groupproject_team3_agriapp.Repository.ProductRepository
import com.example.mapd726_groupproject_team3_agriapp.Repository.UserRepository
import com.example.mapd726_groupproject_team3_agriapp.RoomDB.AgroDao
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {

    @Provides
    fun providesFirebaseRepository(auth: FirebaseAuth,db : FirebaseFirestore, storage: FirebaseStorage, agroDao : AgroDao) : FirebaseRepository {
        return FirebaseRepository(auth,db,storage,agroDao)
    }

    @Provides
    fun providesProductsRepository(db : FirebaseFirestore, agroDao : AgroDao) : ProductRepository {
        return ProductRepository(db,agroDao)
    }

    @Provides
    fun providesUserRepository(auth: FirebaseAuth, db : FirebaseFirestore, storage: FirebaseStorage, agroDao : AgroDao) : UserRepository {
        return UserRepository(auth,db,storage,agroDao)
    }

}

