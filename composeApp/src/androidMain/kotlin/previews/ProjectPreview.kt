package previews


import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import org.example.project.presentacion.CompaniasUiState
import org.mini.data.CompaniasManager
import org.mini.data.PaquetesRecargasManager
import org.mini.model.PaquetesRecargas
import org.mini.ui.CompaniaCard
import org.mini.ui.CompaniasScreen
import org.mini.ui.MenuItems
import org.mini.ui.MenuRecargasScreen

@Preview(showBackground = true)
@Composable
fun CompaniasPreview() {
    val fakeCompanias = CompaniasManager.fakeCompaniasList

    CompaniasScreen(uiState = CompaniasUiState(companias = fakeCompanias), onCompaniasClick = {})
}

@Preview(showBackground = true)
@Composable
fun CompaniaCardPreview() {
    val fakeCompanias = CompaniasManager.fakeCompaniasList

    CompaniaCard(companias = fakeCompanias[0], onCompaniasClick = {})
}

@Preview(showBackground = true)
@Composable
fun MenuRecargasScreenPreview() {

    // Llamada al Composable con datos simulados
    MenuRecargasScreen(
        nombreCompania = "Compañía de ejemplo",
        paquetesRecargas = PaquetesRecargasManager.fakePaquetesRecargasList,
        onPaquetesRecargasClick = { }
    )
}

