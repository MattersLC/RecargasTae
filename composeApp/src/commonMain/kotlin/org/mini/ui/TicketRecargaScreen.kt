package org.mini.ui

import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material.Text
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.text.font.FontWeight
import org.mini.model.DoTResult
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import moe.tlaster.precompose.navigation.Navigator

@Composable
fun TicketRecargaScreen(
    doTResult: DoTResult,
    navigator: Navigator,
    onFinalizeClick: () -> Unit // Callback para manejar la navegación y limpieza del estado
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "Ticket de Recarga",
            style = MaterialTheme.typography.h5.copy(
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Mostrar los valores de DoTResult
        Text(text = "Código Transacción: ${doTResult.transaction_id}", style = MaterialTheme.typography.body1)
        Text(text = "Código: ${doTResult.rcode}", style = MaterialTheme.typography.body1)
        Text(text = "Descripción: ${doTResult.rcode_description}", style = MaterialTheme.typography.body1)
        Text(text = "Detalle de la respuesta: ${doTResult.rcode_detail}", style = MaterialTheme.typography.body1)
        Text(text = "Teléfono: ${doTResult.op_account}", style = MaterialTheme.typography.body1)

        Spacer(modifier = Modifier.height(24.dp))

        // Botón para finalizar o volver a la pantalla principal
        Button(
            onClick = {
                onFinalizeClick() // Llama al callback para restablecer el estado y navegar
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Finalizar", style = MaterialTheme.typography.button)
        }
    }
}
