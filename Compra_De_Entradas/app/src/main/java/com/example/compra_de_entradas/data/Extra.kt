package com.example.compra_de_entradas.data

data class Extra(
    val id: Int,
    val nombre: String,
    val precio: Double,
    val cantidad: Int = 0
)