package org.example.project.domain

import org.mini.model.Companias
import org.mini.model.PaquetesRecargas

interface CompaniasRepository{
    fun getAllCompanias(): List<Companias>

    fun getPaquetesForCompany(nombreCompania: String): List<PaquetesRecargas>
}