package com.siri.dhanyahub.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoGraph
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.siri.dhanyahub.data.model.PriceForecast
import com.siri.dhanyahub.data.model.Trend

@Composable
fun PriceForecastCard(forecast: PriceForecast) {
    val trendColor = when (forecast.trend) {
        Trend.UP -> Color(0xFF4CAF50)
        Trend.DOWN -> Color(0xFFF44336)
        Trend.FLAT -> Color(0xFF9E9E9E)
    }

    ElevatedCard(
        modifier = Modifier
            .width(300.dp)
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.elevatedCardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(Modifier.padding(20.dp)) {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        forecast.millet,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        "7-Day Forecast",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                
                Surface(
                    color = trendColor.copy(alpha = 0.1f),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        if (forecast.trend == Trend.UP) "+Predictive" else if (forecast.trend == Trend.DOWN) "-Watchlist" else "Stable",
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        style = MaterialTheme.typography.labelSmall,
                        color = trendColor,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(Modifier.height(24.dp))

            // Custom Sparkline Chart
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(80.dp)
            ) {
                SparklineChart(
                    data = forecast.forecastPrices,
                    color = trendColor
                )
            }

            Spacer(Modifier.height(24.dp))

            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        "₹${forecast.currentPrice}",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Black
                    )
                    Text(
                        "Current Price",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                
                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        "${forecast.confidence}%",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        "Confidence",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(Modifier.height(16.dp))

            Surface(
                color = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.5f),
                shape = RoundedCornerShape(12.dp)
            ) {
                Row(
                    Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.AutoGraph,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp),
                        tint = MaterialTheme.colorScheme.secondary
                    )
                    Spacer(Modifier.width(12.dp))
                    Text(
                        forecast.recommendation,
                        style = MaterialTheme.typography.bodySmall,
                        lineHeight = androidx.compose.ui.unit.TextUnit.Unspecified
                    )
                }
            }
        }
    }
}

@Composable
private fun SparklineChart(data: List<Int>, color: Color) {
    Canvas(modifier = Modifier.fillMaxSize()) {
        if (data.size < 2) return@Canvas

        val max = data.maxOrNull()?.toFloat() ?: 1f
        val min = data.minOrNull()?.toFloat() ?: 0f
        val range = if (max - min == 0f) 1f else max - min

        val width = size.width
        val height = size.height
        val spacing = width / (data.size - 1)

        val path = Path().apply {
            data.forEachIndexed { index, value ->
                val x = index * spacing
                val y = height - ((value.toFloat() - min) / range * height)
                if (index == 0) moveTo(x, y) else lineTo(x, y)
            }
        }

        // Draw background gradient
        val fillPath = Path().apply {
            addPath(path)
            lineTo(width, height)
            lineTo(0f, height)
            close()
        }
        
        drawPath(
            path = fillPath,
            brush = Brush.verticalGradient(
                colors = listOf(color.copy(alpha = 0.3f), Color.Transparent)
            )
        )

        // Draw the line
        drawPath(
            path = path,
            color = color,
            style = Stroke(width = 3.dp.toPx())
        )
        
        // Draw the last point dot
        val lastX = width
        val lastY = height - ((data.last().toFloat() - min) / range * height)
        drawCircle(
            color = color,
            radius = 4.dp.toPx(),
            center = androidx.compose.ui.geometry.Offset(lastX, lastY)
        )
    }
}
