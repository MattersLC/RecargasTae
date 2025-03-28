import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.client.plugins.*
import io.ktor.http.*
import kotlinx.coroutines.*
import org.example.project.domain.CompaniasRepository
import org.mini.data.CompaniasManager
import org.mini.data.PaquetesRecargasManager
import org.mini.model.Companias
import org.mini.model.PaquetesRecargas
import io.ktor.http.contentType
import org.mini.dto.XmlParser
import io.ktor.http.content.TextContent
import io.ktor.http.ContentType
import org.mini.dto.XMLCreator

// URL Base de la API
private const val BASE_URL = "https://taeservicel.com.mx/testing/transact.asmx"

class CompaniasRepoImpl(
    private val companiasManager: CompaniasManager,
    private val httpClient: HttpClient // Cliente Ktor inyectado
) : CompaniasRepository {
    val xmlCreator = XMLCreator() // Asegúrate de que XMLCreator sea multiplataforma
    val xmlParser = XmlParser() // Asegúrate de que XMLParser sea multiplataforma

    override suspend fun getAllCompanias(): List<Companias> {
        return companiasManager.fakeCompaniasList
    }

    override suspend fun getPaquetesForCompany(nombreCompania: String): List<PaquetesRecargas> {
        return if (nombreCompania.isEmpty()) {
            emptyList()
        } else {
            PaquetesRecargasManager.fakePaquetesRecargasList.filter { it.nombre == nombreCompania }
        }
    }

    override suspend fun getRecargasForPaquetes(nombreCompania: String): String? {
        val xml = xmlCreator.xmlgetProductsFrom("MINIABASTOS2025","gY.\$26@XWUf")
        return try{
                val response: HttpResponse = httpClient.post(BASE_URL) {
                    contentType(ContentType.Text.Xml)
                    header("SOAPAction", "http://tempuri.org/GetSkuList")
                    header("Accept", "*/*")
                    header("User-Agent", "KtorClient")
                    header("Cache-Control", "no-cache")
                    setBody(TextContent(xml, ContentType.Text.Xml)) // Cuerpo de la solicitud como texto XML
                }
            if (response.status.isSuccess()) {
                val responseBody = response.bodyAsText()
                println("Response Body:")

                responseBody
            } else {
                println("Error: Código de respuesta inesperado ${response.status}")
                null
            }
        }catch (e: Exception){
            println("Error: ${e.message}")
            println("No hay conexión con el servidor")
            null
        }
    }

    // Método mejorado y adaptado para Kotlin Multiplatform
    override suspend fun getTRequestID(): String? {
        val valor = xmlCreator.xmlGetTRequestID("MINIABASTOS2025", "gY.\$26@XWUf", "")

        return try {
            val response: HttpResponse = httpClient.post(BASE_URL) {
                // Configuración del encabezado y contenido de la solicitud
                contentType(ContentType.Text.Xml)
                header("SOAPAction", "http://tempuri.org/GetTRequestID")
                header("Connection", "keep-alive")
                header("Accept-Encoding", "gzip, deflate, br")
                header("Accept", "*/*")
                header("User-Agent", "KtorClient")
                header("Cache-Control", "no-cache")
                setBody(TextContent(valor, ContentType.Text.Xml)) // Cuerpo de la solicitud como texto XML
            }

            if (response.status.isSuccess()) {
                val responseBody = response.bodyAsText()
                println("Response Body:\n$responseBody")

                // Procesa el resultado con el XML parser
                val result = xmlParser.getTRequestIDResult(responseBody)
                if (result != null) {
                    println("GetTRequestIDResult: $result")
                } else {
                    println("No me dio ningun valor el API")
                }
                result
            } else {
                println("Error: Código de respuesta inesperado ${response.status}")
                null
            }
        } catch (e: ClientRequestException) {
            println("Error en la solicitud: ${e.response.status} - ${e.response.bodyAsText()}")
            null
        } catch (e: Exception) {
            println("Error: ${e.message}")
            println("No hay conexión con el servidor")
            null
        }
    }

}
