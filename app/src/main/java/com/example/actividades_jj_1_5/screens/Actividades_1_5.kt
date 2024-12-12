package com.dam23_24.composecatalogolayout.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

/*
Actividad 1:
Hacer que el texto del botón muestre "Cargar perfil", pero cambie a "Cancelar"
cuando se muestre la línea de progreso... Cuando pulsemos "Cancelar" vuelve al texto por defecto.
*/

@Composable
fun Actividad1() {
    var showLoading by rememberSaveable { mutableStateOf(false) }

    Column(
        Modifier
            .padding(24.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        if (showLoading) {
            CircularProgressIndicator(
                color = Color.Red,
                strokeWidth = 10.dp
            )

            LinearProgressIndicator(
                modifier = Modifier.padding(top = 32.dp),
                color = Color.Red,
                trackColor = Color.LightGray
            )
        }
        //Separación del botón de la línea de progreso
        Spacer(modifier = Modifier.height(30.dp))
        Button(
            onClick = { showLoading = !showLoading }
        ) {
            //Solo había que modificar esta línea, si se muestra la carga cancelar y sino cargar
            Text(text = if (showLoading) "Cancelar" else "Cargar perfil")
        }
    }
}

/*
Actividad 2:
Modifica ahora también que se separe el botón de la línea de progreso 30 dp,
pero usando un estado... es decir, cuando no sea visible no quiero que tenga la separación
aunque no se vea.
*/


/*
Actividad 3:
- Separar los botones entre ellos 10 dp, del indicador de progreso 15 dp y centrarlos horizontalmente.
- Cuando se clique el botón Incrementar, debe añadir 0.1 a la propiedad progress y quitar 0.1
  cuando se pulse el botón Decrementar.
- Evitar que nos pasemos de los márgenes de su propiedad progressStatus (0-1)
*/

@Composable
fun Actividad3() {
    var progress by rememberSaveable { mutableStateOf(0f) }

    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LinearProgressIndicator(
            progress = progress,
            modifier = Modifier.padding(bottom = 15.dp))//Separación del indicador

        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center) {
            Button(
                //Se le suma o resta 0.1 dependiendo del botón pulsado y mediante
                //coerce se establece el valor máximo o mínimo
                onClick = { progress = (progress + 0.1f).coerceAtMost(1f) },
                //Separación de botones
                modifier = Modifier.padding(end = 10.dp)) {
                Text(text = "Incrementar")
            }
            Button(onClick = { progress = (progress - 0.1f).coerceAtLeast(0f) }) {
                Text(text = "Reducir")
            }
        }
    }
}


/*
Actividad 4:
Sitúa el TextField en el centro de la pantalla y haz que reemplace el valor de una coma por un punto
y que no deje escribir más de un punto decimal...
*/

@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun Actividad4() {
    var myVal by rememberSaveable { mutableStateOf("") }

    //Hago una columna como organizador principal para colocarlo en el centro
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
    TextField(
        value = myVal,
        onValueChange = { newVal ->
            if (newVal.count { it == '.' } <= 1) {//Limito el punto decimal a 1
                myVal = newVal.replace(',', '.')//Aquí remplaza coma por punto
            }
        },
        label = { Text(text = "Importe") },
        modifier = Modifier
            .fillMaxWidth(0.8f)
    )
    }
}


/*
Actividad 5:
Haz lo mismo, pero elevando el estado a una función superior y usando un componente OutlinedTextField
al que debes añadir un padding alrededor de 15 dp y establecer colores diferentes en los bordes
cuando tenga el foco y no lo tenga.
A nivel funcional no permitas que se introduzcan caracteres que invaliden un número decimal.
*/
@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun Actividad5() {
    var myVal by rememberSaveable { mutableStateOf("") }

    Actividad5Content(
        value = myVal,
        onValueChange = { newVal ->//Sinceramente he cogido este regex de internet, pero funciona y no permite otros caracteres
            if (newVal.matches(Regex("^\\d*\\.?\\d*\$"))) {
                myVal = newVal
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Actividad5Content(value: String, onValueChange: (String) -> Unit) {
    var hasFocus by rememberSaveable { mutableStateOf(false) }

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(text = "Importe") },
        modifier = Modifier
            .padding(15.dp) //Padding alrededor
            .fillMaxWidth()
            .onFocusChanged { focusState -> hasFocus = focusState.isFocused }, // Para manejar el foc
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color.Green, //Diferentes colors según el foco
            unfocusedBorderColor = Color.Gray
        )
    )
}
