package com.example.compra_de_entradas.data

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class CompraDeEntradasViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(CompraUiState())
    val uiState: StateFlow<CompraUiState> = _uiState.asStateFlow()

    fun incrementarEntradas() {
        _uiState.update { state ->
            if (state.cantidadEntradas < MAX_CANTIDAD) {
                state.copy(cantidadEntradas = state.cantidadEntradas + 1)
            } else {
                state
            }
        }
    }

    fun decrementarEntradas() {
        _uiState.update { state ->
            if (state.cantidadEntradas > 1) {
                state.copy(cantidadEntradas = state.cantidadEntradas - 1)
            } else {
                state
            }
        }
    }

    fun incrementarExtra(extraId: Int) {
        _uiState.update { state ->
            state.copy(
                extras = state.extras.map { extra ->
                    if (extra.id == extraId && extra.cantidad < MAX_CANTIDAD) {
                        extra.copy(cantidad = extra.cantidad + 1)
                    } else {
                        extra
                    }
                }
            )
        }
    }

    fun decrementarExtra(extraId: Int) {
        _uiState.update { state ->
            state.copy(
                extras = state.extras.map { extra ->
                    if (extra.id == extraId && extra.cantidad > 0) {
                        extra.copy(cantidad = extra.cantidad - 1)
                    } else {
                        extra
                    }
                }
            )
        }
    }

    fun seleccionarCupon(cupon: Cupon?) {
        _uiState.update { state ->
            state.copy(cuponSeleccionado = cupon)
        }
    }
}