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

@Composable
fun App(configDevice: CrossConfigDevice? = null) {
    PreComposeApp {

        val colors = getColorsTheme()
        AppTheme {
            val navigator = rememberNavigator()
            val titleTopBar = getTitleTopAppBar(navigator)
            val isCompania = titleTopBar != TitleTopBarTypes.DASHBOARD.value
            Navigator(navigator)
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                //backgroundColor = Color.Transparent,
                topBar = {
                    TopAppBar(
                        elevation = 0.dp,
                        backgroundColor = colors.backgroundColor,
                        title = {
                            Text(text = titleTopBar,
                                fontSize = 25.sp,
                                color = colors.textColor)
                        },
                        navigationIcon = {
                            if (isCompania) {
                                IconButton(
                                    onClick = {
                                        navigator.popBackStack()
                                    }
                                ){
                                    Icon(
                                        modifier = Modifier.padding(start = 16.dp),
                                        imageVector = Icons.Default.ArrowBackIosNew,
                                        contentDescription = "Back",
                                        tint = colors.textColor
                                    )
                                }
                            }else{
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
            ) {
                Navigator(navigator)
                /*CompaniasScreen(
                    uiState = uiState, onCompaniasClick = {}
                )*/
            }
        }
    }
}

@Composable
fun getTitleTopAppBar(navigator: Navigator): String {
    var titleTopBar = TitleTopBarTypes.DASHBOARD

    val isOnMenu =
        navigator.currentEntry.collectAsState(null).value?.path<String>("nombre")
    isOnMenu?.let {
        titleTopBar = TitleTopBarTypes.valueOf(it)
    }

    return titleTopBar.value
}
