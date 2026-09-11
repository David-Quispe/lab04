package com.example.lab04.divisorgastos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lab04.divisorgastos.domain.DivisorGastosUseCase
import com.example.lab04.divisorgastos.domain.PersonaGasto
import com.example.lab04.divisorgastos.domain.ResultadoCalculoGastos
import java.util.UUID
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * Único punto de entrada de eventos desde la UI: [onEvent]. El ViewModel nunca expone
 * funciones sueltas para cada acción, siguiendo un Flujo Unidireccional de Datos (UDF).
 */
class DivisorGastosViewModel @JvmOverloads constructor(
    private val useCase: DivisorGastosUseCase = DivisorGastosUseCase()
) : ViewModel() {

    private val _uiState = MutableStateFlow(DivisorGastosUiState())
    val uiState: StateFlow<DivisorGastosUiState> = _uiState.asStateFlow()

    fun onEvent(evento: DivisorGastosEvent) {
        when (evento) {
            is DivisorGastosEvent.AgregarPersona -> agregarPersona(evento.nombre)
            is DivisorGastosEvent.EliminarPersona -> eliminarPersona(evento.id)
            is DivisorGastosEvent.CambiarMonto ->
                _uiState.update { it.copy(monto = evento.monto, error = null, calculado = false) }
            is DivisorGastosEvent.CambiarPropina ->
                _uiState.update { it.copy(propina = evento.propina, error = null, calculado = false) }
            is DivisorGastosEvent.CambiarDescuento ->
                _uiState.update { it.copy(descuento = evento.descuento, error = null, calculado = false) }
            DivisorGastosEvent.Calcular -> calcular()
            DivisorGastosEvent.Reiniciar -> _uiState.update { DivisorGastosUiState() }
        }
    }

    private fun agregarPersona(nombre: String) {
        val nombreLimpio = nombre.trim()
        if (nombreLimpio.isEmpty()) {
            _uiState.update { it.copy(error = "El nombre de la persona no puede estar vacío.") }
            return
        }
        _uiState.update { estado ->
            estado.copy(
                personas = estado.personas + PersonaUiModel(id = UUID.randomUUID().toString(), nombre = nombreLimpio),
                error = null,
                calculado = false
            )
        }
    }

    private fun eliminarPersona(id: String) {
        _uiState.update { estado ->
            estado.copy(
                personas = estado.personas.filterNot { it.id == id },
                calculado = false
            )
        }
    }

    private fun calcular() {
        val estado = _uiState.value

        if (estado.personas.isEmpty()) {
            _uiState.update { it.copy(error = "Agrega al menos una persona antes de calcular.") }
            return
        }
        if (estado.monto.isBlank()) {
            _uiState.update { it.copy(error = "Ingresa el monto total del gasto.") }
            return
        }

        val monto = estado.monto.toDoubleOrNull()
        if (monto == null) {
            _uiState.update { it.copy(error = "El monto ingresado no es válido.") }
            return
        }

        val propina = if (estado.propina.isBlank()) 0.0 else estado.propina.toDoubleOrNull()
        if (propina == null) {
            _uiState.update { it.copy(error = "El porcentaje de propina no es válido.") }
            return
        }

        val descuento = if (estado.descuento.isBlank()) 0.0 else estado.descuento.toDoubleOrNull()
        if (descuento == null) {
            _uiState.update { it.copy(error = "El porcentaje de descuento no es válido.") }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            delay(300)

            when (
                val resultado = useCase.calcular(
                    montoTotal = monto,
                    porcentajePropina = propina,
                    porcentajeDescuento = descuento,
                    personas = estado.personas.map { PersonaGasto(id = it.id, nombre = it.nombre) }
                )
            ) {
                is ResultadoCalculoGastos.Exito -> {
                    val pagosPorId = resultado.resultado.pagos.associateBy { it.id }
                    _uiState.update { actual ->
                        actual.copy(
                            personas = actual.personas.map { persona ->
                                persona.copy(montoAPagar = pagosPorId[persona.id]?.monto)
                            },
                            isLoading = false,
                            calculado = true
                        )
                    }
                }
                is ResultadoCalculoGastos.Error -> {
                    _uiState.update { it.copy(isLoading = false, error = resultado.mensaje) }
                }
            }
        }
    }
}
