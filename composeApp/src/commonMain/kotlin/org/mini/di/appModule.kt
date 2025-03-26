package org.mini.di

import CompaniasRepoImpl
import com.expenseApp.db.AppDatabase
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.header
import io.ktor.serialization.kotlinx.json.json
import org.example.project.domain.CompaniasRepository
import org.example.project.presentacion.CompaniasViewModel
import org.koin.dsl.module
import org.mini.data.CompaniasManager

fun appModule() = module {
    single <HttpClient>{HttpClient{install(ContentNegotiation){json()}}}
    single<CompaniasRepository> { CompaniasRepoImpl(CompaniasManager,get()) }
    factory { CompaniasViewModel(get()) }
}