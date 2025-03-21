package org.mini.data

import org.example.project.domain.CompaniasRepository
import org.mini.model.Companias
import org.mini.model.PaquetesRecargas

private const val BASE_URL = "https://taeservicel.com.mx/testing/transact.asmx"
class CompaniasRepoImpl(private val companiasManager: CompaniasManager): CompaniasRepository {

    override fun getAllCompanias(): List<Companias> {
        return companiasManager.fakeCompaniasList;
    }

    override fun getPaquetesForCompany(nombreCompania: String): List<PaquetesRecargas> {
        if (nombreCompania.isEmpty()) {
            return emptyList()
        }else{
            return PaquetesRecargasManager.fakePaquetesRecargasList.filter { it.nombre == nombreCompania }
        }
    }
}

