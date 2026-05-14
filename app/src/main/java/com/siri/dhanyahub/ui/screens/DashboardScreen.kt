package com.siri.dhanyahub.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.siri.dhanyahub.data.model.*
import com.siri.dhanyahub.ui.components.PriceForecastCard
import com.siri.dhanyahub.viewmodel.MainViewModel

@Composable
fun DashboardScreen(viewModel: MainViewModel) {
    val uiState by viewModel.uiState.collectAsState()
    val prices by viewModel.prices.collectAsState()
    val alertMatches by viewModel.priceAlertMatches.collectAsState()
    val contacts by viewModel.contacts.collectAsState()
    val forecasts by viewModel.marketForecasts.collectAsState()

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(bottom = 24.dp)
    ) {
        item {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text(
                    text = if (uiState.role == Role.FARMER) "Farmer's Mandi Watch" else "Consumer's Market Price",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.primary
                )
                
                OutlinedTextField(
                    value = uiState.searchQuery,
                    onValueChange = viewModel::setSearchQuery,
                    label = { Text("Search millet or mandi") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.medium,
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) }
                )

                OutlinedTextField(
                    value = uiState.targetPrice,
                    onValueChange = viewModel::setTargetPrice,
                    label = { Text(if (uiState.role == Role.FARMER) "Target Sell Price (₹/qtl)" else "Budget Buy Price (₹/qtl)") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.medium,
                    keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(
                        keyboardType = androidx.compose.ui.text.input.KeyboardType.Number
                    )
                )

                if (alertMatches.isNotEmpty()) {
                    Surface(
                        color = MaterialTheme.colorScheme.tertiaryContainer,
                        shape = MaterialTheme.shapes.medium,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            Modifier.padding(12.dp),
                            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(Icons.Default.NotificationsActive, contentDescription = null)
                            Text(
                                "Alert: ${alertMatches.size} price(s) match your ${if (uiState.role == Role.FARMER) "selling" else "buying"} goal!",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }
            }
        }

        // Market Trends & Forecast Section
        if (forecasts.isNotEmpty()) {
            item {
                Text(
                    "Market Trends & Forecast",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(Modifier.height(8.dp))
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(horizontal = 4.dp)
                ) {
                    items(forecasts) { forecast ->
                        PriceForecastCard(forecast)
                    }
                }
            }
        }

        item {
            Text(
                "Millet Prices",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        items(prices) { price ->
            MandiPriceCard(price)
        }

        if (contacts.isNotEmpty()) {
            item {
                Spacer(Modifier.height(16.dp))
                Text(
                    "Quick Support Contacts",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    "Reach out to FPOs for direct deals and support.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            items(contacts.take(2)) { contact ->
                FpoCard(contact)
            }
        }
    }
}

@Composable
private fun MandiPriceCard(price: MilletPrice) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(Modifier.padding(16.dp)) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Column(Modifier.weight(1f)) {
                    Text(
                        price.millet,
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        price.mandi,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                
                Surface(
                    color = when (price.trend) {
                        Trend.UP -> MaterialTheme.colorScheme.primaryContainer
                        Trend.DOWN -> MaterialTheme.colorScheme.errorContainer
                        Trend.FLAT -> MaterialTheme.colorScheme.secondaryContainer
                    },
                    shape = androidx.compose.foundation.shape.CircleShape
                ) {
                    Row(
                        Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = when (price.trend) {
                                Trend.UP -> Icons.Default.TrendingUp
                                Trend.DOWN -> Icons.Default.TrendingDown
                                Trend.FLAT -> Icons.Default.HorizontalRule
                            },
                            contentDescription = null,
                            modifier = Modifier.size(16.dp),
                            tint = when (price.trend) {
                                Trend.UP -> MaterialTheme.colorScheme.onPrimaryContainer
                                Trend.DOWN -> MaterialTheme.colorScheme.onErrorContainer
                                Trend.FLAT -> MaterialTheme.colorScheme.onSecondaryContainer
                            }
                        )
                        Spacer(Modifier.width(4.dp))
                        Text(
                            "₹${price.pricePerQuintal}",
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                        )
                    }
                }
            }
            
            HorizontalDivider(Modifier.padding(vertical = 12.dp), thickness = 0.5.dp)
            
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                PriceStat("7d High", "₹${price.dayHigh7}")
                PriceStat("7d Low", "₹${price.dayLow7}")
                Text(
                    price.updatedAt,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
private fun PriceStat(label: String, value: String) {
    Column {
        Text(label, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Text(value, style = MaterialTheme.typography.bodySmall, fontWeight = androidx.compose.ui.text.font.FontWeight.Medium)
    }
}
