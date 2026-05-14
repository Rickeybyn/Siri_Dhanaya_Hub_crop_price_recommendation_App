package com.siri.dhanyahub.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_recipes")
data class FavouriteRecipeEntity(
    @PrimaryKey val recipeId: String,
    val title: String,
    val millet: String,
    val languageNote: String,
    val ingredientsCsv: String,
    val stepsCsv: String,
    val healthNote: String,
)

@Entity(tableName = "cached_prices")
data class CachedPriceEntity(
    @PrimaryKey val key: String,
    val millet: String,
    val mandi: String,
    val pricePerQuintal: Int,
    val trend: String,
    val dayHigh7: Int,
    val dayLow7: Int,
    val updatedAt: String,
)

@Entity(tableName = "fpo_contacts")
data class FpoContactEntity(
    @PrimaryKey val id: String,
    val name: String,
    val location: String,
    val phone: String,
    val note: String,
)
