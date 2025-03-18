package org.example.project.presentacion

import kotlinx.coroutines.delay
import moe.tlaster.precompose.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import moe.tlaster.precompose.viewmodel.viewModelScope
import org.example.project.domain.CompaniasRepository
import org.mini.model.Companias
import org.mini.model.MenuRecargas


data class CompaniasUiState(
    val companias: List<Companias> = emptyList(),
    //val total: Double = 0.0
)
/*sealed class CompaniasUiState {
    object Loading : CompaniasUiState()
    data class Success(val companias: List<Companias>) : CompaniasUiState()
    data class Error(val message: String) : CompaniasUiState()
}*/

class CompaniasViewModel(private val repo: CompaniasRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(CompaniasUiState())
    val uiState = _uiState.asStateFlow()
    private val allCompanias = mutableListOf<Companias>()

    init {
        getAllCompanias()
    }

    private fun getAllCompanias() {
        viewModelScope.launch {
            _uiState.update { state ->
                state.copy(companias = allCompanias)
            }
            /*try {
                while (true) {
                    delay(1000)
                    val companias = repo.getAllCompanias()
                    _uiState.value = CompaniasUiState.Success(companias)
                }
            } catch (e: Exception) {
                _uiState.value = CompaniasUiState.Error(e.message ?: "Unknown error")
            }*/
        }
    }

    fun getCompaniasWihtName(nombre: String): Companias {
        return allCompanias.first { it.nombre==nombre }
    }

}