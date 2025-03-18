package org.mini.model

// Definir los datos de los paquetes
data class MenuRecargas(val name: String, val sku: String, val monto: Int)

fun obtenerNombresDePaquetes(): List<String> {
    val paquetes = listOf(
        MenuRecargas("Telcel 20", "010200001", 20),
        MenuRecargas("Telcel 30", "010300001", 30),
        MenuRecargas("Telcel 50", "010500001", 50),
        MenuRecargas("Telcel 100", "011000001", 100),
        MenuRecargas("Telcel 150", "011500001", 150),
        MenuRecargas("Telcel 200", "012000001", 200),
        MenuRecargas("Telcel 300", "013000001", 300),
        MenuRecargas("Telcel 500", "015000001", 500)
    )
    // Solo devuelve los nombres de los paquetes
    return paquetes.map { it.name }
}