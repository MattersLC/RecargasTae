package org.mini.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.painterResource
import org.mini.getColorsTheme
import org.mini.model.Recarga
import recargastae.composeapp.generated.resources.Res
import recargastae.composeapp.generated.resources.pugPerrito

@Composable
fun PaquetesRecargasScreen(
    nombreCompania: String? = null,
    recarga: List<Recarga> = emptyList(),
    onRecargaClick: (recarga: Recarga) -> Unit
) {
    val colors = getColorsTheme()
    // Valor predeterminado si nombreCompania es nulo
    val nombre by remember { mutableStateOf(nombreCompania ?: "DESCONOCIDO") }

    if (recarga.isEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(Res.drawable.pugPerrito),
                    contentDescription = "Imagen de servicio no disponible",
                    modifier = Modifier
                        .size(150.dp)
                        .padding(bottom = 16.dp)
                )
                Text(
                    text = "Oops!!! Servicio no disponible, inténtelo más tarde",
                    style = MaterialTheme.typography.h5.copy(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    color = colors.textColor,
                    textAlign = TextAlign.Center
                )
            }
        }
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(recarga) { recarg ->
                ItemsRecarga(
                    recarga = recarg,
                    onRecargaClick = onRecargaClick
                )
            }
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
            .padding(horizontal = 20.dp, vertical = 8.dp)
            .height(60.dp)
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
