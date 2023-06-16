package com.ahmrh.patypet.ui.components.dialog

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ahmrh.patypet.ui.components.CustomInputField
import com.ahmrh.patypet.ui.components.button.CustomButton
import com.ahmrh.patypet.ui.theme.PatypetTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SavePetDialog(
    onConfirm: (name: String) -> Unit,
) {

    var name by remember { mutableStateOf("") }
    var openDialog by remember { mutableStateOf(true) }
    if(openDialog){

        AlertDialog(
            onDismissRequest = {
                // Dismiss the dialog when the user clicks outside the dialog or on the back
                // button. If you want to disable that functionality, simply use an empty
                // onDismissRequest.
                openDialog = false
            }
        ) {
            Surface(
                modifier = Modifier
                    .wrapContentWidth()
                    .wrapContentHeight(),
                shape = MaterialTheme.shapes.large,
                tonalElevation = AlertDialogDefaults.TonalElevation
            ) {

                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "My Pet",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    CustomInputField(
                        label = "Name",
                        inputText = name,
                        onTextChange = {
                            name = it
                        }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    CustomButton(
                        text = "Confirm",
                        color = MaterialTheme.colorScheme.secondary,
                        textColor = MaterialTheme.colorScheme.onSecondary,
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            openDialog = false
                            onConfirm(name)

                        }
                    )
                }
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun SavePetDialogPreview() {
    PatypetTheme {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            var text by remember { mutableStateOf("") }
//            SavePetDialog()
        }
    }
}