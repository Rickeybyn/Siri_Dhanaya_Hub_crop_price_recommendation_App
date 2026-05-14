package com.siri.dhanyahub.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeDao {
    @Query("SELECT * FROM favorite_recipes ORDER BY title ASC")
    fun observeFavorites(): Flow<List<FavouriteRecipeEntity>>

    @Upsert
    suspend fun upsertFavorite(recipe: FavouriteRecipeEntity)

    @Query("DELETE FROM favorite_recipes WHERE recipeId = :recipeId")
    suspend fun deleteFavorite(recipeId: String)

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_recipes WHERE recipeId = :recipeId)")
    fun isFavorite(recipeId: String): Flow<Boolean>
}

@Dao
interface PriceDao {
    @Query("SELECT * FROM cached_prices ORDER BY mandi ASC")
    fun observePrices(): Flow<List<CachedPriceEntity>>

    @Upsert
    suspend fun upsertAll(prices: List<CachedPriceEntity>)
}

@Dao
interface FpoDao {
    @Query("SELECT * FROM fpo_contacts ORDER BY location ASC")
    fun observeContacts(): Flow<List<FpoContactEntity>>

    @Upsert
    suspend fun upsertAll(items: List<FpoContactEntity>)
}
