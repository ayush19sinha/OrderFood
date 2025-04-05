package my.android.orderfood.ui.screen.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import my.android.orderfood.R

@Composable
fun QuantityButton(
    quantity: Int,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .background(
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(8.dp)
            )
            .height(36.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = onDecrement,
            modifier = Modifier.size(20.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_remove),
                contentDescription = "Decrease quantity",
                tint = Color.White
            )
        }

        Text(
            text = quantity.toString(),
            color = Color.White,
            modifier = Modifier.padding(horizontal = 8.dp)
        )

        IconButton(
            onClick = onIncrement,
            modifier = Modifier.size(20.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Increase quantity",
                tint = Color.White
            )

        }
    }
}

@Composable
fun GreenQuantityButton(
    quantity: Int,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .background(
                color = Color(0xFFCAECD5),
                shape = RoundedCornerShape(8.dp)
            )
            .height(36.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = onDecrement,
            modifier = Modifier.size(26.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_remove),
                contentDescription = "Decrease quantity",
                tint = Color(0xFF43946C)
            )
        }

        Text(
            text = quantity.toString(),
            modifier = Modifier.padding(horizontal = 8.dp)
        )

        IconButton(
            onClick = onIncrement,
            modifier = Modifier.size(26.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Increase quantity",
                tint = Color(0xFF43946C)
            )

        }
    }
}


@Preview
@Composable
private fun QuantityButtonPreview() {
    QuantityButton(quantity = 4, onDecrement = {}, onIncrement = {})
}

@Preview
@Composable
private fun GreenQuantityButtonPreview() {
    GreenQuantityButton(quantity = 4, onDecrement = {}, onIncrement = {})
}