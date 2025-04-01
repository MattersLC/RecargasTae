package previews


import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import org.example.project.presentacion.CompaniasUiState
import org.mini.data.CompaniasManager
import org.mini.data.PaquetesRecargasManager
import org.mini.model.Recarga
import org.mini.ui.CompaniaCard
import org.mini.ui.CompaniasScreen
import org.mini.ui.ItemsRecarga
import org.mini.ui.MenuRecargasScreen

@Preview(showBackground = true)
@Composable
fun CompaniasPreview() {
    val fakeCompanias = CompaniasManager.fakeCompaniasList

    // Utiliza un estado específico de éxito para la previsualización
    val uiState = CompaniasUiState.Success(fakeCompanias)

    //CompaniasScreen(uiState = uiState, onCompaniasClick = {})
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

@Preview(showBackground = true)
@Composable
fun PaquetesRecargasScreenPreview() {
    ItemsRecarga(Recarga("Telcel 10","10111110",10.0,"","","",0,0,0),onRecargaClick = {})
}



