package com.example.carnet.Items

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
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



@Composable
fun ScreenB(navController: NavController, mascotaList: List<Mascota>) {
    var mascotas by remember { mutableStateOf(mascotaList.toMutableList()) }
    var mascotaSeleccionada by remember { mutableStateOf<Mascota?>(null) }
    var showDialog by remember { mutableStateOf(false) }
    var showDeleteConfirm by remember { mutableStateOf(false) }
    var menuExpandedId by remember { mutableStateOf<Int?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 64.dp, start = 16.dp, end = 16.dp, bottom = 16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(65.dp))

        mascotas.forEach { mascota ->
            Card(
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .width(700.dp)
                    .height(450.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFEAF3F5)),
                elevation = CardDefaults.cardElevation(6.dp)
            ) {
                Box {
                    // Menú superior derecho
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.End
                    ) {
                        IconButton(
                            onClick = {
                                menuExpandedId = if (menuExpandedId == mascota.hashCode()) null else mascota.hashCode()
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.MoreVert,
                                contentDescription = "Menú",
                                tint = Color.Black
                            )
                        }

                        DropdownMenu(
                            expanded = menuExpandedId == mascota.hashCode(),
                            onDismissRequest = { menuExpandedId = null }
                        ) {
                            DropdownMenuItem(
                                text = { Text("Editar") },
                                onClick = {
                                    mascotaSeleccionada = mascota
                                    showDialog = true
                                    menuExpandedId = null
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("Borrar") },
                                onClick = {
                                    mascotaSeleccionada = mascota
                                    showDeleteConfirm = true
                                    menuExpandedId = null
                                }
                            )
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
                                            color = Color(0xFF003366)
                                        )
                                        Text(
                                            text = "CÉDULA DE IDENTIDAD MASCOTA",
                                            fontSize = 14.sp,
                                            fontStyle = FontStyle.Italic,
                                            color = Color.DarkGray
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.height(20.dp))

                                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                                    Text("Nombre: ${mascota.nombre}", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
                                    Text("Raza: ${mascota.raza}", fontSize = 18.sp)
                                    Text("Tamaño: ${mascota.tamaño}", fontSize = 18.sp)
                                    Text("Edad: ${mascota.edad}", fontSize = 18.sp)
                                }

                                Spacer(modifier = Modifier.height(20.dp))

                                Text(
                                    "ID Mascota: 000${mascota.hashCode()}",
                                    fontSize = 14.sp,
                                    color = Color.Gray
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { navController.navigate("ScreenA") },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF25D506)),
            shape = RoundedCornerShape(50),
            modifier = Modifier
                .fillMaxWidth(0.6f)
                .height(50.dp)
        ) {
            Text("Volver", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }
    }

    // Diálogo de edición
    if (showDialog && mascotaSeleccionada != null) {
        EditarMascotaDialog(
            mascota = mascotaSeleccionada!!,
            onDismiss = { showDialog = false },
            onSave = { nombre, raza, tamaño, edad, fotoUrl ->
                val index = mascotas.indexOfFirst { it == mascotaSeleccionada }
                if (index != -1) {
                    mascotas[index] = mascotaSeleccionada!!.copy(
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

    // Diálogo de confirmación de borrado
    if (showDeleteConfirm && mascotaSeleccionada != null) {
        AlertDialog(
            onDismissRequest = { showDeleteConfirm = false },
            title = { Text("Confirmar eliminación") },
            text = { Text("¿Estás seguro de que deseas borrar esta mascota?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        mascotas.remove(mascotaSeleccionada)
                        showDeleteConfirm = false
                        mascotaSeleccionada = null
                    }
                ) {
                    Text("Sí, eliminar", color = Color.Red)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteConfirm = false }) {
                    Text("Cancelar")
                }
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

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Editar Mascota") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(value = nombre, onValueChange = { nombre = it }, label = { Text("Nombre") })
                OutlinedTextField(value = raza, onValueChange = { raza = it }, label = { Text("Raza") })
                OutlinedTextField(value = tamaño, onValueChange = { tamaño = it }, label = { Text("Tamaño") })
                OutlinedTextField(value = edad, onValueChange = { edad = it }, label = { Text("Edad") })
                OutlinedTextField(value = fotoUrl, onValueChange = { fotoUrl = it }, label = { Text("Foto URL") })
            }
        },
        confirmButton = {
            TextButton(onClick = {
                onSave(nombre, raza, tamaño, edad, fotoUrl)
            }) {
                Text("Guardar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}
