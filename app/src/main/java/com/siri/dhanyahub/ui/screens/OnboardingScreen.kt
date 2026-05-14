package com.siri.dhanyahub.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.siri.dhanyahub.data.model.Role

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OnboardingScreen(onRoleSelected: (Role) -> Unit) {
    Surface(color = MaterialTheme.colorScheme.background) {
        Column(
            modifier = Modifier.fillMaxSize().padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
        ) {
            androidx.compose.foundation.Image(
                painter = androidx.compose.ui.res.painterResource(id = com.siri.dhanyahub.R.drawable.app_logo_main),
                contentDescription = null,
                modifier = Modifier.size(120.dp)
            )
            Spacer(Modifier.height(24.dp))
            Text(
                "Welcome to Siri-Dhanya Hub",
                style = MaterialTheme.typography.headlineMedium,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
            )
            Spacer(Modifier.height(12.dp))
            Text(
                "Empowering farmers and consumers through the magic of millets.",
                style = MaterialTheme.typography.bodyLarge,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(Modifier.height(48.dp))
            Text("How will you use the app today?", style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(16.dp))
            
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                RoleCard("Farmer", Role.FARMER, onRoleSelected)
                RoleCard("Consumer", Role.CONSUMER, onRoleSelected)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RoleCard(label: String, role: Role, onClick: (Role) -> Unit) {
    ElevatedCard(
        onClick = { onClick(role) },
        modifier = Modifier.width(140.dp).height(120.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
        ) {
            Icon(
                if (role == Role.FARMER) Icons.Default.Agriculture else Icons.Default.ShoppingBasket,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(Modifier.height(8.dp))
            Text(label, style = MaterialTheme.typography.labelLarge)
        }
    }
}
