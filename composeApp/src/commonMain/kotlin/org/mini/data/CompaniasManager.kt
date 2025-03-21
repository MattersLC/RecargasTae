package org.mini.data

import org.mini.model.CompaniaCategory
import org.mini.model.Companias

object CompaniasManager {
    private var currentId = 1L

    val fakeCompaniasList = mutableListOf(
        Companias(
            id= currentId++,
            nombre = "TELCEL",
            category = CompaniaCategory.TELCEL
        ),
        Companias(
            id= currentId++,
            nombre = "MOVISTAR",
            category = CompaniaCategory.MOVISTAR
        ),
        Companias(
            id= currentId++,
            nombre = "ATYT",
            category = CompaniaCategory.ATyT
        ),
        Companias(
            id= currentId++,
            nombre = "UNEFON",
            category = CompaniaCategory.UNEFON
        )
    )
}