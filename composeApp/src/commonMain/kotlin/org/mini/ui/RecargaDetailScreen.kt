package org.mini.ui

import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import org.mini.getColorsTheme
import org.mini.model.PaquetesRecargas
import org.mini.model.Recarga

@Composable
fun RecargaDetailScreen(
    recarga: Recarga? = null,
    addRecargaAndNavigateBack: (recarga: Recarga) -> Unit,
){
    val colors = getColorsTheme()
    var name by remember { mutableStateOf(recarga?.name ?: "") }
    var sku by remember { mutableStateOf(recarga?.sku ?: "") }
    var monto by remember { mutableStateOf(recarga?.monto ?: 0) }

    var sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden
    )


}