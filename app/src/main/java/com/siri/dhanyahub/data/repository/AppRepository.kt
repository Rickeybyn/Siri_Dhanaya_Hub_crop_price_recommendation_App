package com.siri.dhanyahub.data.repository

import com.siri.dhanyahub.data.local.*
import com.siri.dhanyahub.data.model.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class AppRepository(
    private val recipeDao: RecipeDao,
    private val priceDao: PriceDao,
    private val fpoDao: FpoDao,
) {
    private val sampleRecipes = listOf(
        Recipe(
            id = "r1",
            title = "ನವಣೆ ಉಪ್ಮಾ (Navane Upma)",
            millet = "Navane",
            languageNote = "Kannada / English",
            ingredients = listOf("1 cup navane", "onion", "tomato", "mustard", "curry leaves", "salt", "lemon"),
            steps = listOf(
                "Wash navane and soak for 10 minutes.",
                "Heat oil, add mustard and curry leaves.",
                "Add onions, tomato, and sauté well.",
                "Add navane and water (1:2 ratio). Cook until fluffy.",
                "Squeeze lemon and serve hot."
            ),
            healthNote = "Navane is light, rich in fibre, and a smart breakfast choice for diabetic management.",
        ),
        Recipe(
            id = "r2",
            title = "ಸಜ್ಜೆ ಖಿಚಡಿ (Sajje Khichdi)",
            millet = "Sajje",
            languageNote = "Kannada / English",
            ingredients = listOf("1 cup sajje", "carrot", "beans", "peas", "ghee", "salt", "ginger"),
            steps = listOf(
                "Dry roast sajje for 2 minutes until aromatic.",
                "Pressure cook with chopped vegetables and spices.",
                "Finish with a spoonful of ghee for aroma and healthy fats."
            ),
            healthNote = "Sajje (Pearl Millet) gives steady energy and keeps you full for longer active days.",
        ),
        Recipe(
            id = "r3",
            title = "ಬಾರಗು ರಾಗಿ ಮಿಕ್ಸ್ (Baragu Mix)",
            millet = "Baragu",
            languageNote = "Kannada / English",
            ingredients = listOf("baragu flour", "milk or water", "jaggery", "cardamom", "almonds"),
            steps = listOf(
                "Mix flour with water to make a smooth slurry.",
                "Simmer until thick, stirring constantly.",
                "Add jaggery and cardamom. Garnish with almonds."
            ),
            healthNote = "Baragu (Proso Millet) is easy to digest and perfect for balanced, light meals.",
        ),
        Recipe(
            id = "r4",
            title = "ರಾಗಿ ಮುದ್ದೆ (Ragi Mudde)",
            millet = "Ragi",
            languageNote = "Kannada / English",
            ingredients = listOf("ragi flour", "water", "salt"),
            steps = listOf(
                "Boil water with a pinch of salt.",
                "Add ragi flour slowly while stirring with a wooden stick.",
                "Shape into soft balls and serve hot with sambar or saaru."
            ),
            healthNote = "Ragi is calcium-rich and a classic Kannada staple for bone health.",
        ),
        Recipe(
            id = "r5",
            title = "ಊದಲು ಪಲಾವ್ (Oodalu Pulao)",
            millet = "Oodalu",
            languageNote = "Kannada / English",
            ingredients = listOf("Oodalu millet", "Mixed vegetables", "Cinnamon", "Cloves", "Mint"),
            steps = listOf(
                "Soak Oodalu for 30 minutes.",
                "Sauté spices and vegetables in a cooker.",
                "Add millet and water, cook for 2 whistles."
            ),
            healthNote = "Oodalu (Barnyard Millet) is high in iron and highly recommended for cleaning the body.",
        )
    )

    private val samplePrices = listOf(
        MilletPrice("Navane", "Davangere", 3750, Trend.UP, 3890, 3500, "Today, 09:10 AM"),
        MilletPrice("Sajje", "Bengaluru", 3220, Trend.DOWN, 3410, 3100, "Today, 09:10 AM"),
        MilletPrice("Baragu", "Mysuru", 4100, Trend.UP, 4300, 3950, "Today, 09:10 AM"),
        MilletPrice("Ragi", "Hubballi", 2750, Trend.FLAT, 2890, 2680, "Today, 09:10 AM"),
        MilletPrice("Oodalu", "Shivamogga", 4620, Trend.UP, 4800, 4400, "Today, 09:10 AM"),
        MilletPrice("Korale", "Tumakuru", 5200, Trend.UP, 5350, 5100, "Today, 09:10 AM"),
    )

    private val sampleFpos = listOf(
        FpoContact("f1", "Siri Growers FPO", "Davangere", "+91 90000 11111", "Bulk millet procurement and produce listing"),
        FpoContact("f2", "Dhanya Farmers Union", "Mysuru", "+91 90000 22222", "Farmer-consumer connect for verified lots"),
        FpoContact("f3", "Karnataka Millet Co-op", "Bengaluru", "+91 90000 33333", "Retail and bulk supply with local support"),
        FpoContact("f4", "Raitha Mitra Sangha", "Mandya", "+91 90000 44444", "Direct farm-to-home organic supply chain"),
    )

    private val sampleForecasts = listOf(
        PriceForecast("Navane", 3750, listOf(3750, 3780, 3820, 3850, 3900, 3950, 4020), 92, "Hold stock for 10 days. Prices are expected to rise due to export demand.", Trend.UP),
        PriceForecast("Ragi", 2750, listOf(2750, 2740, 2720, 2700, 2680, 2650, 2620), 88, "Sell immediately. New harvest arrivals are likely to cool down the market.", Trend.DOWN),
        PriceForecast("Sajje", 3220, listOf(3220, 3230, 3215, 3225, 3220, 3230, 3225), 95, "Prices remain stable. Buy or sell as per immediate requirement.", Trend.FLAT)
    )

    suspend fun seedDatabaseIfEmpty() {
        val existingPrices = priceDao.observePrices().firstOrNull()
        if (existingPrices.isNullOrEmpty()) {
            priceDao.upsertAll(
                samplePrices.map {
                    CachedPriceEntity(
                        key = "${it.millet}-${it.mandi}",
                        millet = it.millet,
                        mandi = it.mandi,
                        pricePerQuintal = it.pricePerQuintal,
                        trend = it.trend.name,
                        dayHigh7 = it.dayHigh7,
                        dayLow7 = it.dayLow7,
                        updatedAt = it.updatedAt
                    )
                }
            )
        }
        
        val existingFpos = fpoDao.observeContacts().firstOrNull()
        if (existingFpos.isNullOrEmpty()) {
            fpoDao.upsertAll(
                sampleFpos.map {
                    FpoContactEntity(
                        id = it.id,
                        name = it.name,
                        location = it.location,
                        phone = it.phone,
                        note = it.note
                    )
                }
            )
        }
    }

    fun observePrices(): Flow<List<MilletPrice>> =
        priceDao.observePrices().map { rows ->
            if (rows.isEmpty()) samplePrices else rows.map {
                MilletPrice(
                    millet = it.millet,
                    mandi = it.mandi,
                    pricePerQuintal = it.pricePerQuintal,
                    trend = Trend.valueOf(it.trend),
                    dayHigh7 = it.dayHigh7,
                    dayLow7 = it.dayLow7,
                    updatedAt = it.updatedAt
                )
            }
        }

    fun observeFavorites(): Flow<List<Recipe>> =
        recipeDao.observeFavorites().map { rows ->
            rows.map {
                Recipe(
                    id = it.recipeId,
                    title = it.title,
                    millet = it.millet,
                    languageNote = it.languageNote,
                    ingredients = it.ingredientsCsv.split("|").filter(String::isNotBlank),
                    steps = it.stepsCsv.split("|").filter(String::isNotBlank),
                    healthNote = it.healthNote
                )
            }
        }

    fun observeContacts(): Flow<List<FpoContact>> =
        fpoDao.observeContacts().map { rows ->
            if (rows.isEmpty()) sampleFpos else rows.map {
                FpoContact(it.id, it.name, it.location, it.phone, it.note)
            }
        }

    fun getMarketForecasts(): List<PriceForecast> = sampleForecasts

    fun getRecipes(): List<Recipe> = sampleRecipes

    fun searchRecipes(query: String): List<Recipe> {
        val q = query.trim().lowercase()
        if (q.isEmpty()) return sampleRecipes
        return sampleRecipes.filter {
            it.title.lowercase().contains(q) ||
            it.millet.lowercase().contains(q) ||
            it.healthNote.lowercase().contains(q) ||
            it.ingredients.joinToString(" ").lowercase().contains(q)
        }
    }

    fun searchHealth(query: String): List<MilletHealthCard> {
        val cards = listOf(
            MilletHealthCard("Navane", "High fibre, light, and helpful for smart portion control.", "Good for breakfast and balanced meals."),
            MilletHealthCard("Sajje", "Slow energy release and filling for active days.", "Nice for lunch or dinner."),
            MilletHealthCard("Baragu", "Gentle on digestion and easy to mix in porridges.", "Good for simple home cooking."),
            MilletHealthCard("Ragi", "Calcium-rich traditional staple food.", "Best known for strong Kannada dishes."),
            MilletHealthCard("Oodalu", "Rich in iron and fibre, great for cleansing.", "Ideal for replacement in rice dishes."),
            MilletHealthCard("Korale", "High protein and helpful for nerve health.", "Traditional choice for strengthening.")
        )
        val q = query.trim().lowercase()
        if (q.isEmpty()) return cards
        return cards.filter {
            it.millet.lowercase().contains(q) ||
            it.benefits.lowercase().contains(q) ||
            it.note.lowercase().contains(q)
        }
    }

    fun recommendRecipe(millet: String, preference: String): GenAiSuggestion {
        val match = sampleRecipes.firstOrNull { it.millet.contains(millet, ignoreCase = true) }
            ?: sampleRecipes.first()
        
        val headline = when {
            preference.contains("diabetic", ignoreCase = true) -> "Best fit for diabetes-friendly meals."
            preference.contains("energy", ignoreCase = true) -> "Energy-supporting meal idea."
            else -> "Personalized recommendation."
        }
        
        val explanation = when {
            millet.contains("Navane", ignoreCase = true) -> "Navane is a smart pick for lighter cooking and fibre-rich meals."
            millet.contains("Sajje", ignoreCase = true) -> "Sajje Khichdi gives a satisfying meal with a steady release of energy."
            millet.contains("Ragi", ignoreCase = true) -> "Ragi is excellent for bone health and traditional satiety."
            else -> "This millet choice is packed with local nutrition and minerals."
        }

        return GenAiSuggestion(headline, explanation, match.title)
    }

    suspend fun toggleFavorite(recipe: Recipe, shouldSave: Boolean) {
        if (shouldSave) {
            recipeDao.upsertFavorite(
                FavouriteRecipeEntity(
                    recipeId = recipe.id,
                    title = recipe.title,
                    millet = recipe.millet,
                    languageNote = recipe.languageNote,
                    ingredientsCsv = recipe.ingredients.joinToString("|"),
                    stepsCsv = recipe.steps.joinToString("|"),
                    healthNote = recipe.healthNote
                )
            )
        } else {
            recipeDao.deleteFavorite(recipe.id)
        }
    }

    fun isFavorite(recipeId: String): Flow<Boolean> = recipeDao.isFavorite(recipeId)

    data class MilletHealthCard(val millet: String, val benefits: String, val note: String)
}
