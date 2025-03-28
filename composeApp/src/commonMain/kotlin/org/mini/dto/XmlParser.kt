package org.mini.dto

import org.mini.model.Recarga
import kotlin.text.RegexOption


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

    fun parseSoapResponseToRecargasManually(soapXml: String): List<Recarga> {
        // Paso 1: Decodificar el contenido del nodo GetSkuListResult
        val rawContent = soapXml
            .substringAfter("<GetSkuListResult>")
            .substringBefore("</GetSkuListResult>")
            .replace("&lt;", "<")
            .replace("&gt;", ">")

        val recargas = mutableListOf<Recarga>()

        // Paso 2: Encuentra todos los nodos <product> con expresiones regulares
        val productRegex = "<product>(.*?)</product>".toRegex()
        val matches = productRegex.findAll(rawContent)

        // Paso 3: Itera sobre cada coincidencia usando un bucle clásico
        for (match in matches) {
            val productXml = match.groups[1]?.value
            if (productXml != null) {
                try {
                    // Extrae cada campo del nodo <product>
                    val name = productXml.substringAfter("<name>").substringBefore("</name>")
                    val sku = productXml.substringAfter("<sku>").substringBefore("</sku>")
                    val monto = productXml.substringAfter("<monto>").substringBefore("</monto>").toInt()
                    val info = productXml.substringAfter("<info>").substringBefore("</info>")
                    val regex = productXml.substringAfter("<regex>").substringBefore("</regex>")
                    val costo = productXml.substringAfter("<costo>").substringBefore("</costo>").toInt()
                    val stype = productXml.substringAfter("<stype>").substringBefore("</stype>").toInt()
                    val opid = productXml.substringAfter("<opid>").substringBefore("</opid>").toInt()

                    // Crea un objeto Recarga y agrégalo a la lista
                    recargas.add(Recarga(name, sku, monto, info = info, regex = regex, costo = costo, stype = stype, opid = opid))
                } catch (e: Exception) {
                    println("Error procesando un nodo <product>: ${e.message}")
                }
            }
        }

        return recargas
    }

}
