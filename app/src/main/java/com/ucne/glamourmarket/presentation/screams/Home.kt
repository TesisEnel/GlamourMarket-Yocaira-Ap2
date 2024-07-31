package com.ucne.glamourmarket.ui.theme.screams

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ucne.glamourmarket.R
import com.ucne.glamourmarket.presentation.navigation.Destination

@Composable
fun HomeScream(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFD9FC3)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Header(navController)
        Spacer(modifier = Modifier.height(16.dp))
        Content(navController = navController)
    }
}


@Composable
fun Content(navController: NavController) {
    Column(
        modifier = Modifier
            .background(Color(0xFFFFFFFF))
            .fillMaxSize()
            .padding(8.dp)
    ) {
        Text(
            text = "Categoria",
            style = TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                fontFamily = FontFamily.SansSerif
            ),
            modifier = Modifier.padding(16.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        ProductCardHome(imageResource = R.drawable.perfume, categoria = "Perfumes", navController)
        Spacer(modifier = Modifier.height(16.dp))
        ProductCardHome(imageResource = R.drawable.maquillaje, categoria = "Maquillaje", navController)
        Spacer(modifier = Modifier.height(16.dp))
        ProductCardHome(imageResource = R.drawable.accesorios, categoria = "Accesorios", navController)
    }
}

@Composable
fun ProductCardHome(imageResource: Int, categoria: String, navController: NavController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                navController.navigate(Destination.ProductosPorCategoriaScreen.route + "/${categoria}")
            },
        colors = CardDefaults.cardColors(containerColor = Color(0x54ECCAD6)),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(Color.LightGray)
            ) {
                Image(
                    painter = painterResource(id = imageResource),
                    contentDescription = categoria,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}
