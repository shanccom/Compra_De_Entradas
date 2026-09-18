package com.example.compra_de_entradas.util

import java.util.Locale

fun formatPrice(precio: Double): String =
    String.format(Locale.ROOT, "S/ %.2f", precio)