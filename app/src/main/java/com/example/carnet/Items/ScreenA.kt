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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.style.TextAlign
import kotlin.random.Random

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenA(navController: NavController, mascotaList: MutableList<Mascota>) {
    var nombre by rememberSaveable { mutableStateOf("") }
    var raza by rememberSaveable { mutableStateOf("") }
    var tamaño by rememberSaveable { mutableStateOf("") }
    var edad by rememberSaveable { mutableStateOf("") }
    var fotoUrl by rememberSaveable { mutableStateOf("") }

    var nombreError by rememberSaveable { mutableStateOf<String?>(null) }
    var razaError by rememberSaveable { mutableStateOf<String?>(null) }
    var tamañoError by rememberSaveable { mutableStateOf<String?>(null) }
    var edadError by rememberSaveable { mutableStateOf<String?>(null) }
    var fotoUrlError by rememberSaveable { mutableStateOf<String?>(null) }

    var showErrorMessage by rememberSaveable { mutableStateOf(false) }

    fun validateNombre(text: String): String? {
        return when {
            text.isBlank() -> "El nombre es obligatorio"
            text.length < 2 -> "El nombre debe tener al menos 2 caracteres"
            text.length > 50 -> "El nombre no debe exceder 50 caracteres"
            !text.matches(Regex("^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$")) -> "El nombre solo debe contener letras"
            else -> null
        }
    }

    fun validateRaza(text: String): String? {
        return when {
            text.isBlank() -> "La raza es obligatoria"
            text.length > 50 -> "La raza no debe exceder 50 caracteres"
            !text.matches(Regex("^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$")) -> "La raza solo debe contener letras"
            else -> null
        }
    }

    fun validateTamaño(text: String): String? {
        val tamaños = listOf("pequeño", "mediano", "grande", "pequeña", "mediana", "grande")
        return when {
            text.isBlank() -> "El tamaño es obligatorio"
            !tamaños.contains(text.lowercase()) -> "Tamaño inválido. Use: pequeño, mediano o grande"
            else -> null
        }
    }

    fun validateEdad(text: String): String? {
        return when {
            text.isBlank() -> "La edad es obligatoria"
            !text.matches(Regex("^[0-9]+(\\.[0-9]+)? (años|meses)$")) &&
                    !text.matches(Regex("^[0-9]+$")) -> "Formato inválido. Ejemplos: '2 años', '6 meses' o solo número"
            text.matches(Regex("^[0-9]+$")) && text.toIntOrNull() ?: 0 > 30 -> "Edad no realista para una mascota"
            else -> null
        }
    }

    fun validateFotoUrl(text: String): String? {
        return when {
            text.isBlank() -> "La URL de la foto es obligatoria"
            !text.startsWith("http://") && !text.startsWith("https://") -> "La URL debe comenzar con http:// o https://"
            else -> null
        }
    }

    fun validateAllFields(): Boolean {
        nombreError = validateNombre(nombre)
        razaError = validateRaza(raza)
        tamañoError = validateTamaño(tamaño)
        edadError = validateEdad(edad)
        fotoUrlError = validateFotoUrl(fotoUrl)

        return nombreError == null && razaError == null && tamañoError == null &&
                edadError == null && fotoUrlError == null
    }

    Box(modifier = Modifier.fillMaxSize()) {
        ShootingStarsBackground()

        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(16.dp)
                .shadow(16.dp, shape = MaterialTheme.shapes.large)
                .background(
                    color = Color(0x331A237E),
                    shape = MaterialTheme.shapes.large
                )
                .padding(horizontal = 24.dp, vertical = 32.dp)
        ) {
            Column(
                modifier = Modifier
                    .widthIn(max = 350.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(50.dp))
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(color = Color.White)) {
                            append("Identificación ")
                        }
                        withStyle(style = SpanStyle(color = Color(0xFF000000))) {
                            append("Mascota")
                        }
                    },
                    fontSize = 36.sp,
                    fontWeight = FontWeight.ExtraBold,
                    fontFamily = FontFamily.Cursive,
                    style = TextStyle(
                        shadow = Shadow(
                            color = Color.Black,
                            offset = Offset(2f, 2f),
                            blurRadius = 4f
                        )
                    )
                )

                Spacer(modifier = Modifier.height(40.dp))

                OutlinedTextField(
                    value = nombre,
                    onValueChange = {
                        nombre = it
                        nombreError = null
                    },
                    label = { Text("Nombre") },
                    singleLine = true,
                    isError = nombreError != null,
                    modifier = Modifier
                        .widthIn(max = 300.dp)
                        .padding(vertical = 6.dp),
                    textStyle = TextStyle(color = Color.White),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.White,
                        unfocusedBorderColor = Color.Black,
                        errorBorderColor = Color.Red,
                        cursorColor = Color.White,
                        focusedLabelColor = Color.White,
                        unfocusedLabelColor = Color.LightGray,
                        containerColor = Color.White.copy(alpha = 0.15f)
                    )
                )
                nombreError?.let {
                    Text(
                        text = it,
                        color = Color.Red,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }

                OutlinedTextField(
                    value = raza,
                    onValueChange = {
                        raza = it
                        razaError = null
                    },
                    label = { Text("Raza") },
                    singleLine = true,
                    isError = razaError != null,
                    modifier = Modifier
                        .widthIn(max = 300.dp)
                        .padding(vertical = 6.dp),
                    textStyle = TextStyle(color = Color.White),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.White,
                        unfocusedBorderColor = Color.Black,
                        errorBorderColor = Color.Red,
                        cursorColor = Color.White,
                        focusedLabelColor = Color.White,
                        unfocusedLabelColor = Color.LightGray,
                        containerColor = Color.White.copy(alpha = 0.15f)
                    )
                )
                razaError?.let {
                    Text(
                        text = it,
                        color = Color.Red,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }

                OutlinedTextField(
                    value = tamaño,
                    onValueChange = {
                        tamaño = it
                        tamañoError = null
                    },
                    label = { Text("Tamaño (pequeño, mediano, grande)") },
                    singleLine = true,
                    isError = tamañoError != null,
                    modifier = Modifier
                        .widthIn(max = 300.dp)
                        .padding(vertical = 6.dp),
                    textStyle = TextStyle(color = Color.White),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.White,
                        unfocusedBorderColor = Color.Black,
                        errorBorderColor = Color.Red,
                        cursorColor = Color.White,
                        focusedLabelColor = Color.White,
                        unfocusedLabelColor = Color.LightGray,
                        containerColor = Color.White.copy(alpha = 0.15f)
                    )
                )
                tamañoError?.let {
                    Text(
                        text = it,
                        color = Color.Red,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }

                OutlinedTextField(
                    value = edad,
                    onValueChange = {
                        edad = it
                        edadError = null
                    },
                    label = { Text("Edad (ej: '2 años', '6 meses')") },
                    singleLine = true,
                    isError = edadError != null,
                    modifier = Modifier
                        .widthIn(max = 300.dp)
                        .padding(vertical = 6.dp),
                    textStyle = TextStyle(color = Color.White),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.White,
                        unfocusedBorderColor = Color.Black,
                        errorBorderColor = Color.Red,
                        cursorColor = Color.White,
                        focusedLabelColor = Color.White,
                        unfocusedLabelColor = Color.LightGray,
                        containerColor = Color.White.copy(alpha = 0.15f)
                    )
                )
                edadError?.let {
                    Text(
                        text = it,
                        color = Color.Red,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }

                // Campo Foto URL
                OutlinedTextField(
                    value = fotoUrl,
                    onValueChange = {
                        fotoUrl = it
                        fotoUrlError = null  // Limpiar error al modificar
                    },
                    label = { Text("Foto URL") },
                    singleLine = true,
                    isError = fotoUrlError != null,
                    modifier = Modifier
                        .widthIn(max = 300.dp)
                        .padding(vertical = 6.dp),
                    textStyle = TextStyle(color = Color.White),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.White,
                        unfocusedBorderColor = Color.Black,
                        errorBorderColor = Color.Red,
                        cursorColor = Color.White,
                        focusedLabelColor = Color.White,
                        unfocusedLabelColor = Color.LightGray,
                        containerColor = Color.White.copy(alpha = 0.15f)
                    )
                )
                fotoUrlError?.let {
                    Text(
                        text = it,
                        color = Color.Red,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                if (showErrorMessage) {
                    Text(
                        text = "Por favor corrige los errores en el formulario",
                        color = Color.Red,
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }

                Button(
                    onClick = {
                        if (validateAllFields()) {
                            mascotaList.add(Mascota(nombre, raza, tamaño, edad, fotoUrl))
                            navController.navigate("ScreenB")
                        } else {
                            showErrorMessage = true
                        }
                    },
                    modifier = Modifier
                        .width(200.dp)
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF000000),
                        contentColor = Color.White
                    )
                ) {
                    Text("Registrar", fontSize = 18.sp)
                }

                Spacer(modifier = Modifier.height(12.dp))

                TextButton(
                    onClick = { navController.navigate("ScreenB") },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Ver Mascotas", fontSize = 16.sp, color = Color.White)
                }
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

    val colors = listOf(
        Color(0xFF4686E3),
        Color(0xFF1976D2),
        Color(0xFF42A5F5),
        Color(0xFF64B5F6),
        Color(0xFF90CAF9)
    ).shuffled()

    Canvas(modifier = Modifier
        .fillMaxSize()
        .background(
            Brush.verticalGradient(
                colors = listOf(colors[0], colors[1], colors[2]),
                startY = 0f,
                endY = 2000f
            )
        )
    ) {
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
