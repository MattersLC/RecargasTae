package org.mini.dto

class XmlParser {
    fun getTRequestIDResult(xml: String): String? {
        return try {
            // Expresión regular para capturar el contenido del nodo GetTRequestIDResult
            val regex = """<GetTRequestIDResult>(.*?)</GetTRequestIDResult>""".toRegex()
            val matchResult = regex.find(xml)
            matchResult?.groups?.get(1)?.value // Devuelve el contenido del nodo o null si no se encuentra
        } catch (e: Exception) {
            println("Error al analizar el XML: ${e.message}")
            null
        }
    }
}
