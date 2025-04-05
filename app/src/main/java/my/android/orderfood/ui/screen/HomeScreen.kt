package my.android.orderfood.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import my.android.orderfood.data.model.MenuItem
import my.android.orderfood.data.repository.MenuData
import my.android.orderfood.ui.screen.component.AppTopBar
import my.android.orderfood.ui.screen.component.LookingForMoreFoodCard
import my.android.orderfood.ui.screen.component.TriedTastedFoodCard
import my.android.orderfood.viewmodel.FoodOrderViewModel

@Composable
fun HomeScreen(
    viewModel: FoodOrderViewModel,
    onNavigateToCart: () -> Unit
) {
    val menuData by viewModel.menuData.collectAsState()

    Scaffold { innerPadding ->
        menuData?.let {
            HomeScreenContent(
                modifier = Modifier.padding(innerPadding),
                menuData = it,
                viewModel = viewModel,
                onNavigateToCart = onNavigateToCart
            )
        }
    }
}

@Composable
fun HomeScreenContent(
    modifier: Modifier = Modifier,
    menuData: MenuData,
    viewModel: FoodOrderViewModel,
    onNavigateToCart: () -> Unit
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item { TopBannerArea(onNavigateToCart = onNavigateToCart) }
        item {
            TriedTastedLovedSection(
                menuData = menuData,
                viewModel
            )
        }
        item {
            RiseShineDineSection(
                menuData = menuData,
                viewModel
            )
        }
        item {
            LookingForMoreSection(
                menuData = menuData,
                viewModel = viewModel
            )
            ExploreMoreButton()
        }
    }
}

@Composable
fun TopBannerArea(modifier: Modifier = Modifier,
                  onNavigateToCart: () -> Unit) {
    var isVegModeEnabled by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }

    Box(
        modifier = modifier
            .background(Color(0xFFFAE74D), RoundedCornerShape(bottomEnd = 16.dp, bottomStart = 16.dp))
            .fillMaxWidth()
            .height(360.dp)
    )
    {
        Column {
            AppTopBar(
                deliveryTime = "15 minutes",
                address = "Home 3rd Floor, Rock Castle",
                searchQuery = searchQuery,
                isVegModeEnabled = isVegModeEnabled,
                onSearchQueryChange = {searchQuery = it},
                onVegModeChange = {isVegModeEnabled = it},
                onNavigateToCart = onNavigateToCart,
            )
            AppTitleAndSlogan()
        }
    }
}

@Composable
fun AppTitleAndSlogan(modifier: Modifier = Modifier) {
    Column(modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            "HABIT",
            color = MaterialTheme.colorScheme.primary,
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            "FRESH FOOD DELIVERED IN 15 MINUTES",
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
fun SectionHeader(modifier: Modifier = Modifier, sectionTitle: String) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(sectionTitle)
        Text("View All", textDecoration = TextDecoration.Underline)
    }
}

@Composable
fun SectionWithItems(
    modifier: Modifier = Modifier,
    menuData: List<MenuItem>,
    viewModel: FoodOrderViewModel,
    sectionTitle: String
) {
    val cart by viewModel.cart.collectAsState()

    Column {
        SectionHeader(modifier, sectionTitle = sectionTitle)
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(menuData.size) { index ->
                val item = menuData[index]
                val quantity = cart.items.find { it.menuItem.id == item.id }?.quantity ?: 0
                TriedTastedFoodCard(menuItem = item,quantity = quantity,
                    onAddToCart = { viewModel.addToCart(item) },
                    onRemoveFromCart = { viewModel.removeFromCart(item) })
            }
        }
    }
}

@Composable
fun TriedTastedLovedSection(
    menuData: MenuData,
    viewModel: FoodOrderViewModel
) {
    SectionWithItems(
        menuData = menuData.triedTastedLoved,
        viewModel = viewModel,
        sectionTitle = "TRIED, TASTED & LOVED"
    )
}

@Composable
fun RiseShineDineSection(
    menuData: MenuData,
    viewModel: FoodOrderViewModel
) {
    SectionWithItems(
        menuData = menuData.triedTastedLoved,
        viewModel = viewModel,
        sectionTitle = "RISE, SHINE & DINE"
    )
}

@Composable
fun LookingForMoreSection(
    modifier: Modifier = Modifier,
    menuData: MenuData,
    viewModel: FoodOrderViewModel
) {
    val cart by viewModel.cart.collectAsState()

    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            "LOOKING FOR EVEN MORE?",
            modifier = Modifier.padding(horizontal = 12.dp)
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 400.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(menuData.lookingForMore.size) { index ->
                val item = menuData.lookingForMore[index]
                val quantity = cart.items.find { it.menuItem.id == item.id }?.quantity ?: 0

                LookingForMoreFoodCard(
                    modifier = Modifier,
                    menuItem = item,
                    quantity = quantity,
                    onAddToCart = { viewModel.addToCart(item) },
                    onRemoveFromCart = { viewModel.removeFromCart(item) }
                )
                HorizontalDivider(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    thickness = 1.dp
                )
            }
        }
    }
}

@Composable
fun ExploreMoreButton() {
    OutlinedButton(
        onClick = {},
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = Color(0xFFF6EEFE)
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text("Explore More")
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = null
            )
        }
    }
}