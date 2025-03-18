package org.mini.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.project.presentacion.CompaniasUiState
import org.jetbrains.compose.resources.painterResource
import org.mini.data.CompaniasManager
import org.mini.getColorsTheme
import org.mini.model.CompaniaCategory
import org.mini.model.Companias

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CompaniasScreen(uiState: CompaniasUiState, onCompaniasClick: (nombre: String) -> Unit) {
    val colors = getColorsTheme()

    LazyColumn(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp).fillMaxHeight(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        items(uiState.companias) { compania ->
            CompaniaCard(compania, onCompaniasClick = onCompaniasClick)
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CompaniaCard(companias: Companias, onCompaniasClick: (nombre: String) -> Unit) {
    val colors = getColorsTheme()
    Card(
        shape = RoundedCornerShape(16.dp),
        backgroundColor = colors.cardColor,
        elevation = 8.dp,
        onClick = {
            onCompaniasClick(companias.nombre)
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .height(130.dp)
    ) {
        // Solo se muestra la imagen dentro de la Card
        Image(
            painter = painterResource(companias.category.icon),
            contentDescription = companias.nombre,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp)) // Bordes redondeados para que coincidan con la Card
                .background(Color.Transparent) // Fondo si es necesario
        )
    }
}

