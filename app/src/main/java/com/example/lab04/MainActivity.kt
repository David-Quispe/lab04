package com.example.lab04

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BackdropScaffold
import androidx.compose.material.BackdropValue
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Chip
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.rememberBackdropScaffoldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.PlainTooltip
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.lab04.divisorgastos.DivisorGastosScreen
import com.example.lab04.ui.theme.Lab04Theme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Lab04Theme {
                DivisorGastosScreen()
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

// Controles 1

@Composable
fun AlertDialogDemo() {
    var mostrar by remember { mutableStateOf(true) }
    if (mostrar) {
        AlertDialog(
            onDismissRequest = { mostrar = false },
            title = { Text("Título") },
            text = { Text("Este es el contenido del AlertDialog.") },
            confirmButton = { TextButtonDemoInline("Aceptar") { mostrar = false } },
            dismissButton = { TextButtonDemoInline("Cancelar") { mostrar = false } }
        )
    }
}

@Composable
private fun TextButtonDemoInline(texto: String, onClick: () -> Unit) {
    Button(onClick = onClick) { Text(texto) }
}

@Preview(showBackground = true)
@Composable
fun AlertDialogDemoPreview() { Lab04Theme { AlertDialogDemo() } }

@Composable
fun CardDemo() {
    Card(modifier = Modifier.padding(8.dp)) {
        Text("Contenido dentro de una Card", modifier = Modifier.padding(16.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun CardDemoPreview() { Lab04Theme { CardDemo() } }

@Composable
fun CheckboxDemo() {
    var marcado by remember { mutableStateOf(true) }
    Row(verticalAlignment = Alignment.CenterVertically) {
        Checkbox(checked = marcado, onCheckedChange = { marcado = it })
        Text("Acepto los términos")
    }
}

@Preview(showBackground = true)
@Composable
fun CheckboxDemoPreview() { Lab04Theme { CheckboxDemo() } }

@Composable
fun IconDemo() {
    Icon(
        imageVector = Icons.Filled.Favorite,
        contentDescription = "Favorito",
        tint = Color.Red,
        modifier = Modifier.size(48.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun IconDemoPreview() { Lab04Theme { IconDemo() } }

@Composable
fun ImageDemo() {
    Image(
        imageVector = Icons.Filled.Star,
        contentDescription = "Imagen de ejemplo",
        modifier = Modifier.size(64.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun ImageDemoPreview() { Lab04Theme { ImageDemo() } }

@Composable
fun ProgressBarDemo() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        LinearProgressIndicator(progress = { 0.6f }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(12.dp))
        CircularProgressIndicator()
    }
}

@Preview(showBackground = true)
@Composable
fun ProgressBarDemoPreview() { Lab04Theme { ProgressBarDemo() } }

@Composable
fun RadioButtonDemo() {
    var seleccionado by remember { mutableIntStateOf(0) }
    Column {
        listOf("Opción 1", "Opción 2").forEachIndexed { index, texto ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(selected = seleccionado == index, onClick = { seleccionado = index })
                Text(texto)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RadioButtonDemoPreview() { Lab04Theme { RadioButtonDemo() } }

@Composable
fun SliderDemo() {
    var valor by remember { mutableStateOf(0.5f) }
    Slider(value = valor, onValueChange = { valor = it })
}

@Preview(showBackground = true)
@Composable
fun SliderDemoPreview() { Lab04Theme { SliderDemo() } }

@Composable
fun SpacerDemo() {
    Row {
        Text("Izquierda")
        Spacer(modifier = Modifier.width(32.dp))
        Text("Derecha")
    }
}

@Preview(showBackground = true)
@Composable
fun SpacerDemoPreview() { Lab04Theme { SpacerDemo() } }

@Composable
fun SwitchDemo() {
    var activo by remember { mutableStateOf(true) }
    Switch(checked = activo, onCheckedChange = { activo = it })
}

@Preview(showBackground = true)
@Composable
fun SwitchDemoPreview() { Lab04Theme { SwitchDemo() } }

// Controles 2

@Composable
fun BottomNavigationDemo() {
    var seleccionado by remember { mutableIntStateOf(0) }
    BottomNavigation {
        BottomNavigationItem(
            selected = seleccionado == 0,
            onClick = { seleccionado = 0 },
            icon = { Icon(Icons.Filled.Home, contentDescription = "Inicio") },
            label = { Text("Inicio") }
        )
        BottomNavigationItem(
            selected = seleccionado == 1,
            onClick = { seleccionado = 1 },
            icon = { Icon(Icons.Filled.Person, contentDescription = "Perfil") },
            label = { Text("Perfil") }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BottomNavigationDemoPreview() { Lab04Theme { BottomNavigationDemo() } }

@Composable
fun DialogDemo() {
    var mostrar by remember { mutableStateOf(true) }
    if (mostrar) {
        androidx.compose.ui.window.Dialog(onDismissRequest = { mostrar = false }) {
            Surface(shape = RoundedCornerShape(12.dp)) {
                Text("Contenido personalizado", modifier = Modifier.padding(24.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DialogDemoPreview() { Lab04Theme { DialogDemo() } }

@Composable
fun DividerDemo() {
    Column {
        Text("Sección 1")
        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
        Text("Sección 2")
    }
}

@Preview(showBackground = true)
@Composable
fun DividerDemoPreview() { Lab04Theme { DividerDemo() } }

@Composable
fun DropDownMenuDemo() {
    var expandido by remember { mutableStateOf(true) }
    Box {
        Button(onClick = { expandido = true }) { Text("Abrir menú") }
        DropdownMenu(expanded = expandido, onDismissRequest = { expandido = false }) {
            DropdownMenuItem(text = { Text("Opción A") }, onClick = { expandido = false })
            DropdownMenuItem(text = { Text("Opción B") }, onClick = { expandido = false })
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DropDownMenuDemoPreview() { Lab04Theme { DropDownMenuDemo() } }

@Composable
fun LazyVerticalGridDemo() {
    LazyVerticalGrid(columns = GridCells.Fixed(3), modifier = Modifier.height(150.dp)) {
        items((1..9).toList()) { numero ->
            Box(
                modifier = Modifier
                    .padding(4.dp)
                    .size(40.dp)
                    .background(MaterialTheme.colorScheme.tertiaryContainer),
                contentAlignment = Alignment.Center
            ) { Text("$numero") }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LazyVerticalGridDemoPreview() { Lab04Theme { LazyVerticalGridDemo() } }

@Composable
fun NavigationRailDemo() {
    var seleccionado by remember { mutableIntStateOf(0) }
    NavigationRail {
        NavigationRailItem(
            selected = seleccionado == 0,
            onClick = { seleccionado = 0 },
            icon = { Icon(Icons.Filled.Home, contentDescription = "Inicio") },
            label = { Text("Inicio") }
        )
        NavigationRailItem(
            selected = seleccionado == 1,
            onClick = { seleccionado = 1 },
            icon = { Icon(Icons.Filled.Settings, contentDescription = "Ajustes") },
            label = { Text("Ajustes") }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun NavigationRailDemoPreview() { Lab04Theme { NavigationRailDemo() } }

@Composable
fun OutlinedTextFieldDemo() {
    var texto by remember { mutableStateOf("") }
    OutlinedTextField(value = texto, onValueChange = { texto = it }, label = { Text("Nombre") })
}

@Preview(showBackground = true)
@Composable
fun OutlinedTextFieldDemoPreview() { Lab04Theme { OutlinedTextFieldDemo() } }

@Composable
fun PagerDemo() {
    val estado = rememberPagerState(pageCount = { 3 })
    HorizontalPager(state = estado, modifier = Modifier.height(100.dp)) { pagina ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.primaryContainer),
            contentAlignment = Alignment.Center
        ) { Text("Página $pagina") }
    }
}

@Preview(showBackground = true)
@Composable
fun PagerDemoPreview() { Lab04Theme { PagerDemo() } }

@Composable
fun SnackbarDemo() {
    val estadoSnackbar = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    Column {
        Button(onClick = { scope.launch { estadoSnackbar.showSnackbar("Mensaje de ejemplo") } }) {
            Text("Mostrar Snackbar")
        }
        SnackbarHost(hostState = estadoSnackbar)
    }
}

@Preview(showBackground = true)
@Composable
fun SnackbarDemoPreview() {
    Lab04Theme {
        Snackbar { Text("Este es un Snackbar") }
    }
}

@Composable
fun TabRowDemo() {
    var seleccionada by remember { mutableIntStateOf(0) }
    TabRow(selectedTabIndex = seleccionada) {
        listOf("Tab 1", "Tab 2", "Tab 3").forEachIndexed { index, titulo ->
            Tab(
                selected = seleccionada == index,
                onClick = { seleccionada = index },
                text = { Text(titulo) }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TabRowDemoPreview() { Lab04Theme { TabRowDemo() } }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TooltipDemo() {
    val estadoTooltip = rememberTooltipState()
    TooltipBox(
        positionProvider = TooltipDefaults.rememberPlainTooltipPositionProvider(),
        tooltip = { PlainTooltip { Text("Texto de ayuda") } },
        state = estadoTooltip
    ) {
        Icon(Icons.Filled.Menu, contentDescription = "Tooltip")
    }
}

@Preview(showBackground = true)
@Composable
fun TooltipDemoPreview() { Lab04Theme { TooltipDemo() } }


