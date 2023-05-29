package com.ahmrh.patypet.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ahmrh.patypet.ui.theme.PatypetTheme

@Composable
fun CustomButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit = {},
    color: Color = MaterialTheme.colorScheme.primary,
    textColor: Color = MaterialTheme.colorScheme.onPrimary
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(color),
        colors = ButtonDefaults.buttonColors(
            containerColor = color,
            contentColor = textColor
        )

    ) {
        Text(
            text = text,
            color = textColor
        )
    }

}

@Composable
fun LongButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit = {},
    color: Color = MaterialTheme.colorScheme.primary,
    textColor: Color = MaterialTheme.colorScheme.onPrimary
) {
    CustomButton(
        text = text,
        color = color,
        onClick = onClick,
        textColor = textColor,
        modifier = modifier
            .width(312.dp)
            .height(56.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun ButtonPreview() {
    PatypetTheme() {
        Box(
            Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CustomButton(
                text = "haloo",
                modifier = Modifier
            )
        }
    }
}