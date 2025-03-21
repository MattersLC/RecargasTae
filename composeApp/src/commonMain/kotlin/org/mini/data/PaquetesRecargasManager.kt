package org.mini.data

import org.mini.model.CompaniaCategory
import org.mini.model.Companias
import org.mini.model.PaquetesRecargas

object PaquetesRecargasManager {
        private var currentId = 1L

        val fakePaquetesRecargasList = mutableListOf(
            PaquetesRecargas(
                id= currentId++,
                descripcion = "TIEMPO AIRE",
                nombre = "TELCEL"
            ),
            PaquetesRecargas(
                id= currentId++,
                descripcion = "PAQUETE INTERNET",
                nombre = "TELCEL"
            ),
            PaquetesRecargas(
                id= currentId++,
                descripcion = "PAQUETE AMIGO SL",
                nombre = "TELCEL"
            ),
            PaquetesRecargas(
                id= currentId++,
                descripcion = "PAQUETE AMIGO TIK TOK",
                nombre = "TELCEL"
            )
        )
}