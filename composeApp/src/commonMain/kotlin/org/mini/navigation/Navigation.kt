package org.mini.navigation

import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import moe.tlaster.precompose.flow.collectAsStateWithLifecycle
import moe.tlaster.precompose.koin.koinViewModel
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.path
import org.example.project.presentacion.CompaniasViewModel
import org.koin.core.parameter.parametersOf
import org.mini.getColorsTheme
import org.mini.model.PaquetesRecargas
import org.mini.ui.CompaniasScreen
import org.mini.ui.MenuRecargasScreen

@Composable
fun Navigator(navigator: Navigator) {
    val colors = getColorsTheme()

    // Configuración del ViewModel con HttpClient
    /*val viewModel = viewModel(modelClass = CompaniasViewModel::class) {
        // Pasamos el HttpClient al repositorio
        CompaniasViewModel(CompaniasRepoImpl(CompaniasManager, httpClient))
    }*/
    val viewModel = koinViewModel(CompaniasViewModel::class){ parametersOf() }


    NavHost(
        modifier = Modifier.background(colors.backgroundColor),
        navigator = navigator,
        initialRoute = "/home"
    ) {
        // Escena principal
        scene(route = "/home") {
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            // Pantalla inicial de compañías
            CompaniasScreen(uiState) { companias ->
                navigator.navigate("/menuCompania/${companias.nombre}")
            }
        }

        scene(route = "/menuCompania/{nombre}") { backStackEntry ->
            val nombreCompania = backStackEntry.path<String>("nombre")

            // Estado para almacenar los paquetes de recarga
            val paquetesRecargas = remember { mutableStateOf<List<PaquetesRecargas>>(emptyList()) }

            // Ejecutar el método suspend usando LaunchedEffect
            LaunchedEffect(nombreCompania) {
                nombreCompania?.let {
                    paquetesRecargas.value = viewModel.getPaquetesForCompany(it)
                }
            }

            // Renderizar `MenuRecargasScreen`
            MenuRecargasScreen(
                nombreCompania = nombreCompania,
                paquetesRecargas = paquetesRecargas.value
            ) { paquete ->
                println("Paquete seleccionado: ${paquete.descripcion}")
            }
        }

    }
}

