package com.example.compra_de_entradas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.compra_de_entradas.data.CompraDeEntradasViewModel
import com.example.compra_de_entradas.data.CUPONES_DISPONIBLES
import com.example.compra_de_entradas.data.Cupon
import com.example.compra_de_entradas.data.PRECIO_ENTRADA
import com.example.compra_de_entradas.ui.components.ExtraItem
import com.example.compra_de_entradas.ui.components.QuantitySelector
import com.example.compra_de_entradas.ui.components.SectionTitle
import com.example.compra_de_entradas.ui.theme.Compra_De_EntradasTheme
import com.example.compra_de_entradas.util.formatPrice
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Compra_De_EntradasTheme {
                CompraScreen()
            }
        }
    }
}

@Composable
fun CompraScreen(viewModel: CompraDeEntradasViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Titulo()

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    SectionTitle(titleRes = R.string.section_tickets)

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = stringResource(R.string.price_per_ticket) + " " +
                                formatPrice(PRECIO_ENTRADA),
                            style = MaterialTheme.typography.bodyMedium
                        )

                        QuantitySelector(
                            cantidad = uiState.cantidadEntradas,
                            onDecrease = viewModel::decrementarEntradas,
                            onIncrease = viewModel::incrementarEntradas
                        )
                    }
                }
            }

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                SectionTitle(titleRes = R.string.section_extras)

                uiState.extras.forEach { extra ->
                    ExtraItem(
                        extra = extra,
                        onDecrease = { viewModel.decrementarExtra(extra.id) },
                        onIncrease = { viewModel.incrementarExtra(extra.id) }
                    )
                }
            }

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                SectionTitle(titleRes = R.string.section_coupon)

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    FilterChip(
                        selected = uiState.cuponSeleccionado == null,
                        onClick = { viewModel.seleccionarCupon(null) },
                        label = { Text(stringResource(R.string.no_coupon)) }
                    )

                    CUPONES_DISPONIBLES.forEach { cupon ->
                        CuponChip(
                            cupon = cupon,
                            selected = uiState.cuponSeleccionado?.codigo == cupon.codigo,
                            onClick = { viewModel.seleccionarCupon(cupon) }
                        )
                    }
                }
            }

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    FilaTotal(
                        label = stringResource(R.string.label_subtotal),
                        valor = formatPrice(uiState.subtotal)
                    )
                    FilaTotal(
                        label = stringResource(R.string.label_discount),
                        valor = formatPrice(uiState.descuento)
                    )

                    HorizontalDivider()

                    FilaTotal(
                        label = stringResource(R.string.label_total),
                        valor = formatPrice(uiState.total),
                        resaltado = true
                    )
                }
            }

            val mensajeConfirmacion = stringResource(R.string.buy_confirmation)
            Button(
                onClick = {
                    scope.launch {
                        snackbarHostState.showSnackbar(mensajeConfirmacion)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
            ) {
                Text(
                    text = stringResource(R.string.button_buy).uppercase(),
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
private fun Titulo() {
    Text(
        text = stringResource(R.string.title_purchase),
        style = MaterialTheme.typography.headlineMedium,
        fontWeight = FontWeight.Bold
    )
}

@Composable
private fun CuponChip(
    cupon: Cupon,
    selected: Boolean,
    onClick: () -> Unit
) {
    FilterChip(
        selected = selected,
        onClick = onClick,
        label = { Text("${cupon.codigo} (${cupon.porcentaje}%)") }
    )
}

@Composable
private fun FilaTotal(
    label: String,
    valor: String,
    resaltado: Boolean = false
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = if (resaltado) {
                MaterialTheme.typography.titleMedium
            } else {
                MaterialTheme.typography.bodyMedium
            },
            fontWeight = if (resaltado) FontWeight.Bold else FontWeight.Normal
        )

        Text(
            text = valor,
            style = if (resaltado) {
                MaterialTheme.typography.titleMedium
            } else {
                MaterialTheme.typography.bodyMedium
            },
            color = if (resaltado) {
                MaterialTheme.colorScheme.primary
            } else {
                MaterialTheme.colorScheme.onSurface
            },
            fontWeight = if (resaltado) FontWeight.Bold else FontWeight.Medium
        )
    }
}