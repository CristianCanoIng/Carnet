package com.example.carnet.Items

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.ui.geometry.Offset


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenA(navController: NavController, mascotaList: MutableList<Mascota>) {
    var nombre by rememberSaveable { mutableStateOf("") }
    var raza by rememberSaveable { mutableStateOf("") }
    var tamaño by rememberSaveable { mutableStateOf("") }
    var edad by rememberSaveable { mutableStateOf("") }
    var fotoUrl by rememberSaveable { mutableStateOf("") }

    Box(modifier = Modifier.fillMaxSize()) {
        DistortedBackground()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(color = Color.White)) {
                        append("Identificación ")
                    }
                    withStyle(style = SpanStyle(color = Color(0xFFD50651))) {
                        append("Mascota")
                    }
                },
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Cursive
            )

            Spacer(modifier = Modifier.height(40.dp))

            val campos = listOf(
                "Nombre" to nombre,
                "Raza" to raza,
                "Tamaño" to tamaño,
                "Edad" to edad,
                "Foto URL" to fotoUrl
            )

            val setters = listOf<(String) -> Unit>(
                { nombre = it },
                { raza = it },
                { tamaño = it },
                { edad = it },
                { fotoUrl = it }
            )

            campos.forEachIndexed { index, (label, value) ->
                OutlinedTextField(
                    value = value,
                    onValueChange = setters[index],
                    label = { Text(label) },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth(0.85f)
                        .padding(vertical = 6.dp),
                    textStyle = TextStyle(color = Color.White),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.White,
                        unfocusedBorderColor = Color.Gray,
                        cursorColor = Color.White,
                        focusedLabelColor = Color.White,
                        unfocusedLabelColor = Color.LightGray,
                        containerColor = Color.Transparent
                    )
                )
            }


            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    if (nombre.isNotBlank() && raza.isNotBlank() && tamaño.isNotBlank() &&
                        edad.isNotBlank() && fotoUrl.isNotBlank()) {
                        mascotaList.add(Mascota(nombre, raza, tamaño, edad, fotoUrl))
                        navController.navigate("ScreenB")
                    }
                },
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color.Black
                )
            ) {
                Text("Registrar", fontSize = 18.sp)
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = { navController.navigate("ScreenB") },
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Gray,
                    contentColor = Color.White
                )
            ) {
                Text("Ver Mascotas", fontSize = 16.sp)
            }
        }
    }
}

@Composable
fun DistortedBackground() {
    val infiniteTransition = rememberInfiniteTransition()
    val offsetAnim by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 800f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 15000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    Canvas(modifier = Modifier.fillMaxSize()) {
        drawRect(color = Color.Black)

        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(Color.Red.copy(alpha = 0.6f), Color.Transparent),
                radius = 600f
            ),
            center = Offset(offsetAnim, offsetAnim),
            radius = 500f
        )

        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(Color.Red.copy(alpha = 0.3f), Color.Transparent),
                radius = 500f
            ),
            center = Offset(size.width - offsetAnim, size.height - offsetAnim),
            radius = 400f
        )
    }
}

