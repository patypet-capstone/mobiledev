package com.ahmrh.patypet.ui.components.dialog

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue


@Composable
fun CustomDialog(
    title: String,
    body: String,
    onDismiss: () -> Unit = {}
) {
    var isOpenState by remember { mutableStateOf(true) }

    if (isOpenState) {
        AlertDialog(
            onDismissRequest = { isOpenState = false },
            title = {
                Text(text = title)
            },
            text = {
                Text(
                    text =  body
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        isOpenState = false
                        onDismiss()
                    }
                ) {
                    Text("Okay")
                }
            },


            )
    }
}