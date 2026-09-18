package com.example.compra_de_entradas.data

data class CompraUiState(
    val cantidadEntradas: Int = 1,
    val extras: List<Extra> = EXTRAS_DISPONIBLES,
    val cuponSeleccionado: Cupon? = null
) {
    val subtotalEntradas: Double
        get() = cantidadEntradas * PRECIO_ENTRADA

    val subtotalExtras: Double
        get() = extras.sumOf { it.precio * it.cantidad }

    val subtotal: Double
        get() = subtotalEntradas + subtotalExtras

    val descuento: Double
        get() = cuponSeleccionado?.let { subtotal * it.porcentaje / 100.0 } ?: 0.0

    val total: Double
        get() = subtotal - descuento
}