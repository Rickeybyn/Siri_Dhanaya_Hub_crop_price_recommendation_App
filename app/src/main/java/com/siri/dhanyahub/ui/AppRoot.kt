package com.siri.dhanyahub.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.*
import com.siri.dhanyahub.data.model.Role
import com.siri.dhanyahub.ui.screens.*
import com.siri.dhanyahub.viewmodel.MainViewModel

private sealed class Route(val value: String) {
    data object Onboarding : Route("onboarding")
    data object Dashboard : Route("dashboard")
    data object Recipes : Route("recipes")
    data object Health : Route("health")
    data object Connect : Route("connect")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SiriDhanyaAppRoot(viewModel: MainViewModel) {
    val navController = rememberNavController()
    val uiState by viewModel.uiState.collectAsState()

    val start = if (uiState.role == null) Route.Onboarding.value else Route.Dashboard.value

    NavHost(navController = navController, startDestination = start) {
        composable(Route.Onboarding.value) {
            OnboardingScreen(
                onRoleSelected = {
                    viewModel.setRole(it)
                    navController.navigate(Route.Dashboard.value) {
                        popUpTo(Route.Onboarding.value) { inclusive = true }
                    }
                }
            )
        }
        composable(Route.Dashboard.value) {
            AppScaffold(navController, "Mandi Watch", viewModel) {
                DashboardScreen(viewModel)
            }
        }
        composable(Route.Recipes.value) {
            AppScaffold(navController, "Recipe Lab", viewModel) {
                RecipesScreen(viewModel)
            }
        }
        composable(Route.Health.value) {
            AppScaffold(navController, "Health Benefits", viewModel) {
                HealthScreen(viewModel)
            }
        }
        composable(Route.Connect.value) {
            AppScaffold(navController, "FPO Direct Connect", viewModel) {
                ConnectScreen(viewModel)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AppScaffold(
    navController: androidx.navigation.NavHostController,
    title: String,
    viewModel: MainViewModel,
    content: @Composable ColumnScope.() -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val items = listOf(
        Route.Dashboard to Icons.Default.Storefront,
        Route.Recipes to Icons.Default.MenuBook,
        Route.Health to Icons.Default.Favorite,
        Route.Connect to Icons.Default.People
    )

    Scaffold(
        topBar = {
            LargeTopAppBar(
                title = { Text(title) },
                navigationIcon = {
                    androidx.compose.foundation.Image(
                        painter = androidx.compose.ui.res.painterResource(id = com.siri.dhanyahub.R.drawable.app_logo_main),
                        contentDescription = null,
                        modifier = Modifier.size(56.dp).padding(8.dp)
                    )
                },
                actions = {
                    FilledTonalButton(
                        onClick = {
                            viewModel.setRole(null)
                            navController.navigate(Route.Onboarding.value) {
                                popUpTo(0) { inclusive = true }
                            }
                        },
                        modifier = Modifier.padding(end = 8.dp)
                    ) {
                        Icon(Icons.Default.Home, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(Modifier.width(8.dp))
                        Text("Home")
                    }

                    IconButton(onClick = { viewModel.setSearchQuery("") }) {
                        Icon(Icons.Default.Clear, contentDescription = "Clear search")
                    }
                }
            )
        },
        bottomBar = {
            NavigationBar {
                items.forEach { (route, icon) ->
                    NavigationBarItem(
                        selected = navController.currentDestination?.route == route.value,
                        onClick = {
                            navController.navigate(route.value) {
                                popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = { Icon(icon, contentDescription = null) },
                        label = { Text(route.value.replaceFirstChar { it.uppercase() }) }
                    )
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            content = content
        )
    }
}
