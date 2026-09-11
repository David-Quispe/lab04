package com.example.lab04.divisorgastos

sealed interface DivisorGastosEvent {
    data class AgregarPersona(val nombre: String) : DivisorGastosEvent
    data class EliminarPersona(val id: String) : DivisorGastosEvent
    data class CambiarMonto(val monto: String) : DivisorGastosEvent
    data class CambiarPropina(val propina: String) : DivisorGastosEvent
    data class CambiarDescuento(val descuento: String) : DivisorGastosEvent
    data object Calcular : DivisorGastosEvent
    data object Reiniciar : DivisorGastosEvent
}
