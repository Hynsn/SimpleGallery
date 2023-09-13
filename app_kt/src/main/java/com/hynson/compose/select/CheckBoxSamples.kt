import androidx.compose.foundation.layout.Column
import androidx.compose.material.Checkbox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview


@Preview
@Composable
fun CheckBoxSamples() {
    var singleCheckBox by remember {
        mutableStateOf(false)
    }
    var bigCheckBox by remember {
        mutableStateOf(listOf(false, false))
    }
    Column() {
        //单个勾选框
        Checkbox(checked = singleCheckBox, onCheckedChange = {
            singleCheckBox = it
        })
        //多个勾选框
        bigCheckBox.forEachIndexed { i, check ->
            Checkbox(checked = check, onCheckedChange = {
                bigCheckBox = bigCheckBox.mapIndexed { j, isSelect ->
                    if (i == j) {
                        !isSelect
                    } else {
                        isSelect
                    }
                }
            })
        }
    }
}