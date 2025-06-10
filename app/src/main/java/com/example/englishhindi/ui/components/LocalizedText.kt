package com.bhashasetu.app.ui.components

import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit

/**
 * A wrapper around the standard Text composable that displays text in either English or Hindi
 * based on the currently selected language.
 *
 * @param textEn The English text to display
 * @param textHi The Hindi text to display
 * @param currentLanguage The currently selected language code ("en" or "hi")
 * @param modifier Optional modifier for styling
 * @param fontFamily Optional font family to apply
 * @param fontSize Optional font size to apply
 * @param fontStyle Optional font style to apply
 * @param fontWeight Optional font weight to apply
 * @param letterSpacing Optional letter spacing to apply
 * @param textDecoration Optional text decoration to apply
 * @param textAlign Optional text alignment to apply
 * @param lineHeight Optional line height to apply
 * @param overflow Optional text overflow handling to apply
 * @param softWrap Optional soft wrap handling
 * @param maxLines Optional maximum number of lines to display
 * @param style Optional TextStyle to apply
 */
@Composable
fun LocalizedText(
    textEn: String,
    textHi: String,
    currentLanguage: String,
    modifier: Modifier = Modifier,
    color = androidx.compose.ui.graphics.Color.Unspecified,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontStyle: FontStyle? = null,
    fontWeight: FontWeight? = null,
    fontFamily: FontFamily? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    lineHeight: TextUnit = TextUnit.Unspecified,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    style: TextStyle = LocalTextStyle.current
) {
    val text = if (currentLanguage == "hi") textHi else textEn
    
    Text(
        text = text,
        modifier = modifier,
        color = color,
        fontSize = fontSize,
        fontStyle = fontStyle,
        fontWeight = fontWeight,
        fontFamily = fontFamily,
        letterSpacing = letterSpacing,
        textDecoration = textDecoration,
        textAlign = textAlign,
        lineHeight = lineHeight,
        overflow = overflow,
        softWrap = softWrap,
        maxLines = maxLines,
        style = style
    )
}