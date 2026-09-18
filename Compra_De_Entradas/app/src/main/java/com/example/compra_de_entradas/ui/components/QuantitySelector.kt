package com.example.compra_de_entradas.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.compra_de_entradas.R

@Composable
fun QuantitySelector(
    cantidad: Int,
    onDecrease: () -> Unit,
    onIncrease: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(
            onClick = onDecrease,
            enabled = cantidad > 0
        ) {
            Text(stringResource(R.string.symbol_minus))
        }

        Text(
            text = cantidad.toString(),
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Button(
            onClick = onIncrease
        ) {
            Text(stringResource(R.string.symbol_plus))
        }
    }
}