package org.mini.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.mini.getColorsTheme
import org.mini.model.PaquetesRecargas
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color

@Composable
fun MenuRecargasScreen(
    nombreCompania: String? = null,
    paquetesRecargas: List<PaquetesRecargas> = emptyList(),
    onPaquetesRecargasClick: (paquetesRecargas: PaquetesRecargas) -> Unit
) {
    // Default value if nombreCompania is null
    val nombre by remember { mutableStateOf(nombreCompania ?: "DESCONOCIDO") }

    LazyColumn(
        modifier = Modifier.fillMaxHeight(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(paquetesRecargas) { paquete ->
            // Pass the correct item to the MenuItems composable
            MenuItems(
                paquetesRecargas = paquete,
                onPaquetesRecargasClick = onPaquetesRecargasClick
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MenuItems(
    paquetesRecargas: PaquetesRecargas,
    onPaquetesRecargasClick: (paquetesRecargas: PaquetesRecargas) -> Unit
) {
    val colors = getColorsTheme() // Assuming this is necessary
    Card(
        shape = RoundedCornerShape(16.dp),
        backgroundColor = colors.colorExpenseItem,
        elevation = 8.dp,
        onClick = {
            onPaquetesRecargasClick(paquetesRecargas) // Correctly passing the parameter
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
                text = paquetesRecargas.descripcion, // Assuming "descripcion" exists
                style = MaterialTheme.typography.h6,
                textAlign = TextAlign.Center,
                modifier = Modifier.weight(1f),
                color = colors.textColor
            )
        }
    }
}
