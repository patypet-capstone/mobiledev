package com.ahmrh.patypet.ui.components.button

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ahmrh.patypet.ui.components.button.CustomButton


@Composable
fun LongButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit = {},
    color: Color = MaterialTheme.colorScheme.primary,
    textColor: Color = MaterialTheme.colorScheme.onPrimary,
    isError: Boolean = false
) {
    CustomButton(
        text = text,
        color = color,
        onClick = onClick,
        textColor = textColor,
        modifier = modifier
            .width(312.dp)
            .height(56.dp),
        isError = isError
    )
}