package org.mini.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import org.mini.getColorsTheme
import org.mini.model.Recarga


@Composable
fun RecargaDetailScreen(
    nombreCompania: String,
    recargaApply: Recarga? = null,
    onButtonClick: (Recarga) -> Unit // Se define el callback con tipo correcto
) {
    val colors = getColorsTheme()
    val nombre by remember { mutableStateOf(nombreCompania) }

    // Recordar los valores iniciales de los detalles de la recarga
    var name by remember { mutableStateOf(recargaApply?.name ?: "") }
    var sku by remember { mutableStateOf(recargaApply?.sku ?: "") }
    var monto by remember { mutableStateOf(recargaApply?.monto ?: 0.0) }

    var numeroTelefono1 by remember { mutableStateOf("") }
    var numeroTelefono2 by remember { mutableStateOf("") }
    var isButtonEnabled by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Encabezado de detalles de la recarga
        Text(
            text = "Detalles de la Recarga",
            style = MaterialTheme.typography.h5.copy(
                fontWeight = FontWeight.Bold,
                color = colors.textColor
            ),
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = "Nombre: $name",
            style = MaterialTheme.typography.body1,
            color = colors.textColor,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        Text(
            text = "SKU: $sku",
            style = MaterialTheme.typography.body1,
            color = colors.textColor,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        Text(
            text = "Monto: $$monto",
            style = MaterialTheme.typography.body1,
            color = colors.textColor,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Campo para el primer número de teléfono (10 dígitos máximo)
        OutlinedTextField(
            value = numeroTelefono1,
            onValueChange = {
                if (it.length <= 10 && it.all { char -> char.isDigit() }) {
                    numeroTelefono1 = it
                }
            },
            label = { Text("Número de teléfono") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                textColor = colors.textColor,
                focusedBorderColor = colors.purple,
                unfocusedBorderColor = colors.textColor
            )
        )

        // Campo para confirmar el número
        OutlinedTextField(
            value = numeroTelefono2,
            onValueChange = {
                if (it.length <= 10 && it.all { char -> char.isDigit() }) {
                    numeroTelefono2 = it

                    // Validación de los números
                    if (numeroTelefono1 == numeroTelefono2 && numeroTelefono1.isNotEmpty()) {
                        errorMessage = ""
                        isButtonEnabled = true
                    } else {
                        errorMessage = "Los números telefónicos no coinciden."
                        isButtonEnabled = false
                    }
                }
            },
            label = { Text("Confirmar número de teléfono") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                textColor = colors.textColor,
                focusedBorderColor = colors.purple,
                unfocusedBorderColor = colors.textColor
            )
        )

        // Mostrar mensaje de error si hay problemas con los números
        if (errorMessage.isNotEmpty()) {
            Text(
                text = errorMessage,
                color = MaterialTheme.colors.error,
                style = MaterialTheme.typography.body2,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        // Botón para aplicar la recarga
        Button(
            onClick = {
                // Crear un objeto Recarga para pasarlo al callback
                val recarga = Recarga(
                    name = name,
                    sku = sku,
                    monto = monto,
                    telefono = numeroTelefono2,
                    info = "",
                    regex = "",
                    costo = 0,
                    stype = 0,
                    opid = 0
                )
                onButtonClick(recarga) // Llamar al callback con el objeto creado
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            enabled = isButtonEnabled, // Habilitar el botón solo si los números coinciden
            colors = ButtonDefaults.buttonColors(
                backgroundColor = if (isButtonEnabled) colors.purple else Color.Gray,
                contentColor = colors.textColor
            )
        ) {
            Text("Aplicar Recarga", style = MaterialTheme.typography.button)
        }
    }
}
