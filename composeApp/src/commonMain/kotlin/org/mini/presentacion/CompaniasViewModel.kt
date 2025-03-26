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
class CompaniasViewModel(private val repo: CompaniasRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<CompaniasUiState>(CompaniasUiState.Loading)
    val uiState = _uiState.asStateFlow()
    //private val allCompanias = repo.getAllCompanias()

    init {
        getAllCompanias()
    }

    private fun getAllCompanias() {
        viewModelScope.launch {
            viewModelScope.launch {
                try {
                    while (true) {
                        delay(1000)
                        val companias = repo.getAllCompanias()
                        _uiState.value = CompaniasUiState.Success(companias)
                    }
                } catch (e: Exception) {
                    _uiState.value = CompaniasUiState.Error(e.message ?: "Unknown error")
                }
            }
            }
        }

    // Método para obtener paquetes de recargas
    suspend fun getPaquetesForCompany(nombreCompania: String): List<PaquetesRecargas> {
        return repo.getPaquetesForCompany(nombreCompania)
    }

    // Método para obtener tipos de recargas
    /*fun getRecargasForPaquetes(nombreCompania: String): String? {
        //return repo.getRecargasForPaquetes(nombreCompania)
    }*/
}
