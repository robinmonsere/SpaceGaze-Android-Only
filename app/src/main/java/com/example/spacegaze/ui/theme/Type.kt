package com.example.spacegaze.ui.theme

import android.content.res.Resources.Theme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.spacegaze.R

val Calibri = FontFamily(
    Font(R.font.calibri_regular),
    Font(R.font.calibri_bold, FontWeight.Bold),
    Font(R.font.calibri_italic, style = FontStyle.Italic)
)

// Set of Material typography styles to start with
val Typography = Typography(
    body1 = TextStyle(
        fontFamily = Calibri,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
    ),
    body2 = TextStyle(
        fontFamily = Calibri,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        color = Color.White
    ),
    h1 = TextStyle(
        fontFamily = Calibri,
        fontWeight = FontWeight.Normal,
        fontSize = 30.sp
    ),
    h2 = TextStyle(
        fontFamily = Calibri,
        fontWeight = FontWeight.Normal,
        fontSize = 20.sp
    ),
    h3 = TextStyle(
        fontFamily = Calibri,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        color = Color.White
    ),
    subtitle1 = TextStyle(
        fontFamily = Calibri,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
    ),

    /* Other default text styles to override
    button = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
    */
)