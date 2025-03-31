package org.example.project.presentacion

import kotlinx.coroutines.delay
import moe.tlaster.precompose.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import moe.tlaster.precompose.viewmodel.viewModelScope
import org.example.project.domain.CompaniasRepository
import org.mini.dto.XmlParser
import org.mini.model.Companias
import org.mini.model.DoTResult
import org.mini.model.PaquetesRecargas
import org.mini.model.Recarga
import recargastae.composeapp.generated.resources.Res


/*data class CompaniasUiState(
    val companias: List<Companias> = emptyList(),
    //val total: Double = 0.0
)*/
sealed class CompaniasUiState {
    object Loading : CompaniasUiState()
    data class Success(val companias: List<Companias>) : CompaniasUiState()
    data class Error(val message: String) : CompaniasUiState()
}

sealed class PaquetesRecargasUiState {
    object Loading : PaquetesRecargasUiState()
    data class Success(val paquetesRecargas: List<PaquetesRecargas>) : PaquetesRecargasUiState()
    data class Error(val message: String) : PaquetesRecargasUiState()
}

sealed class RecargasUiState {
    object Loading : RecargasUiState()
    data class Success(val recargas: List<Recarga>) : RecargasUiState()
    data class Error(val message: String) : RecargasUiState()
}

sealed class SolicitudRecargaUiState {
    object Idle : SolicitudRecargaUiState() // Estado inicial sin acciones
    object Loading : SolicitudRecargaUiState() // Mostrar el CircularProgressIndicator
    data class Success(val doTResult: DoTResult) : SolicitudRecargaUiState()
    data class Error(val message: String) : SolicitudRecargaUiState()
}

class CompaniasViewModel(private val repo: CompaniasRepository) : ViewModel() {

    // Estado global de la UI
    private val _uiState = MutableStateFlow<CompaniasUiState>(CompaniasUiState.Loading)
    val uiState: StateFlow<CompaniasUiState> = _uiState.asStateFlow()

    // Estado específico para paquetes de recargas
    private val _paquetesRecargasState =
        MutableStateFlow<PaquetesRecargasUiState>(PaquetesRecargasUiState.Loading)
    val paquetesRecargasState: StateFlow<PaquetesRecargasUiState> =
        _paquetesRecargasState.asStateFlow()

    // Estado para las recargas
    private val _recargasState = MutableStateFlow<RecargasUiState>(RecargasUiState.Loading)
    val recargasState: StateFlow<RecargasUiState> = _recargasState.asStateFlow()

    // Estado para el resultado de la solicitud DoT
    private val _solicitudRecargaState =
        MutableStateFlow<SolicitudRecargaUiState>(SolicitudRecargaUiState.Idle)
    val solicitudRecargaState: StateFlow<SolicitudRecargaUiState> = _solicitudRecargaState.asStateFlow()

    init {
        getAllCompanias()
    }

    private fun getAllCompanias() {
        viewModelScope.launch {
            try {
                delay(1000) // Simulación de espera
                val companias = repo.getAllCompanias()
                _uiState.value = CompaniasUiState.Success(companias)
            } catch (e: Exception) {
                _uiState.value = CompaniasUiState.Error(e.message ?: "Error al obtener compañías.")
            }
        }
    }

    fun getPaquetesForCompany(nombreCompania: String) {
        viewModelScope.launch {
            try {
                delay(1000) // Simulación de espera
                val paquetesRecargas = repo.getPaquetesForCompany(nombreCompania)
                _paquetesRecargasState.value = PaquetesRecargasUiState.Success(paquetesRecargas)
            } catch (e: Exception) {
                _paquetesRecargasState.value =
                    PaquetesRecargasUiState.Error(e.message ?: "Error al obtener paquetes.")
            }
        }
    }

    fun getRecargasForPaquetes(nombreCompania: String) {
        viewModelScope.launch {
            try {
                delay(1000) // Simulación de espera
                val recargasXml = repo.getRecargasForPaquetes(nombreCompania)
                val xmlParser = XmlParser()
                val recargasList =
                    recargasXml?.let { xmlParser.parseSoapResponseToRecargasManually(it) }

                if (recargasList != null) {
                    _recargasState.value = RecargasUiState.Success(recargasList)
                } else {
                    _recargasState.value = RecargasUiState.Error("No se encontraron recargas.")
                }
            } catch (e: Exception) {
                _recargasState.value =
                    RecargasUiState.Error(e.message ?: "Error al obtener recargas.")
            }
        }
    }

    fun getRecargasWithSku(sku: String): Recarga? {
        val recargas = (recargasState.value as? RecargasUiState.Success)?.recargas
        return recargas?.find { it.sku == sku }
    }

    fun getDoTRequest(recarga: Recarga) {
        viewModelScope.launch {
            try {
                // Activar estado de cargando
                _solicitudRecargaState.value = SolicitudRecargaUiState.Loading

                // Simular un tiempo de espera antes de la solicitud (opcional)
                delay(500) // Para mostrar el indicador de carga un poco más

                // Realizar la solicitud al repositorio
                val result = repo.getDoT(recarga)

                // Simular carga para pruebas de UX
                delay(1000)

                // Actualizar el estado dependiendo del resultado
                if (result != null) {
                    _solicitudRecargaState.value = SolicitudRecargaUiState.Success(result)
                } else {
                    _solicitudRecargaState.value = SolicitudRecargaUiState.Error("Error al procesar la recarga.")
                }
            } catch (e: Exception) {
                _solicitudRecargaState.value = SolicitudRecargaUiState.Error(e.message ?: "Error desconocido.")
            }
        }
    }



    fun clearSolicitudRecargaState() {
        _solicitudRecargaState.value = SolicitudRecargaUiState.Idle // Restablecer el estado inicial
    }
}
