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
import org.mini.model.PaquetesRecargas
import org.mini.model.Recarga


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


class CompaniasViewModel(private val repo: CompaniasRepository) : ViewModel() {

    // Estado global de la UI
    private val _uiState = MutableStateFlow<CompaniasUiState>(CompaniasUiState.Loading)
    val uiState: StateFlow<CompaniasUiState> = _uiState.asStateFlow()

    // Estado específico para paquetes de recargas
    private val _paquetesRecargasState = MutableStateFlow<PaquetesRecargasUiState>(PaquetesRecargasUiState.Loading)
    val paquetesRecargasState: StateFlow<PaquetesRecargasUiState> = _paquetesRecargasState.asStateFlow()

    // Estado global de la UI
    private val _recargasState = MutableStateFlow<RecargasUiState>(RecargasUiState.Loading)
    val recargasState: StateFlow<RecargasUiState> = _recargasState.asStateFlow()


    init {
        getAllCompanias() // Llama a la función para inicializar los datos
    }

    private fun getAllCompanias() {
        viewModelScope.launch {
            try {
                delay(1000) // Simula un tiempo de carga inicial
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
                val paquetesRecargas = repo.getPaquetesForCompany(nombreCompania)
                _paquetesRecargasState.value = PaquetesRecargasUiState.Success(paquetesRecargas)
            } catch (e: Exception) {
                _uiState.value = CompaniasUiState.Error(e.message ?: "Error al obtener paquetes.")
            }
        }
    }

    fun getRecargasForPaquetes(nombreCompania: String) {
        viewModelScope.launch {
            try {
                val recargas = repo.getRecargasForPaquetes(nombreCompania)
                val xmlParser = XmlParser()
                val recargasList = recargas?.let { xmlParser.parseSoapResponseToRecargasManually(it) }
                _recargasState.value = recargasList?.let { RecargasUiState.Success(it) }!!
            } catch (e: Exception) {
                _uiState.value = CompaniasUiState.Error(e.message ?: "Error al obtener recargas.")
            }
        }
    }
}
