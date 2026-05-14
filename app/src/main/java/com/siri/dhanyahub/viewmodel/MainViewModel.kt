package com.siri.dhanyahub.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.siri.dhanyahub.data.model.*
import com.siri.dhanyahub.data.repository.AppRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class AppUiState(
    val role: Role? = null,
    val searchQuery: String = "",
    val selectedMillet: String = "Navane",
    val preference: String = "Healthy breakfast",
    val targetPrice: String = "",
    val aiSuggestion: GenAiSuggestion? = null,
)

class MainViewModel(private val repository: AppRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(AppUiState())
    val uiState: StateFlow<AppUiState> = _uiState.asStateFlow()

    private val _rawPrices = repository.observePrices()
    private val _rawFavorites = repository.observeFavorites()
    private val _rawContacts = repository.observeContacts()

    val prices = combine(_rawPrices, _uiState) { list: List<MilletPrice>, state: AppUiState ->
        val q = state.searchQuery.trim().lowercase()
        if (q.isEmpty()) list else list.filter { it.millet.lowercase().contains(q) || it.mandi.lowercase().contains(q) }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    val recipes = _uiState.map { state ->
        repository.searchRecipes(state.searchQuery)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    val favorites = combine(_rawFavorites, _uiState) { list: List<Recipe>, state: AppUiState ->
        val q = state.searchQuery.trim().lowercase()
        if (q.isEmpty()) list else list.filter { it.title.lowercase().contains(q) || it.millet.lowercase().contains(q) }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    val healthCards = _uiState.map { state ->
        repository.searchHealth(state.searchQuery)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    val contacts = _rawContacts.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    val marketForecasts = flow {
        emit(repository.getMarketForecasts())
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    val priceAlertMatches = combine(prices, _uiState) { priceList: List<MilletPrice>, state: AppUiState ->
        val target = state.targetPrice.replace(",", "").trim().toIntOrNull() ?: return@combine emptyList<MilletPrice>()
        priceList.filter {
            if (state.role == Role.FARMER) {
                it.pricePerQuintal >= target // Farmer wants to know if they can sell at or above target
            } else {
                it.pricePerQuintal <= target // Consumer wants to know if they can buy at or below target
            }
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    init {
        viewModelScope.launch { repository.seedDatabaseIfEmpty() }
        viewModelScope.launch {
            _uiState.update { it.copy(aiSuggestion = repository.recommendRecipe(it.selectedMillet, it.preference)) }
        }
    }

    fun setRole(role: Role?) = _uiState.update { it.copy(role = role) }
    fun setSearchQuery(q: String) = _uiState.update { it.copy(searchQuery = q) }
    fun setSelectedMillet(millet: String) {
        _uiState.update { current ->
            val updated = current.copy(selectedMillet = millet)
            updated.copy(aiSuggestion = repository.recommendRecipe(updated.selectedMillet, updated.preference))
        }
    }
    fun setPreference(value: String) {
        _uiState.update { current ->
            val updated = current.copy(preference = value)
            updated.copy(aiSuggestion = repository.recommendRecipe(updated.selectedMillet, updated.preference))
        }
    }
    fun setTargetPrice(value: String) = _uiState.update { it.copy(targetPrice = value) }

    fun isFavorite(recipeId: String): Flow<Boolean> = repository.isFavorite(recipeId)

    fun toggleFavorite(recipe: Recipe, save: Boolean) {
        viewModelScope.launch { repository.toggleFavorite(recipe, save) }
    }

    fun updateSuggestion() {
        _uiState.update { it.copy(aiSuggestion = repository.recommendRecipe(it.selectedMillet, it.preference)) }
    }

    class Factory(private val repository: AppRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MainViewModel(repository) as T
        }
    }
}
