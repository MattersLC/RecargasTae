package org.mini.dto

class XMLCreator {
    fun xmlGetTRequestID(username: String, password: String, licence: String): String {
        return """
            <?xml version="1.0" encoding="utf-8"?>
            <soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
                <soap:Body>
                    <GetTRequestID xmlns="http://tempuri.org/">
                        <username>$username</username>
                        <password>$password</password>
                        <licence>$licence</licence>
                    </GetTRequestID>
                </soap:Body>
            </soap:Envelope>
        """.trimIndent()
    }

    fun xmlgetProductsFrom(username: String, password: String): String {
        return """
            <?xml version="1.0" encoding="utf-8"?>
            <soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
                <soap:Body>
                    <GetSkuList xmlns="http://tempuri.org/">
                        <username>$username</username>
                        <password>$password</password>
                    </GetSkuList>
                </soap:Body>
            </soap:Envelope>
        """.trimIndent()
    }
}
