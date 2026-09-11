package com.example.lab04

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowColumn
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BackdropScaffold
import androidx.compose.material.BackdropValue
import androidx.compose.material.Chip
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.rememberBackdropScaffoldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.lab04.ui.theme.Lab04Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Lab04Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Lab04Theme {
        Greeting("Android")
    }
}

// Contenedores

@Composable
fun LazyColumnDemo() {
    LazyColumn(modifier = Modifier.height(150.dp)) {
        items(listOf("Elemento 1", "Elemento 2", "Elemento 3", "Elemento 4")) { item ->
            Text(text = item, modifier = Modifier.padding(8.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LazyColumnDemoPreview() { Lab04Theme { LazyColumnDemo() } }

@Composable
fun LazyRowDemo() {
    LazyRow(modifier = Modifier.fillMaxWidth()) {
        items(listOf("A", "B", "C", "D")) { item ->
            Card(modifier = Modifier.padding(4.dp)) {
                Text(text = item, modifier = Modifier.padding(16.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LazyRowDemoPreview() { Lab04Theme { LazyRowDemo() } }

@Composable
fun GridDemo() {
    Column {
        for (fila in 0 until 2) {
            Row {
                for (columna in 0 until 3) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .padding(2.dp)
                            .background(MaterialTheme.colorScheme.primary)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GridDemoPreview() { Lab04Theme { GridDemo() } }

@Composable
fun ConstraintLayoutDemo() {
    ConstraintLayout(modifier = Modifier.fillMaxWidth().height(100.dp)) {
        val (titulo, boton) = createRefs()
        Text(
            text = "Título",
            modifier = Modifier.constrainAs(titulo) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
            }
        )
        Button(
            onClick = {},
            modifier = Modifier.constrainAs(boton) {
                bottom.linkTo(parent.bottom)
                end.linkTo(parent.end)
            }
        ) { Text("Acción") }
    }
}

@Preview(showBackground = true)
@Composable
fun ConstraintLayoutDemoPreview() { Lab04Theme { ConstraintLayoutDemo() } }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarDemo() {
    TopAppBar(title = { Text("Título de la pantalla") })
}

@Preview(showBackground = true)
@Composable
fun TopAppBarDemoPreview() { Lab04Theme { TopAppBarDemo() } }

@Composable
fun FloatingActionButtonDemo() {
    FloatingActionButton(onClick = {}) {
        Icon(Icons.Filled.Add, contentDescription = "Agregar")
    }
}

@Preview(showBackground = true)
@Composable
fun FloatingActionButtonDemoPreview() { Lab04Theme { FloatingActionButtonDemo() } }

@Composable
fun ScaffoldDemo() {
    Scaffold(
        topBar = { TopAppBarDemo() },
        floatingActionButton = { FloatingActionButtonDemo() }
    ) { padding ->
        Text("Contenido de la pantalla", modifier = Modifier.padding(padding).padding(16.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun ScaffoldDemoPreview() { Lab04Theme { ScaffoldDemo() } }

@Composable
fun SurfaceDemo() {
    Surface(
        modifier = Modifier.size(100.dp),
        color = MaterialTheme.colorScheme.secondaryContainer,
        shape = RoundedCornerShape(12.dp),
        shadowElevation = 4.dp
    ) {
        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
            Text("Surface")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SurfaceDemoPreview() { Lab04Theme { SurfaceDemo() } }

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ChipDemo() {
    Chip(onClick = {}) {
        Text("Chip de ejemplo")
    }
}

@Preview(showBackground = true)
@Composable
fun ChipDemoPreview() { Lab04Theme { ChipDemo() } }

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BackdropScaffoldDemo() {
    val estado = rememberBackdropScaffoldState(BackdropValue.Concealed)
    BackdropScaffold(
        scaffoldState = estado,
        appBar = { TopAppBarDemo() },
        backLayerContent = { Text("Contenido trasero", modifier = Modifier.padding(16.dp)) },
        frontLayerContent = { Text("Contenido frontal", modifier = Modifier.padding(16.dp)) }
    )
}

@Preview(showBackground = true)
@Composable
fun BackdropScaffoldDemoPreview() { Lab04Theme { BackdropScaffoldDemo() } }

@Composable
fun FlowRowDemo() {
    FlowRow(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        repeat(6) { i ->
            Card { Text("Item $i", modifier = Modifier.padding(12.dp)) }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FlowRowDemoPreview() { Lab04Theme { FlowRowDemo() } }

@Composable
fun FlowColumnDemo() {
    FlowColumn(modifier = Modifier.height(120.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        repeat(6) { i ->
            Card { Text("Item $i", modifier = Modifier.padding(12.dp)) }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FlowColumnDemoPreview() { Lab04Theme { FlowColumnDemo() } }
