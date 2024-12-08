package com.example.unitconverter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.unitconverter.ui.theme.UnitConverterTheme
import kotlin.math.roundToInt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            UnitConverterTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    // Pass the padding to your UnitConverter function
                    UnitConverter(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}
@Composable
fun UnitConverter(modifier: Modifier = Modifier) {
    var inputValue by remember { mutableStateOf("") }
    var outputValue by remember { mutableStateOf("") }
    var inputUnit by remember { mutableStateOf("Meters") }
    var outputUnit by remember { mutableStateOf("Meters") }
    var iExpanded by remember { mutableStateOf(false) }
    var oExpanded by remember { mutableStateOf(false) }
    val conversionFactor = remember { mutableDoubleStateOf(1.00) }
    val oconversionFactor = remember { mutableDoubleStateOf(1.00) }

    fun convertUnit() {
        val inputValueDouble = inputValue.toDoubleOrNull() ?: 0.0
        val result =
            (inputValueDouble * conversionFactor.doubleValue * 100.0 / oconversionFactor.doubleValue).roundToInt() / 100.0
        outputValue = result.toString()
    }

    Scaffold(modifier = modifier.fillMaxSize()) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Unit Converter",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary
            )

            // Input Section
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    OutlinedTextField(
                        value = inputValue,
                        onValueChange = {
                            inputValue = it
                            convertUnit()
                        },
                        label = { Text("Enter value") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    DropdownButton(
                        label = "Input Unit",
                        selectedUnit = inputUnit,
                        onUnitChange = { unit, factor ->
                            inputUnit = unit
                            conversionFactor.doubleValue = factor
                            convertUnit()
                        },
                        expanded = iExpanded,
                        onExpandedChange = { iExpanded = it }
                    )
                }
            }

            // Output Section
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    DropdownButton(
                        label = "Output Unit",
                        selectedUnit = outputUnit,
                        onUnitChange = { unit, factor ->
                            outputUnit = unit
                            oconversionFactor.doubleValue = factor
                            convertUnit()
                        },
                        expanded = oExpanded,
                        onExpandedChange = { oExpanded = it }
                    )

                    Text(
                        text = "Converted Value: $outputValue $outputUnit",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }
}

@Composable
fun DropdownButton(
    label: String,
    selectedUnit: String,
    onUnitChange: (String, Double) -> Unit,
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit
) {
    Column {
        Text(text = label, style = MaterialTheme.typography.labelMedium)
        Button(
            onClick = { onExpandedChange(true) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(selectedUnit)
            Icon(Icons.Default.ArrowDropDown, contentDescription = null)
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { onExpandedChange(false) }
        ) {
            DropdownMenuItem(
                text = { Text("Centimeters") },
                onClick = {
                    onUnitChange("Centimeters", 0.01)
                    onExpandedChange(false)
                }
            )
            DropdownMenuItem(
                text = { Text("Meters") },
                onClick = {
                    onUnitChange("Meters", 1.0)
                    onExpandedChange(false)
                }
            )
            DropdownMenuItem(
                text = { Text("Feet") },
                onClick = {
                    onUnitChange("Feet", 0.3048)
                    onExpandedChange(false)
                }
            )
            DropdownMenuItem(
                text = { Text("Millimeters") },
                onClick = {
                    onUnitChange("Millimeters", 0.001)
                    onExpandedChange(false)
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RedesignedUnitConverterPreview() {
    UnitConverterTheme {
        UnitConverter()
    }
}
