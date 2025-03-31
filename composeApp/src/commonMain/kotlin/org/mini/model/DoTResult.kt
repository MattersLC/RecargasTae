package org.mini.model

// Clase de datos que representa el resultado
data class DoTResult(
    val transaction_id: Int,
    val rcode: Int,
    val rcode_description: String,
    val rcode_detail: String,
    val op_account: String,
)