package com.ahmrh.patypet.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ahmrh.patypet.ui.theme.PatypetTheme

@Composable
fun CustomInputField(
    modifier: Modifier = Modifier,
    inputText: String = "",
    label: String = "Label",
) {
    var text by remember { mutableStateOf(inputText) }

    OutlinedTextField(
        modifier = modifier,
        value = text,
        onValueChange = { text = it },
        label = {
        },
        placeholder = { Text(text = label) },

        shape = RoundedCornerShape(16.dp),
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedContainerColor = MaterialTheme.colorScheme.primaryContainer,

            unfocusedBorderColor = MaterialTheme.colorScheme.primaryContainer,

            )

    )
}

@Composable
fun LongInputField(
    modifier: Modifier = Modifier,
    inputText: String = "",
    label: String = "Label",
){
    CustomInputField(
        inputText = inputText,
        label = label,
        modifier = modifier
            .width(312.dp)

    )
}

@Preview(showBackground = true)
@Composable
fun InputFieldPreview() {
    PatypetTheme {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CustomInputField()
        }
    }
}