package org.mini.model

import kotlinx.serialization.Serializable

@Serializable
data class Recarga(
    val name: String,
    val sku: String,
    val monto: Int,
    val telefono: String = "",
    val info: String,
    val regex: String,
    val costo: Int,
    val stype: Int,
    val opid: Int
)