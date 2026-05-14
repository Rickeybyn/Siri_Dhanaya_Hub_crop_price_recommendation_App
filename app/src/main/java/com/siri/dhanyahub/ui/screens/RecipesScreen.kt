package com.siri.dhanyahub.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.horizontalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.siri.dhanyahub.data.model.Recipe
import com.siri.dhanyahub.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipesScreen(viewModel: MainViewModel) {
    val uiState by viewModel.uiState.collectAsState()
    val recipes by viewModel.recipes.collectAsState()

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(bottom = 24.dp)
    ) {
        item {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text(
                    "Millet Recipe Lab",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.primary
                )
                
                OutlinedTextField(
                    value = uiState.searchQuery,
                    onValueChange = viewModel::setSearchQuery,
                    label = { Text("Search recipes by millet or title") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.medium,
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) }
                )

                RecipeComposerTools(
                    selectedMillet = uiState.selectedMillet,
                    preference = uiState.preference,
                    onMilletChange = viewModel::setSelectedMillet,
                    onPreferenceChange = viewModel::setPreference,
                    suggestion = uiState.aiSuggestion
                )
            }
        }

        items(recipes) { recipe ->
            RecipeCard(recipe = recipe, viewModel = viewModel)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RecipeComposerTools(
    selectedMillet: String,
    preference: String,
    onMilletChange: (String) -> Unit,
    onPreferenceChange: (String) -> Unit,
    suggestion: com.siri.dhanyahub.data.model.GenAiSuggestion?,
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f)),
        shape = MaterialTheme.shapes.large
    ) {
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Text("Personalize your suggestions", style = MaterialTheme.typography.titleMedium)
            
            Row(
                modifier = Modifier.horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                listOf("Navane", "Sajje", "Baragu", "Ragi", "Oodalu").forEach {
                    FilterChip(
                        selected = selectedMillet == it,
                        onClick = { onMilletChange(it) },
                        label = { Text(it) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = MaterialTheme.colorScheme.primary
                        )
                    )
                }
            }
            
            OutlinedTextField(
                value = preference,
                onValueChange = onPreferenceChange,
                label = { Text("Goal (e.g. diabetic-friendly, high-energy)") },
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.medium
            )
            
            if (suggestion != null) {
                ElevatedCard(
                    colors = CardDefaults.elevatedCardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
                            Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                            Spacer(Modifier.width(8.dp))
                            Text(suggestion.headline, style = MaterialTheme.typography.titleSmall, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                        }
                        Text(suggestion.explanation, style = MaterialTheme.typography.bodySmall)
                        if (suggestion.recipeTitle.isNotBlank()) {
                            AssistChip(
                                onClick = { },
                                label = { Text("Recommended: ${suggestion.recipeTitle}") },
                                leadingIcon = { Icon(Icons.Default.Restaurant, contentDescription = null, modifier = Modifier.size(16.dp)) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun RecipeCard(recipe: Recipe, viewModel: MainViewModel) {
    val isFav by viewModel.isFavorite(recipe.id).collectAsState(initial = false)
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Column(Modifier.weight(1f)) {
                    Text(recipe.title, style = MaterialTheme.typography.titleLarge)
                    Text(recipe.millet, style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.primary)
                }
                IconButton(onClick = { viewModel.toggleFavorite(recipe, !isFav) }) {
                    Icon(
                        imageVector = if (isFav) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = null,
                        tint = if (isFav) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline
                    )
                }
            }
            
            Text(recipe.healthNote, style = MaterialTheme.typography.bodyMedium, fontStyle = androidx.compose.ui.text.font.FontStyle.Italic)
            
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text("Ingredients", style = MaterialTheme.typography.labelLarge, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                Text(recipe.ingredients.joinToString(", "), style = MaterialTheme.typography.bodySmall)
            }
            
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text("Instructions", style = MaterialTheme.typography.labelLarge, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                recipe.steps.forEachIndexed { index, step ->
                    Text("${index + 1}. $step", style = MaterialTheme.typography.bodySmall)
                }
            }
            
            Text(
                "Availability: ${recipe.languageNote}",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
