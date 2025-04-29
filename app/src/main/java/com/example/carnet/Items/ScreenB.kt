package com.example.carnet.Items

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import androidx.compose.material3.DropdownMenu
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.zIndex

@Composable
fun ScreenB(navController: NavController, mascotaList: MutableList<Mascota>) {
    var mascotaSeleccionada by remember { mutableStateOf<Mascota?>(null) }
    var showDialog by remember { mutableStateOf(false) }
    var showDeleteConfirm by remember { mutableStateOf(false) }
    var menuExpandedId by remember { mutableStateOf<Int?>(null) }

    Box(modifier = Modifier.fillMaxSize()) {
        ShootingStarsBackground()
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(bottom = 16.dp)
                .zIndex(2f)
        ) {
            Button(
                onClick = { navController.navigate("ScreenA") },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF000000)),
                shape = RoundedCornerShape(50),
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth(0.3f)
                    .height(50.dp)
            ) {
                Text("Volver", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 24.dp, start = 16.dp, end = 16.dp, bottom = 80.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(mascotaList) { mascota ->
                Spacer(modifier = Modifier.height(104.dp))
                val index = mascotaList.indexOf(mascota)
                val cardColors = listOf(
                    listOf(Color(0xFF1A237E), Color(0xFFA8B0E0), Color(0xFF5C6BC0)),
                    listOf(Color(0xFF0D47A1), Color(0xFF1976D2), Color(0xFF42A5F5)),
                    listOf(Color(0xFF01579B), Color(0xFF039BE5), Color(0xFF29B6F6)),
                    listOf(Color(0xFF006064), Color(0xFF00ACC1), Color(0xFF26C6DA)),
                    listOf(Color(0xFF0277BD), Color(0xFF0288D1), Color(0xFF03A9F4))
                )[index % 5]

                Card(
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(450.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.Transparent
                    ),
                    elevation = CardDefaults.cardElevation(6.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.linearGradient(
                                    colors = cardColors,
                                    start = Offset(0f, 0f),
                                    end = Offset(700f, 450f)
                                )
                            )
                    ) {
                        Box {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp),
                                horizontalArrangement = Arrangement.End
                            ) {
                                Box {
                                    IconButton(
                                        onClick = {
                                            menuExpandedId = if (menuExpandedId == mascota.hashCode()) null else mascota.hashCode()
                                        },
                                        modifier = Modifier
                                            .size(40.dp)
                                            .background(
                                                color = Color.White.copy(alpha = 0.2f),
                                                shape = CircleShape
                                            )
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.MoreVert,
                                            contentDescription = "Menú",
                                            tint = Color.White,
                                            modifier = Modifier.size(24.dp)
                                        )
                                    }
                                    DropdownMenu(
                                        expanded = menuExpandedId == mascota.hashCode(),
                                        onDismissRequest = { menuExpandedId = null },
                                        modifier = Modifier
                                            .background(
                                                Brush.verticalGradient(
                                                    colors = listOf(
                                                        Color(0xFF283593),
                                                        Color(0xFF1A237E)
                                                    )
                                                ),
                                                shape = RoundedCornerShape(12.dp)
                                            )
                                            .width(180.dp)
                                    ) {
                                        DropdownMenuItem(
                                            text = {
                                                Row(
                                                    verticalAlignment = Alignment.CenterVertically,
                                                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                                                ) {
                                                    Icon(
                                                        imageVector = Icons.Default.Edit,
                                                        contentDescription = "Editar",
                                                        tint = Color(0xFF64B5F6)
                                                    )
                                                    Text(
                                                        "Editar",
                                                        color = Color.White,
                                                        fontWeight = FontWeight.Medium
                                                    )
                                                }
                                            },
                                            onClick = {
                                                mascotaSeleccionada = mascota
                                                showDialog = true
                                                menuExpandedId = null
                                            },
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(horizontal = 8.dp)
                                                .clip(RoundedCornerShape(8.dp))
                                                .background(Color.Transparent)
                                        )
                                        Divider(
                                            color = Color.White.copy(alpha = 0.2f),
                                            thickness = 1.dp,
                                            modifier = Modifier.padding(horizontal = 16.dp)
                                        )
                                        DropdownMenuItem(
                                            text = {
                                                Row(
                                                    verticalAlignment = Alignment.CenterVertically,
                                                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                                                ) {
                                                    Icon(
                                                        imageVector = Icons.Default.Delete,
                                                        contentDescription = "Borrar",
                                                        tint = Color(0xFFEF5350)
                                                    )
                                                    Text(
                                                        "Borrar",
                                                        color = Color.White,
                                                        fontWeight = FontWeight.Medium
                                                    )
                                                }
                                            },
                                            onClick = {
                                                mascotaSeleccionada = mascota
                                                showDeleteConfirm = true
                                                menuExpandedId = null
                                            },
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(horizontal = 8.dp)
                                                .clip(RoundedCornerShape(8.dp))
                                                .background(Color.Transparent)
                                        )
                                    }
                                }
                            }

                            Column {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(24.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Image(
                                        painter = rememberAsyncImagePainter(mascota.fotoUrl),
                                        contentDescription = "Foto de la mascota",
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier
                                            .weight(0.8f)
                                            .height(250.dp)
                                            .clip(RoundedCornerShape(12.dp))
                                    )

                                    Spacer(modifier = Modifier.width(24.dp))

                                    Column(
                                        modifier = Modifier
                                            .weight(1.2f)
                                            .fillMaxHeight(),
                                        verticalArrangement = Arrangement.SpaceAround
                                    ) {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Image(
                                                painter = rememberAsyncImagePainter("https://upload.wikimedia.org/wikipedia/commons/thumb/e/ef/Coat_of_arms_of_Colombia.svg/200px-Coat_of_arms_of_Colombia.svg.png"),
                                                contentDescription = "Escudo de Colombia",
                                                modifier = Modifier
                                                    .size(40.dp)
                                                    .padding(end = 8.dp)
                                            )
                                            Column {
                                                Text(
                                                    text = "REPÚBLICA DE COLOMBIA",
                                                    fontSize = 18.sp,
                                                    fontWeight = FontWeight.Bold,
                                                    color = Color.White
                                                )
                                                Text(
                                                    text = "CÉDULA DE IDENTIDAD MASCOTA",
                                                    fontSize = 14.sp,
                                                    fontStyle = FontStyle.Italic,
                                                    color = Color.White.copy(alpha = 0.8f)
                                                )
                                            }
                                        }

                                        Spacer(modifier = Modifier.height(20.dp))

                                        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                                            Text("Nombre: ${mascota.nombre}",
                                                fontSize = 18.sp,
                                                fontWeight = FontWeight.SemiBold,
                                                color = Color.White
                                            )
                                            Text("Raza: ${mascota.raza}",
                                                fontSize = 18.sp,
                                                color = Color.White
                                            )
                                            Text("Tamaño: ${mascota.tamaño}",
                                                fontSize = 18.sp,
                                                color = Color.White
                                            )
                                            Text("Edad: ${mascota.edad}",
                                                fontSize = 18.sp,
                                                color = Color.White
                                            )
                                        }

                                        Spacer(modifier = Modifier.height(20.dp))

                                        Text(
                                            "ID Mascota: 000${mascota.hashCode()}",
                                            fontSize = 14.sp,
                                            color = Color.White.copy(alpha = 0.7f)
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.height(8.dp))
                            }
                        }
                    }
                }
            }
            item {
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
    if (showDeleteConfirm && mascotaSeleccionada != null) {
        AlertDialog(
            onDismissRequest = { showDeleteConfirm = false },
            containerColor = Color(0xFF283593),
            titleContentColor = Color.White,
            textContentColor = Color.White.copy(alpha = 0.9f),
            title = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Warning,
                        contentDescription = "Advertencia",
                        tint = Color(0xFFFFD54F),
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    Text(
                        "Confirmar eliminación",
                        fontWeight = FontWeight.Bold
                    )
                }
            },
            text = {
                Text(
                    "¿Estás seguro de que deseas borrar esta mascota?",
                    fontSize = 16.sp
                )
            },
            shape = RoundedCornerShape(16.dp),
            confirmButton = {
                Button(
                    onClick = {
                        mascotaList.remove(mascotaSeleccionada)
                        showDeleteConfirm = false
                        mascotaSeleccionada = null
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFEF5350)
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Eliminar",
                            tint = Color.White,
                            modifier = Modifier.padding(end = 4.dp)
                        )
                        Text("Sí, eliminar", color = Color.White)
                    }
                }
            },
            dismissButton = {
                OutlinedButton(
                    onClick = { showDeleteConfirm = false },
                    border = BorderStroke(1.dp, Color.White.copy(alpha = 0.5f)),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Cancelar", color = Color.White)
                }
            }
        )
    }

    if (showDialog && mascotaSeleccionada != null) {
        EditarMascotaDialog(
            mascota = mascotaSeleccionada!!,
            onDismiss = { showDialog = false },
            onSave = { nombre, raza, tamaño, edad, fotoUrl ->
                val index = mascotaList.indexOfFirst { it == mascotaSeleccionada }
                if (index != -1) {
                    mascotaList[index] = mascotaSeleccionada!!.copy(
                        nombre = nombre,
                        raza = raza,
                        tamaño = tamaño,
                        edad = edad,
                        fotoUrl = fotoUrl
                    )
                }
                showDialog = false
                mascotaSeleccionada = null
            }
        )
    }
}

