package org.mini.model

import org.jetbrains.compose.resources.DrawableResource
import recargastae.composeapp.generated.resources.Res
import recargastae.composeapp.generated.resources.atyt
import recargastae.composeapp.generated.resources.movi
import recargastae.composeapp.generated.resources.telcel
import recargastae.composeapp.generated.resources.unefon


data class Companias(
    val id: Long=1,
    val nombre: String,
    val category: CompaniaCategory,
){
    val icon = category.icon
}

enum class CompaniaCategory(val icon: DrawableResource) {
    TELCEL(Res.drawable.telcel),
    MOVISTAR(Res.drawable.movi),
    ATyT(Res.drawable.atyt),
    UNEFON(Res.drawable.unefon)
}