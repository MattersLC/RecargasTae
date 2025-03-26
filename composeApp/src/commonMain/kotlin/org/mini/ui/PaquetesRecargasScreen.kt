package org.mini.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.mini.getColorsTheme
import org.mini.model.Recarga

@Composable
fun PaquetesRecargasScreen(
    nombreCompania: String? = null,
    recarga: List<Recarga> = emptyList(),
    onRecargaClick: (recarga: Recarga) -> Unit
) {
    // Default value if nombreCompania is null
    val nombre by remember { mutableStateOf(nombreCompania ?: "DESCONOCIDO") }

    LazyColumn(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp).fillMaxHeight(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(recarga) { recarg ->
            // Pass the correct item to the MenuItems composable
            ItemsRecarga(
                recarga = recarg,
                onRecargaClick = onRecargaClick
            )
        }
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ItemsRecarga(
    recarga: Recarga,
    onRecargaClick: (recarga: Recarga) -> Unit
) {
    val colors = getColorsTheme() // Assuming this is necessary
    Card(
        shape = RoundedCornerShape(16.dp),
        backgroundColor = colors.colorExpenseItem,
        elevation = 8.dp,
        onClick = {
            onRecargaClick(recarga) // Correctly passing the parameter
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .height(130.dp)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = recarga.name, // Assuming "descripcion" exists
                style = MaterialTheme.typography.h6,
                textAlign = TextAlign.Center,
                modifier = Modifier.weight(1f),
                color = colors.textColor
            )
        }
    }
}
