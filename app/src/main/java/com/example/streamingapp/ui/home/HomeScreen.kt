package com.example.streamingapp.ui.home

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Scale
import com.example.streamingapp.R
import com.example.streamingapp.data.model.Sound
import com.example.streamingapp.ui.theme.Roboto
import com.example.streamingapp.ui.theme.StreamingAppTheme

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val paginatedSounds = homeViewModel.paginatedSounds.collectAsLazyPagingItems()
    val uiState by homeViewModel.uiState.collectAsState()

    val navigateToDetail = { _: Int -> }

    StreamingAppTheme {
        HomeScreenContent(
            uiState.query,
            homeViewModel::updateQuery,
            paginatedSounds,
            homeViewModel::searchAllSounds,
            navigateToDetail
        )
    }
}

@Composable
fun HomeScreenContent(
    query: String,
    updateQuery: (String) -> Unit,
    paginatedSounds: LazyPagingItems<Sound>?,
    searchAllSounds: (String) -> Unit,
    navigateToDetail: (Int) -> Unit
) {
    Scaffold {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            SearchSoundBar(searchAllSounds, query, updateQuery)
            paginatedSounds?.let {
                if (paginatedSounds.itemCount > 0) {
                    SoundsList(
                        paginatedSounds,
                        navigateToDetail
                    )
                } else {
                    Message(stringResource(R.string.welcome_message))
                }
            }
            if (paginatedSounds == null) {
                Message(stringResource(R.string.welcome_message))
            }
        }
    }
}

@Composable
fun SoundsList(paginatedSounds: LazyPagingItems<Sound>, navigateToDetail: (Int) -> Unit) {
    Box {
        val isLandscape =
            LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE
        if (!isLandscape) {
            LazyColumn(contentPadding = PaddingValues(top = 16.dp)) {
                items(count = paginatedSounds.itemCount, key = { index ->
                    val sound = paginatedSounds[index]
                    sound?.id ?: ""
                }) { index ->
                    val sound = paginatedSounds[index] ?: return@items
                    ListItem(
                        sound,
                        navigateToDetail
                    )
                }
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(4),
                contentPadding = PaddingValues(
                    top = 16.dp,
                    bottom = 12.dp
                ),
                content = {
                    items(count = paginatedSounds.itemCount, key = { index ->
                        val sound = paginatedSounds[index]
                        sound?.id ?: ""
                    }) { index ->
                        val sound = paginatedSounds[index] ?: return@items
                        SoundCard(
                            sound,
                            navigateToDetail
                        )
                    }
                }
            )
        }
    }
}

@Composable
fun SoundCard(sound: Sound, navigateToDetail: (Int) -> Unit) {
    Card(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()
            .height(180.dp)
            .clickable {
                navigateToDetail(sound.id)
            },
        shape = MaterialTheme.shapes.small
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(9.dp),
            horizontalAlignment = Alignment.Start
        ) {

            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(stringResource(id = R.string.image_place_holder))
                    .crossfade(true)
                    .scale(Scale.FILL)
                    .build(),
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .height(120.dp)
                    .fillMaxWidth()
                    .clip(MaterialTheme.shapes.small),
                contentScale = ContentScale.Crop,
                contentDescription = null
            )
            Column(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    text = sound.name,
                    fontWeight = FontWeight.Light,
                    fontFamily = Roboto,
                    style = MaterialTheme.typography.bodySmall,
                )
            }
        }
    }
}

@Composable
fun ListItem(
    sound: Sound,
    navigateToDetail: (Int) -> Unit
) {
    Surface(
        color = MaterialTheme.colorScheme.onPrimary,
        modifier = Modifier.padding(vertical = 0.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            val lineColor = MaterialTheme.colorScheme.inversePrimary
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .drawBehind {
                        drawLine(
                            color = lineColor,
                            start = Offset(0f, size.height),
                            end = Offset(size.width, size.height),
                            strokeWidth = 2.dp.toPx()
                        )
                    }
                    .clickable {
                        navigateToDetail(sound.id)
                    }
            ) {
                AsyncImage(
                    model = stringResource(R.string.image_place_holder),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(start = 16.dp, top = 6.dp, bottom = 6.dp)
                        .height(80.dp)
                        .width(80.dp)
                        .clip(MaterialTheme.shapes.medium),
                    contentScale = ContentScale.Crop
                )

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 16.dp, end = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        text = sound.name,
                        fontFamily = Roboto,
                        style = MaterialTheme.typography.bodyMedium,
                    )

                    Text(
                        text = sound.username,
                        fontFamily = Roboto,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Light
                    )
                }
            }
        }
    }
}

@Composable
fun Message(text: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = text,
            fontFamily = Roboto,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Light
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchSoundBar(
    searchAllSounds: (String) -> Unit,
    query: String,
    updateQuery: (String) -> Unit
) {
    var active by remember { mutableStateOf(false) }

    SearchBar(
        modifier = Modifier.fillMaxWidth(),
        query = query,
        onQueryChange = {
            updateQuery(it)
        },
        onSearch = {
            if (it.isNotEmpty()) {
                searchAllSounds(it)
                active = false
            }
        },
        active = active,
        onActiveChange = {
            active = it
        },
        placeholder = {
            Text(
                text = stringResource(R.string.search_place_holder),
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Light
            )
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = stringResource(R.string.search_content_description)
            )
        },
        trailingIcon = {
            if (active) {
                val emptyText = ""
                Icon(
                    modifier = Modifier.clickable {
                        if (query.isNotEmpty()) {
                            updateQuery(emptyText)
                        } else {
                            active = false
                        }
                    },
                    imageVector = Icons.Default.Close,
                    contentDescription = stringResource(R.string.close_icon_content_description)
                )
            }
        }
    ) {}
}


@Preview
@Composable
fun HomeScreenPreview() {
    StreamingAppTheme {
        HomeScreenContent("", {}, null, {}, {})
    }
}
