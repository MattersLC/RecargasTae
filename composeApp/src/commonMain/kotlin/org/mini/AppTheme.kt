package org.mini

import androidx.compose.foundation.shape.AbsoluteCutCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.mini.data.SessionCache

@Composable
fun AppTheme(content: @Composable () -> Unit){
    MaterialTheme(colors = MaterialTheme.colors.copy(primary = Color.Black),
        shapes = MaterialTheme.shapes.copy(
            small = AbsoluteCutCornerShape(0.dp),
            medium = AbsoluteCutCornerShape(0.dp),
            large = AbsoluteCutCornerShape(0.dp)
        )){
        content()
    }
}

@Composable
fun getColorsTheme(): DarkModeColors{
    val isDarkMode = SessionCache.isDarkMode()

    val Purple = Color(0xFF6A66FF)
    val ColorRecargaItem = if(isDarkMode) Color.White else Color.Black
    val BackgroundColor = if(isDarkMode) Color.Black else Color.White
    val TextColor = if(isDarkMode) Color.White else Color.Black
    var AddIconColor = if(isDarkMode) Purple else Color.Black
    val ColorArrowRound = if(isDarkMode) Purple else Color.Gray.copy(alpha = .2f)
    val CardColor = if(isDarkMode) Color.White else Color.White

    return DarkModeColors(
        purple = Purple,
        colorRecargaItem = ColorRecargaItem,
        backgroundColor = BackgroundColor,
        textColor = TextColor,
        addIconColor = AddIconColor,
        colorArrowRound = ColorArrowRound,
        cardColor = CardColor
    )
}
data class DarkModeColors(
    val purple: Color,
    val colorRecargaItem: Color,
    val backgroundColor:Color,
    val textColor: Color,
    val addIconColor: Color,
    val colorArrowRound: Color,
    val cardColor: Color
)
