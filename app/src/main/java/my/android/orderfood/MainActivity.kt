package my.android.orderfood

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import my.android.orderfood.data.repository.MenuRepository
import my.android.orderfood.navigation.AppNavigation
import my.android.orderfood.ui.theme.OrderFoodTheme
import my.android.orderfood.viewmodel.FoodOrderViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            OrderFoodTheme(darkTheme = false, dynamicColor = false) {
                val repository = MenuRepository(applicationContext)
                val viewModel: FoodOrderViewModel = viewModel {
                    FoodOrderViewModel(repository)
                }
                AppNavigation(viewModel)
            }
        }
    }
}