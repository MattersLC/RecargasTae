package org.mini.navigation

import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import moe.tlaster.precompose.flow.collectAsStateWithLifecycle
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.path
import moe.tlaster.precompose.viewmodel.viewModel
import org.example.project.presentacion.CompaniasViewModel
import org.mini.data.CompaniasManager
import org.mini.data.CompaniasRepoImpl
import org.mini.getColorsTheme
import org.mini.ui.CompaniasScreen
import org.mini.ui.MenuRecargasScreen

@Composable
fun Navigator(navigator: Navigator) {
    val colors = getColorsTheme()
    val viewModel = viewModel(modelClass = CompaniasViewModel::class) {
        CompaniasViewModel(CompaniasRepoImpl(CompaniasManager))
    }

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

        // Escena para `MenuRecargasScreen`
        scene(route = "/menuCompania/{nombre}") { backStackEntry ->
            val nombreCompania = backStackEntry.path<String>("nombre")

            // Lógica para obtener paquetes de recargas desde el ViewModel
            val paquetesRecargas = remember(nombreCompania) {
                nombreCompania?.let { viewModel.getPaquetesForCompany(it) } ?: emptyList()
            }

            // Renderizar `MenuRecargasScreen`
            MenuRecargasScreen(
                nombreCompania = nombreCompania,
                paquetesRecargas = paquetesRecargas
            ) { paquete ->
                // Acción en el click de un paquete
                println("Paquete seleccionado: ${paquete.descripcion}")
            }
        }
    }
}

