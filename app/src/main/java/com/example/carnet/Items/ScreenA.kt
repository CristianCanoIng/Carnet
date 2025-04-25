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
import androidx.compose.foundation.background
import androidx.compose.ui.geometry.Offset
import kotlin.random.Random


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenA(navController: NavController, mascotaList: MutableList<Mascota>) {
    var nombre by rememberSaveable { mutableStateOf("") }
    var raza by rememberSaveable { mutableStateOf("") }
    var tamaño by rememberSaveable { mutableStateOf("") }
    var edad by rememberSaveable { mutableStateOf("") }
    var fotoUrl by rememberSaveable { mutableStateOf("") }

    Box(modifier = Modifier.fillMaxSize()) {
        ShootingStarsBackground()
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
                    withStyle(style = SpanStyle(color = Color(0xFF25D506))) {
                        append("Mascota")
                    }
                },
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Monospace
            )

            Spacer(modifier = Modifier.height(60.dp))

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
fun ShootingStarsBackground() {
    val infiniteTransition = rememberInfiniteTransition()
    val stars = remember { List(20) { Offset(Random.nextFloat(), Random.nextFloat()) } }

    val starAnimations = stars.map {
        infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = 1500f,
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = (5000..10000).random(),
                    easing = LinearEasing
                ),
                repeatMode = RepeatMode.Restart
            )
        )
    }

    Canvas(modifier = Modifier.fillMaxSize().background(Color.Black)) {
        val width = size.width
        val height = size.height

        stars.forEachIndexed { index, startOffset ->
            val offsetAnim = starAnimations[index].value

            val startX = startOffset.x * width
            val startY = startOffset.y * height
            val endX = startX + offsetAnim / 2
            val endY = startY + offsetAnim / 2

            drawLine(
                brush = Brush.linearGradient(
                    colors = listOf(Color.White, Color.Transparent),
                    start = Offset(startX, startY),
                    end = Offset(endX, endY)
                ),
                start = Offset(startX, startY),
                end = Offset(endX, endY),
                strokeWidth = 2f
            )
        }
    }
}
