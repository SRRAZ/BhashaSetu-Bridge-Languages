package com.example.englishhindi.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Sort
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.englishhindi.R
import com.example.englishhindi.data.model.Word
import com.example.englishhindi.ui.components.WordCard
import com.example.englishhindi.ui.theme.AppLanguage
import com.example.englishhindi.ui.theme.LocalAppLanguage
import com.example.englishhindi.ui.theme.localizedString
import com.example.englishhindi.ui.viewmodel.WordViewModel
import com.google.accompanist.flowlayout.FlowRow
import kotlinx.coroutines.launch

/**
 * Screen displaying a list of vocabulary words
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WordListScreen(
    wordViewModel: WordViewModel = viewModel(),
    onAddWordClick: () -> Unit = {},
    onWordClick: (Word) -> Unit = {},
    onPlayPronunciation: (Word, Boolean) -> Unit = { _, _ -> }
) {
    // UI state
    var searchQuery by rememberSaveable { mutableStateOf("") }
    var showSearch by rememberSaveable { mutableStateOf(false) }
    var selectedCategory by rememberSaveable { mutableStateOf<String?>(null) }
    var showFavoritesOnly by rememberSaveable { mutableStateOf(false) }
    
    // Language state
    val isHindiFirst = LocalAppLanguage.current == AppLanguage.HINDI
    
    // Collect words from ViewModel
    val allWords by wordViewModel.words.collectAsState(initial = emptyList())
    val categories by wordViewModel.categories.collectAsState(initial = emptyList())
    val isLoading by wordViewModel.isLoading.collectAsState(initial = true)
    
    // Filter words based on search query, category, and favorites
    val filteredWords = remember(allWords, searchQuery, selectedCategory, showFavoritesOnly) {
        allWords.filter { word ->
            // Apply category filter
            (selectedCategory == null || word.category == selectedCategory) &&
            // Apply favorites filter
            (!showFavoritesOnly || word.isFavorite) &&
            // Apply search filter
            (searchQuery.isEmpty() || 
             word.englishWord.contains(searchQuery, ignoreCase = true) ||
             word.hindiWord.contains(searchQuery, ignoreCase = true) ||
             word.category.contains(searchQuery, ignoreCase = true))
        }
    }
    
    // Scrolling state
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    
    // FAB visibility based on scroll position
    val showFab by remember {
        derivedStateOf {
            listState.firstVisibleItemIndex == 0
        }
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    if (showSearch) {
                        OutlinedTextField(
                            value = searchQuery,
                            onValueChange = { searchQuery = it },
                            placeholder = { Text(localizedString("Search words...", "शब्द खोजें...")) },
                            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                            trailingIcon = {
                                IconButton(onClick = { searchQuery = "" }) {
                                    Icon(Icons.Default.Clear, contentDescription = "Clear search")
                                }
                            },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )
                    } else {
                        Text(localizedString("Vocabulary", "शब्दावली"))
                    }
                },
                actions = {
                    IconButton(onClick = { showSearch = !showSearch }) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = if (showSearch) "Hide search" else "Show search"
                        )
                    }
                    
                    IconButton(onClick = { showFavoritesOnly = !showFavoritesOnly }) {
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = "Toggle favorites",
                            tint = if (showFavoritesOnly) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.onSurface
                        )
                    }
                    
                    IconButton(onClick = { /* Show sort options */ }) {
                        Icon(
                            imageVector = Icons.Default.Sort,
                            contentDescription = "Sort options"
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            AnimatedVisibility(
                visible = showFab,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                ExtendedFloatingActionButton(
                    onClick = onAddWordClick,
                    icon = { Icon(Icons.Default.Add, contentDescription = null) },
                    text = { Text(localizedString("Add Word", "शब्द जोड़ें")) },
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                // Category chips
                if (categories.isNotEmpty()) {
                    Surface(
                        color = MaterialTheme.colorScheme.background,
                        shadowElevation = 4.dp
                    ) {
                        FlowRow(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 8.dp),
                            mainAxisSpacing = 8.dp,
                            crossAxisSpacing = 8.dp
                        ) {
                            // All categories chip
                            FilterChip(
                                selected = selectedCategory == null,
                                onClick = { selectedCategory = null },
                                label = { Text(localizedString("All", "सभी")) },
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Default.Category,
                                        contentDescription = null,
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                            )
                            
                            // Individual category chips
                            categories.forEach { category ->
                                FilterChip(
                                    selected = selectedCategory == category,
                                    onClick = { 
                                        selectedCategory = if (selectedCategory == category) null else category 
                                    },
                                    label = { Text(category) }
                                )
                            }
                        }
                    }
                }
                
                // Loading state
                if (isLoading) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                } 
                // Empty state
                else if (filteredWords.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.padding(32.dp)
                        ) {
                            if (searchQuery.isNotEmpty()) {
                                Text(
                                    text = localizedString(
                                        "No words found for \"$searchQuery\"",
                                        "\"$searchQuery\" के लिए कोई शब्द नहीं मिला"
                                    ),
                                    style = MaterialTheme.typography.bodyLarge,
                                    textAlign = TextAlign.Center
                                )
                            } else if (showFavoritesOnly) {
                                Text(
                                    text = localizedString(
                                        "No favorite words yet. Add words to your favorites!",
                                        "अभी तक कोई पसंदीदा शब्द नहीं। अपने पसंदीदा में शब्द जोड़ें!"
                                    ),
                                    style = MaterialTheme.typography.bodyLarge,
                                    textAlign = TextAlign.Center
                                )
                            } else if (selectedCategory != null) {
                                Text(
                                    text = localizedString(
                                        "No words in the \"$selectedCategory\" category yet",
                                        "\"$selectedCategory\" श्रेणी में अभी तक कोई शब्द नहीं"
                                    ),
                                    style = MaterialTheme.typography.bodyLarge,
                                    textAlign = TextAlign.Center
                                )
                            } else {
                                Text(
                                    text = localizedString(
                                        "No words added yet. Tap the + button to add your first word!",
                                        "अभी तक कोई शब्द नहीं जोड़ा गया। अपना पहला शब्द जोड़ने के लिए + बटन पर टैप करें!"
                                    ),
                                    style = MaterialTheme.typography.bodyLarge,
                                    textAlign = TextAlign.Center
                                )
                            }
                            
                            Spacer(modifier = Modifier.height(16.dp))
                            
                            FloatingActionButton(
                                onClick = onAddWordClick,
                                containerColor = MaterialTheme.colorScheme.primary,
                                contentColor = MaterialTheme.colorScheme.onPrimary
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = localizedString("Add Word", "शब्द जोड़ें")
                                )
                            }
                        }
                    }
                } 
                // Word list
                else {
                    LazyColumn(
                        state = listState,
                        contentPadding = PaddingValues(bottom = 88.dp)
                    ) {
                        items(
                            items = filteredWords,
                            key = { it.id }
                        ) { word ->
                            WordCard(
                                word = word,
                                onCardClick = { onWordClick(word) },
                                onFavoriteClick = { wordViewModel.toggleFavorite(word.id) },
                                onPlayPronunciationClick = onPlayPronunciation,
                                isHindiFirst = isHindiFirst
                            )
                        }
                    }
                }
            }
        }
    }
}