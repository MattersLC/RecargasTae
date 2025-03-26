package org.mini

import org.mini.dto.XmlParser
import kotlin.test.DefaultAsserter.assertEquals
import kotlin.test.Test

class XmlParserTest {

    @Test
    fun `test parseResponse with valid XML`() {
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

        // Llamar al método y verificar el resultado
        val result = xmlParser.getTRequestIDResult(soapResponse)
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
}
