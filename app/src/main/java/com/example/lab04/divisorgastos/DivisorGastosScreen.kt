package com.example.lab04.divisorgastos

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.lab04.ui.theme.Lab04Theme
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DivisorGastosScreen(
    viewModel: DivisorGastosViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = { TopAppBar(title = { Text("Divisor de Gastos Compartidos") }) }
    ) { innerPadding ->
        DivisorGastosContenido(
            uiState = uiState,
            onEvent = viewModel::onEvent,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        )
    }
}

@Composable
private fun DivisorGastosContenido(
    uiState: DivisorGastosUiState,
    onEvent: (DivisorGastosEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    var nombreNuevaPersona by remember { mutableStateOf("") }

    Column(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(
                value = nombreNuevaPersona,
                onValueChange = { nombreNuevaPersona = it },
                label = { Text("Nombre de la persona") },
                modifier = Modifier.weight(1f)
            )
            Button(onClick = {
                onEvent(DivisorGastosEvent.AgregarPersona(nombreNuevaPersona))
                nombreNuevaPersona = ""
            }) {
                Text("Agregar")
            }
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f, fill = false),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(uiState.personas, key = { it.id }) { persona ->
                Card(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(persona.nombre, style = MaterialTheme.typography.bodyLarge)
                            if (persona.montoAPagar != null) {
                                Text(
                                    text = "Debe pagar: ${String.format(Locale.US, "%.2f", persona.montoAPagar)}",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }
                        IconButton(onClick = { onEvent(DivisorGastosEvent.EliminarPersona(persona.id)) }) {
                            Icon(Icons.Filled.Delete, contentDescription = "Eliminar a ${persona.nombre}")
                        }
                    }
                }
            }
        }

        OutlinedTextField(
            value = uiState.monto,
            onValueChange = { onEvent(DivisorGastosEvent.CambiarMonto(it)) },
            label = { Text("Monto total del gasto") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = uiState.propina,
            onValueChange = { onEvent(DivisorGastosEvent.CambiarPropina(it)) },
            label = { Text("Propina % (opcional)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = uiState.descuento,
            onValueChange = { onEvent(DivisorGastosEvent.CambiarDescuento(it)) },
            label = { Text("Descuento/cupón % (opcional)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            modifier = Modifier.fillMaxWidth()
        )

        if (uiState.isLoading) {
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
        }

        if (uiState.error != null) {
            Text(
                text = uiState.error,
                color = Color.Red,
                style = MaterialTheme.typography.bodyMedium
            )
        }

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(
                onClick = { onEvent(DivisorGastosEvent.Calcular) },
                enabled = !uiState.isLoading,
                modifier = Modifier.weight(1f)
            ) {
                Text("Calcular")
            }
            OutlinedButton(
                onClick = { onEvent(DivisorGastosEvent.Reiniciar) },
                modifier = Modifier.weight(1f)
            ) {
                Text("Reiniciar")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DivisorGastosScreenPreview() {
    Lab04Theme {
        DivisorGastosContenido(
            uiState = DivisorGastosUiState(
                personas = listOf(
                    PersonaUiModel(id = "1", nombre = "Ana", montoAPagar = 33.33),
                    PersonaUiModel(id = "2", nombre = "Luis", montoAPagar = 33.34)
                ),
                monto = "100",
                calculado = true
            ),
            onEvent = {}
        )
    }
}
