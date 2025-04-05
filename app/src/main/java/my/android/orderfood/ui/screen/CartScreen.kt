package my.android.orderfood.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import my.android.orderfood.R
import my.android.orderfood.data.model.Cart
import my.android.orderfood.data.model.CartItem
import my.android.orderfood.ui.screen.component.GreenQuantityButton
import my.android.orderfood.viewmodel.FoodOrderViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(
    viewModel: FoodOrderViewModel,
    onNavigateBack: () -> Unit
) {
    val cart by viewModel.cart.collectAsState()
    Scaffold(topBar = {
        TopAppBar(title = {
            Row {
                Text("Your Cart", fontWeight = FontWeight.SemiBold)
            }
        },
            navigationIcon = {
                IconButton(onClick = onNavigateBack) {
                    Icon(Icons.AutoMirrored.Filled.KeyboardArrowLeft, contentDescription = null)
                }
            })
    }) { innerPadding ->
        CartScreenContent(modifier = Modifier.padding(innerPadding), cart, onNavigateBack)
    }

}

@Composable
fun CartScreenContent(modifier: Modifier = Modifier, cart: Cart, onNavigateBack: () -> Unit) {
    if (cart.items.isEmpty()) {
        Box(
            modifier = modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Default.ShoppingCart,
                    contentDescription = null,
                    modifier = Modifier.size(64.dp),
                    tint = Color.Gray
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Your cart is empty",
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Add some delicious items from the menu",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(24.dp))
                Button(
                    onClick = onNavigateBack,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF6D3D95)
                    )
                ) {
                    Text("Browse Menu")
                }
            }
        }
    } else {
        Box(
            modifier = modifier
                .fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 80.dp)
            ) {
                item {
                    DeliveryBanner()
                }

                items(cart.items) { cartItem ->
                    CartItemRow(cartItem = cartItem)
                }
                item { AddItemButton() }

                item {
                    HorizontalDivider(
                        modifier = Modifier.padding(
                            horizontal = 16.dp,
                            vertical = 8.dp
                        )
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(
                                text = "Total",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "Inclusive of all taxes",
                                color = Color.Gray
                            )
                        }
                        Column {
                            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                Text(
                                    text = "₹${String.format("%.2f", cart.total)}",
                                    textDecoration = TextDecoration.LineThrough
                                )

                                Text(
                                    text = "₹${String.format("%.2f", cart.discountedTotal)}",
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Row(
                                modifier = Modifier
                                    .padding(2.dp)
                                    .background(Color(0xFFCAECD5), RoundedCornerShape(8.dp))
                            ) {
                                Text(
                                    "SAVING ₹${cart.total - cart.discountedTotal}",
                                    color = Color(0xFF43946C)
                                )
                            }
                        }
                    }
                }
            }

            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(16.dp)
            ) {
                Button(
                    onClick = { },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                ) {
                    Text(
                        text = "Pay ₹${String.format("%.2f", cart.total)}",
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun DeliveryBanner() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF6EEFE))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {

            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Delivery in 10 minutes",
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}


@Composable
fun CartItemRow(cartItem: CartItem) {
    val menuItem = cartItem.menuItem

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        AsyncImage(
            model = R.drawable.non_veg_icon,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(24.dp)
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 12.dp)
        ) {
            Text(
                text = menuItem.name,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Row {
                Text("Customise Selection", color = Color.Gray)
                Icon(Icons.Default.ArrowDropDown, contentDescription = null, tint = Color.Gray)
            }
        }

        GreenQuantityButton(
            quantity = cartItem.quantity,
            onIncrement = { },
            onDecrement = { })
        Spacer(Modifier.width(18.dp))


        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "₹${menuItem.discountedPrice}",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "₹${menuItem.price}",
                style = MaterialTheme.typography.bodyMedium,
                textDecoration = TextDecoration.LineThrough,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun DeliveryInformation() {
    
}

@Composable
fun AddItemButton() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(Icons.Default.Add, contentDescription = null, tint = Color(0xFF43946C))
        Text("Add items", color = Color(0xFF43946C))
    }
}