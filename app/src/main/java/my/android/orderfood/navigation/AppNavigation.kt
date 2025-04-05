package my.android.orderfood.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import my.android.orderfood.ui.screen.HomeScreen
import my.android.orderfood.viewmodel.FoodOrderViewModel

@Composable
fun AppNavigation(viewModel: FoodOrderViewModel) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(
                viewModel = viewModel,
                onNavigateToCart = { navController.navigate("cart") }
            )
        }
    }
}