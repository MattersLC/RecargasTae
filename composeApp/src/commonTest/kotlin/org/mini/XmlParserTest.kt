package org.mini

import CompaniasRepoImpl
import io.ktor.client.HttpClient
import kotlinx.coroutines.runBlocking
import nl.adaptivity.xmlutil.serialization.XML
import org.mini.dto.XmlParser
import kotlin.test.DefaultAsserter.assertEquals
import kotlin.test.Test
import org.mini.data.CompaniasManager
import org.mini.model.DoTResult

class XmlParserTest {

    //aqui tiene que ir la invocacion para ocupar el metodo y que me devuelva el xml

    val httpClient = HttpClient()

    // Instancia de CompaniasManager y la implementación de CompaniasRepo
    val companiasManager = CompaniasManager
    val repo = CompaniasRepoImpl(companiasManager, httpClient)

    @Test
    fun `test getRecargasForPaquetes method`() = runBlocking {
        // Instancia de HttpClient
        val xmlParser = XmlParser()

        // Nombre de compañía para probar
        val nombreCompania = "TELCEL"

        // Llamar al método y obtener el resultado
        val result = repo.getRecargasForPaquetes(nombreCompania)

        if (result != null) {
            // Imprimir el resultado para verificarlo
            println("Resultado obtenido: $result")
            val recarga1 = xmlParser.parseSoapResponseToRecargasManually(result)
            println("Despues de aqui viene la recarga 0")
            println(recarga1[0].name)
        }
        else{
            println("No hay conexion con el servidor")
        }
    }

    @Test
    fun `test parseResponse with valid XML`() = runBlocking {
        val xmlParser = XmlParser()

        // XML válido (exactamente como el devuelto por la API)
        val soapResponse = """
        <?xml version="1.0" encoding="utf-8"?>
        <soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema">
            <soap:Body>
                <GetTRequestIDResponse xmlns="http://tempuri.org/">
                    <GetTRequestIDResult>3234653462</GetTRequestIDResult>
                </GetTRequestIDResponse>
            </soap:Body>
        </soap:Envelope>
    """.trimIndent()

        val getTRequestId = repo.getTRequestID()

        // Llamar al método y verificar el resultado
        val result = getTRequestId
        println("el valor es ------>"+result)
    }



    @Test
    fun `test parseResponse with invalid XML`() {
        val xmlParser = XmlParser()

        // XML mal formado para probar el manejo de errores
        val invalidSoapResponse = """
            <soap:Envelope>
                <soap:Body>
                    <GetTRequestIDResponse>
                        <InvalidNode>12345</InvalidNode>
                    </GetTRequestIDResponse>
                </soap:Body>
            </soap:Envelope>
        """.trimIndent()

        // Llamar al método y obtener el resultado
        val result = xmlParser.getTRequestIDResult(invalidSoapResponse)

        // Verificar que el resultado sea null debido a XML no válido
        assertEquals(null, result, "El método debería devolver null para XML mal formado.")
    }


    @Test
    fun `test xmlDoT`(){

        val xmlParser = XmlParser()
        val xml = """
        <?xml version="1.0" encoding="utf-8"?>
        <soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema">
            <soap:Body>
                <DoTResponse xmlns="http://tempuri.org/">
                    <DoTResult>
                        <transaction_id>0</transaction_id>
                        <rcode>3</rcode>
                        <rcode_description>Sin Saldo $0.00</rcode_description>
                        <rcode_detail>SR029A</rcode_detail>
                        <op_account>2222222222</op_account>
                        <op_authorization />
                        <printData />
                        <xmlDevData />
                    </DoTResult>
                </DoTResponse>
            </soap:Body>
        </soap:Envelope>
    """.trimIndent()

        // Carga y deserializa el XML
        val dotResult = xmlParser.parseSoapResponseToDoTResultManually(xml)
        if (dotResult != null) {
            println(dotResult.rcode_description)
        }
    }
}

