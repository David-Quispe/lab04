package com.example.lab04.divisorgastos.domain

import java.math.BigDecimal
import java.math.RoundingMode

data class PersonaGasto(val id: String, val nombre: String)

data class PagoPersona(val id: String, val nombre: String, val monto: Double)

data class ResultadoDivisionGastos(
    val pagos: List<PagoPersona>,
    val totalConAjustes: Double
)

sealed interface ResultadoCalculoGastos {
    data class Exito(val resultado: ResultadoDivisionGastos) : ResultadoCalculoGastos
    data class Error(val mensaje: String) : ResultadoCalculoGastos
}

/**
 * Lógica pura de cálculo del divisor de gastos compartidos.
 * No depende de Android ni de Compose, por lo que se puede testear con JUnit puro.
 */
class DivisorGastosUseCase {

    fun calcular(
        montoTotal: Double,
        porcentajePropina: Double,
        porcentajeDescuento: Double,
        personas: List<PersonaGasto>
    ): ResultadoCalculoGastos {
        if (personas.isEmpty()) {
            return ResultadoCalculoGastos.Error("Agrega al menos una persona para poder calcular.")
        }
        if (montoTotal < 0.0) {
            return ResultadoCalculoGastos.Error("El monto total no puede ser negativo.")
        }
        if (porcentajePropina < 0.0) {
            return ResultadoCalculoGastos.Error("El porcentaje de propina no puede ser negativo.")
        }
        if (porcentajeDescuento < 0.0) {
            return ResultadoCalculoGastos.Error("El porcentaje de descuento no puede ser negativo.")
        }

        // Se usa BigDecimal en toda la cadena (no solo al redondear) para evitar el error de
        // precisión binaria de Double, que puede desplazar valores como 103.5 a 103.49999999999999
        // y arruinar el redondeo. El descuento se aplica sobre el monto original y la propina
        // sobre el monto ya descontado.
        val cien = BigDecimal(100)
        val descuentoFraccion = BigDecimal(porcentajeDescuento.toString()).divide(cien)
        val propinaFraccion = BigDecimal(porcentajePropina.toString()).divide(cien)

        val montoConDescuento = BigDecimal(montoTotal.toString()).multiply(BigDecimal.ONE.subtract(descuentoFraccion))
        val montoFinal = montoConDescuento.multiply(BigDecimal.ONE.plus(propinaFraccion))
        val totalRedondeado = montoFinal.setScale(2, RoundingMode.HALF_UP)

        val cantidadPersonas = personas.size
        val montoBase = montoFinal.divide(BigDecimal(cantidadPersonas), 2, RoundingMode.HALF_UP)

        val pagos = MutableList(cantidadPersonas) { montoBase }
        val sumaSinUltimo = montoBase.multiply(BigDecimal(cantidadPersonas - 1))
        // Se ajusta el pago de la última persona para que la suma exacta cuadre con el total.
        pagos[cantidadPersonas - 1] = totalRedondeado.subtract(sumaSinUltimo).setScale(2, RoundingMode.HALF_UP)

        val pagosPersonas = personas.mapIndexed { index, persona ->
            PagoPersona(id = persona.id, nombre = persona.nombre, monto = pagos[index].toDouble())
        }

        return ResultadoCalculoGastos.Exito(
            ResultadoDivisionGastos(pagos = pagosPersonas, totalConAjustes = totalRedondeado.toDouble())
        )
    }
}
