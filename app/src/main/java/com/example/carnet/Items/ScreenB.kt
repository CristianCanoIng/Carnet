package com.example.carnet.Items

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil3.compose.rememberAsyncImagePainter

@Composable
fun ScreenB(navController: NavController, mascotaList: List<Mascota>) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        mascotaList.forEach { mascota ->
            Card(
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E1E))
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(mascota.fotoUrl),
                        contentDescription = "Foto de la mascota",
                        modifier = Modifier
                            .size(200.dp)
                            .clip(RoundedCornerShape(12.dp))
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text("Nombre: ${mascota.nombre}", color = Color.White, fontSize = 18.sp)
                    Text("Raza: ${mascota.raza}", color = Color.White, fontSize = 16.sp)
                    Text("Tamaño: ${mascota.tamaño}", color = Color.White, fontSize = 16.sp)
                    Text("Edad: ${mascota.edad}", color = Color.White, fontSize = 16.sp)
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = { navController.navigate("ScreenA") },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD50651)),
            modifier = Modifier
                .fillMaxWidth(0.6f)
                .height(50.dp)
        ) {
            Text("Volver", color = Color.White, fontSize = 16.sp)
        }
    }
}

