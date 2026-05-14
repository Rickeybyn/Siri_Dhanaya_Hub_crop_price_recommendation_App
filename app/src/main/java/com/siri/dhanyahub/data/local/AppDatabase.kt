package com.siri.dhanyahub.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [FavouriteRecipeEntity::class, CachedPriceEntity::class, FpoContactEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun recipeDao(): RecipeDao
    abstract fun priceDao(): PriceDao
    abstract fun fpoDao(): FpoDao

    companion object {
        fun build(context: Context): AppDatabase =
            Room.databaseBuilder(context, AppDatabase::class.java, "siri_dhanya_hub.db")
                .fallbackToDestructiveMigration()
                .build()
    }
}
