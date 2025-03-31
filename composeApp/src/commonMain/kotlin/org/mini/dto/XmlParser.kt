package org.mini.dto

import org.mini.model.Recarga
import org.mini.model.DoTResult


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
                    val monto = productXml.substringAfter("<monto>").substringBefore("</monto>").toDouble()
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

    fun parseSoapResponseToDoTResultManually(soapXml: String): DoTResult? {
        // Paso 1: Extraer el contenido dentro de <soap:Body>
        val bodyContent = soapXml
            .substringAfter("<soap:Body>")
            .substringBefore("</soap:Body>")

        // Paso 2: Extraer el contenido dentro de <DoTResult>
        val rawContent = bodyContent
            .substringAfter("<DoTResult>")
            .substringBefore("</DoTResult>")

        return try {
            // Extraer los valores de cada campo dentro de <DoTResult>
            val transactionId = rawContent.substringAfter("<transaction_id>").substringBefore("</transaction_id>").toInt()
            val rcode = rawContent.substringAfter("<rcode>").substringBefore("</rcode>").toInt()
            val rcodeDescription = rawContent.substringAfter("<rcode_description>").substringBefore("</rcode_description>")
            val rcodeDetail = rawContent.substringAfter("<rcode_detail>").substringBefore("</rcode_detail>")
            val opAccount = rawContent.substringAfter("<op_account>").substringBefore("</op_account>")

            // Crea el objeto DoTResult y devuélvelo
            DoTResult(
                transaction_id = transactionId,
                rcode = rcode,
                rcode_description = rcodeDescription,
                rcode_detail = rcodeDetail,
                op_account = opAccount,
            )
        } catch (e: Exception) {
            println("Error procesando el nodo <DoTResult>: ${e.message}")
            null
        }
    }
}
