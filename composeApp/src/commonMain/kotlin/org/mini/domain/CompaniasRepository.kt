package org.example.project.domain

import org.mini.model.Companias
import org.mini.model.PaquetesRecargas
import org.mini.model.Recarga

interface CompaniasRepository{
    suspend fun getAllCompanias(): List<Companias>

    suspend fun getPaquetesForCompany(nombreCompania: String): List<PaquetesRecargas>

    suspend fun getRecargasForPaquetes(nombreCompania: String): String?
}