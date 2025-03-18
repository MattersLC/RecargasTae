package previews


import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import org.mini.model.CompaniaCategory
import org.mini.model.Companias
import org.mini.ui.CompaniaCard

@Preview(showBackground = true)
@Composable
fun CompaniaCardPreview() {

    CompaniaCard(
        companias = Companias(
            id = 1,
            nombre = "Telcel",
            category = CompaniaCategory.TELCEL
        ), onCompaniasClick = {}
    )

}
