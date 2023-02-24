package com.example.mapd726_groupproject_team3_agriapp.DI

import android.content.Context
import androidx.room.Room
import com.example.mapd726_groupproject_team3_agriapp.RoomDB.AgroDao
import com.example.mapd726_groupproject_team3_agriapp.RoomDB.AgroDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Singleton
    @Provides
    fun provideAgroDatabase(@ApplicationContext context: Context) : AgroDatabase {

        return Room.databaseBuilder(context, AgroDatabase::class.java,
            "agro_database")
            .build()
    }

    @Singleton
    @Provides
    fun provideAgroDao(agroDatabase: AgroDatabase) : AgroDao {
        return agroDatabase.agroDao()

    }
}
