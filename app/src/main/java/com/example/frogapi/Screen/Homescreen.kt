package com.example.frogapi.Screen

import android.graphics.fonts.FontStyle
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.frogapi.Data.FrogUIState
import com.example.frogapi.Data.FrogViewModel
import com.example.frogapi.Frog
import com.example.frogapi.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(){
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = { FrogTopBar(scrollBehavior = scrollBehavior) }
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ){
            val frogViewModel:FrogViewModel = viewModel(factory = FrogViewModel.Factory)
            display(frogViewModel.frogUIState)
        }
    }

}
@Composable
fun display(uiState: FrogUIState){

   when(uiState){
       is FrogUIState.Error -> {}
       is FrogUIState.Loading -> {
        loadingScreen()
       }
       is FrogUIState.Success -> {showFrogs(uiState.photos)

       }
   }
    
}
@Composable
fun showFrogs(frogs:List<Frog>,modifier:Modifier = Modifier){
    LazyVerticalGrid( columns = GridCells.Fixed(1),
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(4.dp)
    ){
        items(items = frogs, key = {photo -> photo.name}){
                photo -> frogCard(photo,modifier = modifier
            .padding(8.dp)
            .fillMaxWidth())
        }
    }
}

@Composable 
fun frogCard(frog:Frog,modifier: Modifier= Modifier){
    Card (
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ){

        Text(modifier = modifier.padding(horizontal = 4.dp),text = frog.name, style = MaterialTheme.typography.headlineLarge)
        Text(modifier = modifier.padding(horizontal = 4.dp),text = "(${frog.type})", style = MaterialTheme.typography.headlineMedium)
        AsyncImage(model = ImageRequest.Builder(
            context = LocalContext.current
        ).data(frog.img_src).crossfade(true).build(), contentDescription = null,
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.Crop,
            placeholder = painterResource(id = R.drawable.loading_img),
            error = painterResource(id = R.drawable.ic_broken_image))

        Text(modifier = modifier.padding(8.dp),text = frog.description, style = MaterialTheme.typography.bodyLarge)

    }

}
@Composable
fun loadingScreen(){
    Image(painter = painterResource(id = R.drawable.loading_img), contentDescription = "Retreiveing information")
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FrogTopBar(scrollBehavior: TopAppBarScrollBehavior, modifier: Modifier = Modifier) {
    TopAppBar(
        scrollBehavior = scrollBehavior,
        title = {
            Text(
                text = "Amphibians",
                style = MaterialTheme.typography.headlineMedium,
            )
        },
        modifier = modifier.padding(8.dp)
    )
}
