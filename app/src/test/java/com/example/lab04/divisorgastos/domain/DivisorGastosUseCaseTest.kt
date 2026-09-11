package com.example.lab04.divisorgastos.domain

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class DivisorGastosUseCaseTest {

    private val useCase = DivisorGastosUseCase()

    @Test
    fun `caso normal reparte el monto entre las personas y la suma cuadra con el total`() {
        val personas = listOf(
            PersonaGasto("1", "Ana"),
            PersonaGasto("2", "Luis"),
            PersonaGasto("3", "Carla")
        )

        val resultado = useCase.calcular(
            montoTotal = 100.0,
            porcentajePropina = 0.0,
            porcentajeDescuento = 0.0,
            personas = personas
        )

        require(resultado is ResultadoCalculoGastos.Exito)
        assertEquals(100.0, resultado.resultado.totalConAjustes, 0.0)
        assertEquals(listOf(33.33, 33.33, 33.34), resultado.resultado.pagos.map { it.monto })
        assertEquals(100.0, resultado.resultado.pagos.sumOf { it.monto }, 0.0001)
    }

    @Test
    fun `caso con propina y descuento juntos aplica ambos y ajusta el ultimo pago`() {
        val personas = listOf(
            PersonaGasto("1", "Ana"),
            PersonaGasto("2", "Luis"),
            PersonaGasto("3", "Carla"),
            PersonaGasto("4", "Beto")
        )

        // 100 - 10% descuento = 90; 90 + 15% propina = 103.5
        val resultado = useCase.calcular(
            montoTotal = 100.0,
            porcentajePropina = 15.0,
            porcentajeDescuento = 10.0,
            personas = personas
        )

        require(resultado is ResultadoCalculoGastos.Exito)
        assertEquals(103.5, resultado.resultado.totalConAjustes, 0.0)
        assertEquals(listOf(25.88, 25.88, 25.88, 25.86), resultado.resultado.pagos.map { it.monto })
        assertEquals(103.5, resultado.resultado.pagos.sumOf { it.monto }, 0.0001)
    }

    @Test
    fun `caso de error con 0 personas devuelve un mensaje claro`() {
        val resultado = useCase.calcular(
            montoTotal = 100.0,
            porcentajePropina = 0.0,
            porcentajeDescuento = 0.0,
            personas = emptyList()
        )

        require(resultado is ResultadoCalculoGastos.Error)
        assertTrue(resultado.mensaje.isNotBlank())
    }

    @Test
    fun `caso de error con monto negativo devuelve un mensaje claro`() {
        val resultado = useCase.calcular(
            montoTotal = -50.0,
            porcentajePropina = 0.0,
            porcentajeDescuento = 0.0,
            personas = listOf(PersonaGasto("1", "Ana"))
        )

        require(resultado is ResultadoCalculoGastos.Error)
        assertTrue(resultado.mensaje.isNotBlank())
    }
}
