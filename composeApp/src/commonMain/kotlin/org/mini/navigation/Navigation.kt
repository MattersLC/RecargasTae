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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.delay
import moe.tlaster.precompose.koin.koinViewModel
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.path
import org.example.project.presentacion.CompaniasUiState
import org.example.project.presentacion.CompaniasViewModel
import org.example.project.presentacion.PaquetesRecargasUiState
import org.example.project.presentacion.RecargasUiState
import org.example.project.presentacion.SolicitudRecargaUiState
import org.koin.core.parameter.parametersOf
import org.mini.getColorsTheme
import org.mini.model.DoTResult
import org.mini.ui.CompaniasScreen
import org.mini.ui.MenuRecargasScreen
import org.mini.ui.PaquetesRecargasScreen
import org.mini.ui.RecargaDetailScreen
import org.mini.ui.TicketRecargaScreen

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
        // Escena principal (Pantalla de inicio)
        scene(route = "/home") {
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

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
                    val companias = (uiState as CompaniasUiState.Success).companias
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
                    val paquetes =
                        (paquetesUiState as PaquetesRecargasUiState.Success).paquetesRecargas
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

            val recargasUiState by viewModel.recargasState.collectAsState()
            var showTimeoutMessage by remember { mutableStateOf(false) }

            LaunchedEffect(nombreCompania) {
                viewModel.getRecargasForPaquetes(nombreCompania)

                delay(10000)
                if (recargasUiState is RecargasUiState.Loading) {
                    showTimeoutMessage = true
                }
            }

            when {
                showTimeoutMessage -> {
                    PaquetesRecargasScreen(
                        nombreCompania = nombreCompania,
                        recarga = emptyList(),
                        onRecargaClick = { recarga ->
                            println("Recarga seleccionada: ${recarga.name}")
                        }
                    )
                }

                recargasUiState is RecargasUiState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                recargasUiState is RecargasUiState.Success -> {
                    val recargas = (recargasUiState as RecargasUiState.Success).recargas
                    PaquetesRecargasScreen(
                        nombreCompania = nombreCompania,
                        recarga = recargas,
                        onRecargaClick = { recarga ->
                            navigator.navigate("/recargaDetail/${recarga.sku}/${nombreCompania}")
                        }
                    )
                }

                recargasUiState is RecargasUiState.Error -> {
                    Text(
                        text = (recargasUiState as RecargasUiState.Error).message,
                        color = MaterialTheme.colors.error,
                        style = MaterialTheme.typography.h6,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }

        scene(route = "/recargaDetail/{sku}/{nombre}") { backStackEntry ->
            val sku = backStackEntry.path<String>("sku") ?: ""
            val nombreCompania = backStackEntry.path<String>("nombre") ?: "TELCEL"

            // Obtener la recarga correspondiente al SKU
            val recarga = viewModel.getRecargasWithSku(sku)

            // Observar el estado de la solicitud
            val solicitudRecargaUiState by viewModel.solicitudRecargaState.collectAsState()

            // Verificar si la recarga es nula
            if (recarga == null) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Recarga no encontrada.",
                        style = MaterialTheme.typography.h5.copy(
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        ),
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colors.error
                    )
                }
                return@scene
            }

            // Layout principal para manejar diferentes estados
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                when (solicitudRecargaUiState) {
                    is SolicitudRecargaUiState.Idle -> {
                        // Pantalla inicial con el formulario para aplicar recarga
                        RecargaDetailScreen(
                            nombreCompania = nombreCompania,
                            recargaApply = recarga,
                            onButtonClick = { selectedRecarga ->
                                // Inicia la solicitud de recarga
                                viewModel.getDoTRequest(selectedRecarga)
                            }
                        )
                    }
                    is SolicitudRecargaUiState.Loading -> {
                        // Mostrar el indicador de carga mientras se procesa la solicitud
                        CircularProgressIndicator()
                    }
                    is SolicitudRecargaUiState.Success -> {
                        // Estado exitoso: navegar al ticket
                        val result = (solicitudRecargaUiState as SolicitudRecargaUiState.Success).doTResult
                        LaunchedEffect(result) {
                            navigator.navigate(
                                "/ticketRecarga/${result.transaction_id}/${result.rcode}/${result.rcode_description}/${result.rcode_detail}/${result.op_account}"
                            )
                        }
                    }
                    is SolicitudRecargaUiState.Error -> {
                        // Mostrar un mensaje de error en caso de fallo
                        val errorMessage = (solicitudRecargaUiState as SolicitudRecargaUiState.Error).message
                        Text(
                            text = "Error: $errorMessage",
                            style = MaterialTheme.typography.body1.copy(color = MaterialTheme.colors.error),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }



        scene(route = "/ticketRecarga/{transaction_id}/{rcode}/{rcode_description}/{rcode_detail}/{op_account}") { backStackEntry ->
            val transactionId = backStackEntry.path<Int>("transaction_id") ?: 0
            val rcode = backStackEntry.path<Int>("rcode") ?: 0
            val rcodeDescription =
                backStackEntry.path<String>("rcode_description") ?: "Sin descripción"
            val rcodeDetail = backStackEntry.path<String>("rcode_detail") ?: "Sin detalles"
            val opAccount = backStackEntry.path<String>("op_account") ?: "Sin cuenta"

            val doTResult = DoTResult(
                transaction_id = transactionId,
                rcode = rcode,
                rcode_description = rcodeDescription,
                rcode_detail = rcodeDetail,
                op_account = opAccount
            )

            TicketRecargaScreen(
                doTResult = doTResult,
                navigator = navigator,
                onFinalizeClick = {
                    viewModel.clearSolicitudRecargaState()
                    navigator.navigate("/home")
                }
            )
        }
    }
}

