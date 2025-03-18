package org.mini.data

import org.mini.model.CompaniaCategory
import org.mini.model.Companias

object CompaniasManager {
    private var currentId = 1L

    val fakeCompaniasList = mutableListOf(
        Companias(
            id= currentId++,
            nombre = "Telcel",
            category = CompaniaCategory.TELCEL
        ),
        Companias(
            id= currentId++,
            nombre = "Movistar",
            category = CompaniaCategory.MOVISTAR
        ),
        Companias(
            id= currentId++,
            nombre = "AT&T",
            category = CompaniaCategory.ATyT
        ),
        Companias(
            id= currentId++,
            nombre = "Unefon",
            category = CompaniaCategory.UNEFON
        )
    )
}