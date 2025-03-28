package org.mini.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import moe.tlaster.precompose.koin.koinViewModel
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.path
import org.example.project.presentacion.CompaniasUiState
import org.example.project.presentacion.CompaniasViewModel
import org.example.project.presentacion.PaquetesRecargasUiState
import org.example.project.presentacion.RecargasUiState
import org.koin.core.parameter.parametersOf
import org.mini.getColorsTheme
import org.mini.ui.CompaniasScreen
import org.mini.ui.MenuRecargasScreen
import org.mini.ui.PaquetesRecargasScreen
@Composable
fun Navigator(navigator: Navigator) {
    val colors = getColorsTheme()

    // Configuración del ViewModel con Koin
    val viewModel = koinViewModel(CompaniasViewModel::class) { parametersOf() }

    NavHost(
        modifier = Modifier.background(colors.backgroundColor),
        navigator = navigator,
        initialRoute = "/home"
    ) {
        // Escena principal
        scene(route = "/home") {
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            // Pantalla inicial de compañías
            when (uiState) {
                is CompaniasUiState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
                is CompaniasUiState.Success -> {
                    CompaniasScreen(uiState) { compania ->
                        navigator.navigate("/menuCompania/${compania.nombre}")
                    }
                }
                is CompaniasUiState.Error -> {
                    Text(
                        text = (uiState as CompaniasUiState.Error).message,
                        color = colors.textColor,
                        style = MaterialTheme.typography.h6,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }

        // Escena para el menú de recargas
        scene(route = "/menuCompania/{nombre}") { backStackEntry ->
            val nombreCompania = backStackEntry.path<String>("nombre") ?: ""

            if (nombreCompania.isEmpty()) {
                Text(
                    text = "El nombre de la compañía no es válido.",
                    style = MaterialTheme.typography.h5.copy(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    textAlign = TextAlign.Center
                )
                return@scene
            }

            // Obtén el estado de paquetes de recargas
            val paquetesUiState by viewModel.paquetesRecargasState.collectAsState()

            LaunchedEffect(nombreCompania) {
                viewModel.getPaquetesForCompany(nombreCompania)
            }

            when (paquetesUiState) {
                is PaquetesRecargasUiState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
                is PaquetesRecargasUiState.Success -> {
                    val paquetes = (paquetesUiState as PaquetesRecargasUiState.Success).paquetesRecargas
                    MenuRecargasScreen(
                        nombreCompania = nombreCompania,
                        paquetesRecargas = paquetes
                    ) { paquete ->
                        navigator.navigate("/paquetes/${nombreCompania}")
                    }
                }
                is PaquetesRecargasUiState.Error -> {
                    Text(
                        text = (paquetesUiState as PaquetesRecargasUiState.Error).message,
                        color = colors.textColor,
                        style = MaterialTheme.typography.h6,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }

        // Escena para las recargas
        scene(route = "/paquetes/{nombre}") { backStackEntry ->
            val nombreCompania = backStackEntry.path<String>("nombre") ?: ""

            if (nombreCompania.isEmpty()) {
                Text(
                    text = "El parámetro 'nombre' es inválido.",
                    style = MaterialTheme.typography.h5.copy(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    textAlign = TextAlign.Center
                )
                return@scene
            }

            // Obtén el estado de recargas
            val recargasUiState by viewModel.recargasState.collectAsState()

            LaunchedEffect(nombreCompania) {
                viewModel.getRecargasForPaquetes(nombreCompania)
            }

            when (recargasUiState) {
                is RecargasUiState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
                is RecargasUiState.Success -> {
                    val recargas = (recargasUiState as RecargasUiState.Success).recargas
                    PaquetesRecargasScreen(
                        nombreCompania = nombreCompania,
                        recarga = recargas,
                        onRecargaClick = { recarga ->
                            println("Recarga seleccionada: ${recarga.name}")
                        }
                    )
                }
                is RecargasUiState.Error -> {
                    Text(
                        text = (recargasUiState as RecargasUiState.Error).message,
                        color = colors.textColor,
                        style = MaterialTheme.typography.h6,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}
