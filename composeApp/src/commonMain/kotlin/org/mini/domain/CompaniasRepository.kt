package org.example.project.domain

import org.mini.model.Companias

interface CompaniasRepository{
    suspend fun getAllCompanias(): List<Companias>
}