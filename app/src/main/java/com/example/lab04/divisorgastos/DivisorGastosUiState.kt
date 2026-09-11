package com.example.lab04.divisorgastos

data class PersonaUiModel(
    val id: String,
    val nombre: String,
    val montoAPagar: Double? = null
)

data class DivisorGastosUiState(
    val personas: List<PersonaUiModel> = emptyList(),
    val monto: String = "",
    val propina: String = "",
    val descuento: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val calculado: Boolean = false
)
