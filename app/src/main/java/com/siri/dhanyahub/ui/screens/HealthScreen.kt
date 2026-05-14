package com.siri.dhanyahub.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.siri.dhanyahub.viewmodel.MainViewModel

@Composable
fun HealthScreen(viewModel: MainViewModel) {
    val uiState by viewModel.uiState.collectAsState()
    val cards by viewModel.healthCards.collectAsState()

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(bottom = 24.dp)
    ) {
        item {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text(
                    "Millet Health Benefits",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.primary
                )
                
                OutlinedTextField(
                    value = uiState.searchQuery,
                    onValueChange = viewModel::setSearchQuery,
                    label = { Text("Search benefits (e.g. iron, fibre)") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.medium,
                    leadingIcon = { Icon(Icons.Default.HealthAndSafety, contentDescription = null) }
                )
            }
        }

        items(cards) { card ->
            HealthBenefitCard(card)
        }
    }
}

@Composable
private fun HealthBenefitCard(card: com.siri.dhanyahub.data.repository.AppRepository.MilletHealthCard) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
                Surface(
                    color = MaterialTheme.colorScheme.primaryContainer,
                    shape = androidx.compose.foundation.shape.CircleShape,
                    modifier = Modifier.size(40.dp)
                ) {
                    Box(contentAlignment = androidx.compose.ui.Alignment.Center) {
                        Text(
                            card.millet.take(1),
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                }
                Spacer(Modifier.width(12.dp))
                Text(card.millet, style = MaterialTheme.typography.titleLarge)
            }
            
            Text(card.benefits, style = MaterialTheme.typography.bodyLarge)
            
            Surface(
                color = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.5f),
                shape = MaterialTheme.shapes.small
            ) {
                Text(
                    card.note,
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
            }
        }
    }
}
