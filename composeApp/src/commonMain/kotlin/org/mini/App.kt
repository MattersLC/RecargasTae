package org.mini

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.mini.data.TitleTopBarTypes
import moe.tlaster.precompose.PreComposeApp
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.rememberNavigator
import org.example.project.presentacion.CompaniasUiState
import org.mini.data.CompaniasManager
import org.mini.data.CrossConfigDevice
import org.mini.model.CompaniaCategory
import org.mini.model.Companias
import org.mini.ui.CompaniaCard
import org.mini.ui.CompaniasScreen

@Composable
fun App(configDevice: CrossConfigDevice? = null) {

    PreComposeApp {

        val colors = getColorsTheme()
        AppTheme {
            val navigator = rememberNavigator()
            val titleTopBar = getTitleTopAppBar(navigator)
            val isCompania = titleTopBar != TitleTopBarTypes.DASHBOARD.value
            //Navigator(navigator)
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                backgroundColor = Color.Transparent,
                topBar = {
                    TopAppBar(
                        elevation = 0.dp,
                        //backgroundColor = colors.backgroundColor,
                        title = {
                            Text(
                                text = titleTopBar,
                                fontSize = 25.sp,
                                color = colors.textColor
                            )
                        }
                    )}

            ) {
                CompaniasScreen(
                    uiState = CompaniasUiState(
                        companias = CompaniasManager.fakeCompaniasList
                    ), onCompaniasClick = {}
                )
            }
        }
    }
}

@Composable
fun getTitleTopAppBar(navigator: Navigator): String {
    var titleTopBar = TitleTopBarTypes.DASHBOARD

    val isOnAddExpenses =
        navigator.currentEntry.collectAsState(null).value?.route?.route.equals("/recargas/{id}?")
    if (isOnAddExpenses) {
        titleTopBar = TitleTopBarTypes.TELCEL
    }

    return titleTopBar.value
}