@Composable
fun EditarMascotaDialog(
    mascota: Mascota,
    onDismiss: () -> Unit,
    onSave: (String, String, String, String, String) -> Unit
) {
    var nombre by remember { mutableStateOf(mascota.nombre) }
    var raza by remember { mutableStateOf(mascota.raza) }
    var tamaño by remember { mutableStateOf(mascota.tamaño) }
    var edad by remember { mutableStateOf(mascota.edad) }
    var fotoUrl by remember { mutableStateOf(mascota.fotoUrl) }

    var nombreError by remember { mutableStateOf<String?>(null) }
    var razaError by remember { mutableStateOf<String?>(null) }
    var tamañoError by remember { mutableStateOf<String?>(null) }
    var edadError by remember { mutableStateOf<String?>(null) }
    var fotoUrlError by remember { mutableStateOf<String?>(null) }

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

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = Color(0xFF283593),
        titleContentColor = Color.White,
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Editar",
                    tint = Color(0xFF64B5F6),
                    modifier = Modifier.padding(end = 8.dp)
                )
                Text("Editar Mascota", fontWeight = FontWeight.Bold)
            }
        },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                // Campo Nombre
                OutlinedTextField(
                    value = nombre,
                    onValueChange = {
                        nombre = it
                        nombreError = null
                    },
                    label = { Text("Nombre") },
                    isError = nombreError != null,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        cursorColor = Color.White,
                        focusedBorderColor = Color(0xFF64B5F6),
                        unfocusedBorderColor = Color.White.copy(alpha = 0.5f),
                        errorBorderColor = Color.Red,
                        focusedLabelColor = Color(0xFF64B5F6),
                        unfocusedLabelColor = Color.White.copy(alpha = 0.7f)
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
                nombreError?.let {
                    Text(
                        text = it,
                        color = Color.Red,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }

                // Campo Raza
                OutlinedTextField(
                    value = raza,
                    onValueChange = {
                        raza = it
                        razaError = null
                    },
                    label = { Text("Raza") },
                    isError = razaError != null,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        cursorColor = Color.White,
                        focusedBorderColor = Color(0xFF64B5F6),
                        unfocusedBorderColor = Color.White.copy(alpha = 0.5f),
                        errorBorderColor = Color.Red,
                        focusedLabelColor = Color(0xFF64B5F6),
                        unfocusedLabelColor = Color.White.copy(alpha = 0.7f)
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
                razaError?.let {
                    Text(
                        text = it,
                        color = Color.Red,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }

                // Campo Tamaño
                OutlinedTextField(
                    value = tamaño,
                    onValueChange = {
                        tamaño = it
                        tamañoError = null
                    },
                    label = { Text("Tamaño (pequeño, mediano, grande)") },
                    isError = tamañoError != null,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        cursorColor = Color.White,
                        focusedBorderColor = Color(0xFF64B5F6),
                        unfocusedBorderColor = Color.White.copy(alpha = 0.5f),
                        errorBorderColor = Color.Red,
                        focusedLabelColor = Color(0xFF64B5F6),
                        unfocusedLabelColor = Color.White.copy(alpha = 0.7f)
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
                tamañoError?.let {
                    Text(
                        text = it,
                        color = Color.Red,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }

                // Campo Edad
                OutlinedTextField(
                    value = edad,
                    onValueChange = {
                        edad = it
                        edadError = null
                    },
                    label = { Text("Edad (ej: '2 años', '6 meses')") },
                    isError = edadError != null,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        cursorColor = Color.White,
                        focusedBorderColor = Color(0xFF64B5F6),
                        unfocusedBorderColor = Color.White.copy(alpha = 0.5f),
                        errorBorderColor = Color.Red,
                        focusedLabelColor = Color(0xFF64B5F6),
                        unfocusedLabelColor = Color.White.copy(alpha = 0.7f)
                    ),
                    modifier = Modifier.fillMaxWidth()
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
                        fotoUrlError = null
                    },
                    label = { Text("Foto URL") },
                    isError = fotoUrlError != null,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        cursorColor = Color.White,
                        focusedBorderColor = Color(0xFF64B5F6),
                        unfocusedBorderColor = Color.White.copy(alpha = 0.5f),
                        errorBorderColor = Color.Red,
                        focusedLabelColor = Color(0xFF64B5F6),
                        unfocusedLabelColor = Color.White.copy(alpha = 0.7f)
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
                fotoUrlError?.let {
                    Text(
                        text = it,
                        color = Color.Red,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }
        },
        shape = RoundedCornerShape(16.dp),
        confirmButton = {
            Button(
                onClick = {
                    if (validateAllFields()) {
                        onSave(nombre, raza, tamaño, edad, fotoUrl)
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF64B5F6)
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Guardar",
                        tint = Color.White,
                        modifier = Modifier.padding(end = 4.dp)
                    )
                    Text("Guardar", color = Color.White)
                }
            }
        },
        dismissButton = {
            OutlinedButton(
                onClick = onDismiss,
                border = BorderStroke(1.dp, Color.White.copy(alpha = 0.5f)),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Cancelar", color = Color.White)
            }
        }
    )
}