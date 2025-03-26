package org.mini

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Apps
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import moe.tlaster.precompose.PreComposeApp
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.path
import moe.tlaster.precompose.navigation.rememberNavigator
import org.mini.data.CrossConfigDevice
import org.mini.data.TitleTopBarTypes
import org.mini.navigation.Navigator
import org.koin.compose.KoinContext

@Composable
fun App(configDevice: CrossConfigDevice? = null) {
    PreComposeApp {
        KoinContext {
            val colors = getColorsTheme()
            AppTheme {
                val navigator = rememberNavigator()

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            elevation = 0.dp,
                            backgroundColor = colors.backgroundColor,
                            title = {
                                Text(
                                    text = getTitleTopAppBar(navigator),
                                    fontSize = 25.sp,
                                    color = colors.textColor
                                )
                            },
                            navigationIcon = {
                                val isCompania = getTitleTopAppBar(navigator) != TitleTopBarTypes.DASHBOARD.value
                                if (isCompania) {
                                    IconButton(onClick = { navigator.popBackStack() }) {
                                        Icon(
                                            modifier = Modifier.padding(start = 16.dp),
                                            imageVector = Icons.Default.ArrowBackIosNew,
                                            contentDescription = "Back",
                                            tint = colors.textColor
                                        )
                                    }
                                } else {
                                    Icon(
                                        modifier = Modifier.padding(start = 16.dp),
                                        imageVector = Icons.Default.Apps,
                                        contentDescription = "Dashboard app",
                                        tint = colors.textColor
                                    )
                                }
                            }
                        )
                    },
                ) { paddingValues ->
                    Navigator(navigator = navigator)
                }
            }
        }
    }
}

@Composable
fun getTitleTopAppBar(navigator: Navigator): String {
    val currentEntry = navigator.currentEntry.collectAsState(null).value
    val routeName = currentEntry?.path<String>("nombre")
    return routeName?.let { TitleTopBarTypes.valueOf(it).value } ?: TitleTopBarTypes.DASHBOARD.value
}
