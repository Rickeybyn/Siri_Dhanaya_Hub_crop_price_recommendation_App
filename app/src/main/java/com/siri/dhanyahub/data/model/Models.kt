package com.siri.dhanyahub.data.model

data class MilletPrice(
    val millet: String,
    val mandi: String,
    val pricePerQuintal: Int,
    val trend: Trend,
    val dayHigh7: Int,
    val dayLow7: Int,
    val updatedAt: String,
)

enum class Trend { UP, DOWN, FLAT }

data class Recipe(
    val id: String,
    val title: String,
    val millet: String,
    val languageNote: String,
    val ingredients: List<String>,
    val steps: List<String>,
    val healthNote: String,
    val imageUrl: String = "",
)

data class FpoContact(
    val id: String,
    val name: String,
    val location: String,
    val phone: String,
    val note: String,
)

data class GenAiSuggestion(
    val headline: String,
    val explanation: String,
    val recipeTitle: String,
)

data class PriceForecast(
    val millet: String,
    val currentPrice: Int,
    val forecastPrices: List<Int>,
    val confidence: Int,
    val recommendation: String,
    val trend: Trend
)

enum class Role { FARMER, CONSUMER }
