package com.example.compra_de_entradas.data

const val PRECIO_ENTRADA = 20.00

const val MAX_CANTIDAD = 10

val EXTRAS_DISPONIBLES = listOf(
    Extra(1, "Canchita", 8.00),
    Extra(2, "Gaseosa", 6.00),
    Extra(3,"Bocadito", 7.00 ),

)

val CUPONES_DISPONIBLES = listOf(
    Cupon("CINE10", 10),
    Cupon("ESTUDIANTE", 20)
)