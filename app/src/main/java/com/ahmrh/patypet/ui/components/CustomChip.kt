package com.ahmrh.patypet.ui.components

import androidx.annotation.ColorInt
import androidx.annotation.Size
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ahmrh.patypet.R
import com.ahmrh.patypet.ui.theme.PatypetTheme
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

@Composable
fun CustomChip(
    chipType: ChipType,
    content: String = "",
    modifier: Modifier = Modifier,
    colours: List<String>? = null
) {
    Row(
        modifier = modifier
            .border(
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(
                    2.dp,
                    MaterialTheme.colorScheme.primary
                )
            )
            .padding(
                horizontal = 4.dp,
                vertical = 8.dp
            ),
        horizontalArrangement = Arrangement.spacedBy(4.dp)

    ) {
        Box(
            modifier = Modifier
                .width(28.dp)
                .height(28.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(MaterialTheme.colorScheme.primary)

        ) {
            when (chipType) {
                is ChipType.Age -> {
                    Image(
                        painterResource(id = R.drawable.chip_image_age),
                        contentDescription = null,
                        Modifier.align(
                            Alignment.Center
                        )
                    )
                }

                is ChipType.Weight -> {
                    Image(
                        painterResource(id = R.drawable.chip_image_weight),
                        contentDescription = null,
                        Modifier.align(
                            Alignment.Center
                        )
                    )
                }

                is ChipType.Color -> {
                    Image(
                        painterResource(id = R.drawable.chip_image_color),
                        contentDescription = null,
                        Modifier.align(
                            Alignment.Center
                        )
                    )
                }
            }
        }
        Column(
            modifier = Modifier
                .width(96.dp)
        ) {

            when (chipType) {
                is ChipType.Age -> { Text("Life Span", fontSize = 10.sp, fontWeight = FontWeight.Medium) }

                is ChipType.Weight -> { Text("Weight", fontSize = 10.sp, fontWeight = FontWeight.Medium) }

                is ChipType.Color -> { Text("Color", fontSize = 10.sp, fontWeight = FontWeight.Medium) }
            }

            if(chipType is ChipType.Color){
                // Need to be fixed
                Row(
                    horizontalArrangement = Arrangement.spacedBy(2.dp),
                    modifier = Modifier
                        .padding(top = 4.dp)
                ){
                    colours?.forEach{
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(Color(parseColor(it)))
                        ){}


                    }
                }

            } else {
                Text(content, fontSize = 9.sp, fontWeight = FontWeight.Normal)
            }

        }

    }
}
@ColorInt
fun parseColor(@Size(min = 1) colorString: String): Int {
    if (colorString[0] == '#') { // Use a long to avoid rollovers on #ffXXXXXX
        var color = colorString.substring(1).toLong(16)
        if (colorString.length == 7) { // Set the alpha value
            color = color or -0x1000000
        } else require(colorString.length == 9) { "Unknown color" }
        return color.toInt()
    }
    throw IllegalArgumentException("Unknown color")
}

@Preview(showBackground = true)
@Composable
fun CustomChipPreview() {
    PatypetTheme {
        CustomChip(
            ChipType.Color,
            colours = listOf(
                "#ccb4ae",
                "#a5a39f",
                "#f1ded7"
            )
        )
    }
}

sealed class ChipType {
    object Age : ChipType()
    object Weight : ChipType()
    object Color : ChipType()
}