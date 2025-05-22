package com.example.englishhindi.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.englishhindi.ui.theme.EnglishHindiTheme

/**
 * A component for switching between English and Hindi language.
 * 
 * @param selectedLanguage The currently selected language code (e.g., "en" or "hi")
 * @param onLanguageSelected Callback when a language is selected
 * @param modifier Optional modifier for the component
 */
@Composable
fun LanguageSwitcher(
    selectedLanguage: String,
    onLanguageSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val languages = listOf(
        "en" to "English",
        "hi" to "हिंदी (Hindi)"
    )
    
    Row(
        modifier = modifier
            .selectableGroup()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        languages.forEach { (code, name) ->
            Row(
                Modifier
                    .selectable(
                        selected = (code == selectedLanguage),
                        onClick = { onLanguageSelected(code) },
                        role = Role.RadioButton
                    )
                    .padding(horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = (code == selectedLanguage),
                    onClick = null // null because we're handling the click on the row
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = name,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LanguageSwitcherPreview() {
    EnglishHindiTheme {
        Surface {
            LanguageSwitcher(
                selectedLanguage = "en",
                onLanguageSelected = {}
            )
        }
    }
}