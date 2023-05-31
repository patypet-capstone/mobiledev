package com.ahmrh.patypet.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ahmrh.patypet.R
import com.ahmrh.patypet.ui.theme.PatypetTheme

@Composable
fun CustomInputField(
    modifier: Modifier = Modifier,
    inputText: String = "",
    label: String = "Label",
    isPassword: Boolean = false,
    onTextChange: (String) -> Unit = {},
) {

    if (isPassword) {
        var visible by remember { mutableStateOf(false) }

        OutlinedTextField(
            modifier = modifier,
            value = inputText,
            onValueChange = { onTextChange(it) },
            label = { Text(label) },
            visualTransformation = if (visible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = {
                    visible = !visible
                }) {
                    Icon(
                        painter = if (visible) painterResource(
                            id = R.drawable.visible
                        ) else painterResource(
                            id = R.drawable.visible_off
                        ),
                        contentDescription = "Show Password",
                        tint = if (visible) MaterialTheme.colorScheme.secondary
                        else MaterialTheme.colorScheme.outline
                    )
                }
            },

            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = MaterialTheme.colorScheme.primaryContainer,

                unfocusedBorderColor = MaterialTheme.colorScheme.primaryContainer,

                )

        )
    } else {
        OutlinedTextField(
            modifier = modifier,
            value = inputText,
            onValueChange = { onTextChange(it) },
            label = { Text(label) },

            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = MaterialTheme.colorScheme.primaryContainer,

                unfocusedBorderColor = MaterialTheme.colorScheme.primaryContainer,

                )
        )
    }


}

@Composable
fun LongInputField(
    modifier: Modifier = Modifier,
    inputText: String = "",
    label: String = "Label",
    isPassword: Boolean = false,
    onTextChange: (String) -> Unit = {},
) {
    CustomInputField(
        inputText = inputText,
        label = label,
        modifier = modifier.width(312.dp),
        isPassword = isPassword,
        onTextChange = onTextChange

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
            CustomInputField(isPassword = true)
        }
    }
}