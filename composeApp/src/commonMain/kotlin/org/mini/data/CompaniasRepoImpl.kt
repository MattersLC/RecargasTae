package org.mini.data

import org.example.project.domain.CompaniasRepository
import org.mini.model.Companias

class CompaniasRepoImpl(private val companiasManager: CompaniasManager): CompaniasRepository {

    override suspend fun getAllCompanias(): List<Companias> {
        return companiasManager.fakeCompaniasList;
    }
}