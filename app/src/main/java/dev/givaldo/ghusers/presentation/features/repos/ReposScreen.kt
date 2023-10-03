package dev.givaldo.ghusers.presentation.features.repos

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.givaldo.ghusers.R
import dev.givaldo.ghusers.domain.model.GithubRepo
import dev.givaldo.ghusers.ui.ErrorContent
import dev.givaldo.ghusers.ui.LoadingContent

@Composable
fun ReposScreen(
    viewModel: ReposViewModel = viewModel(factory = ReposViewModel.Factory),
    navigateBack: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()
    ReposContent(
        uiState = uiState,
        onBackClick = navigateBack
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ReposContent(
    modifier: Modifier = Modifier,
    uiState: ReposUiState,
    onBackClick: () -> Unit,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.app_name))
                },
                navigationIcon = {
                    IconButton(
                        modifier = Modifier
                            .padding(8.dp)
                            .background(
                                color = MaterialTheme.colorScheme.primary,
                                shape = MaterialTheme.shapes.small,
                            )
                            .size(36.dp),
                        onClick = onBackClick,
                        content = {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                tint = MaterialTheme.colorScheme.onPrimary,
                                contentDescription = stringResource(id = R.string.navigate_up),
                            )
                        },
                    )
                }
            )
        },
        content = {
            when (uiState) {
                is ReposUiState.Success -> ReposListContent(
                    modifier = Modifier.padding(it),
                    repos = uiState.repos
                )

                is ReposUiState.Loading -> LoadingContent()
                is ReposUiState.Error -> ErrorContent(uiState.throwable)
            }
        }
    )
}

@Composable
fun ReposListContent(
    modifier: Modifier,
    repos: List<GithubRepo>
) {
    LazyColumn(
        modifier = modifier,
        content = {
            items(repos) {
                RepoListItem(
                    item = it
                )
            }
        }
    )
}

@Composable
fun RepoListItem(
    modifier: Modifier = Modifier,
    item: GithubRepo
) {
    var isExpanded by rememberSaveable {
        mutableStateOf(false)
    }

    Column(
        modifier.clickable { isExpanded = !isExpanded }
    ) {

        ListItem(
            modifier = Modifier.fillMaxWidth(),
            headlineContent = {
                Text(text = item.name)
            },
            overlineContent = {
                Text(text = item.language.toString())
            },
            trailingContent = {
                val rotate by animateFloatAsState(
                    targetValue = if (isExpanded) 0f else 180f,
                    label = "rotate-animation"
                )
                Icon(
                    modifier = modifier.rotate(rotate),
                    imageVector = Icons.Default.KeyboardArrowUp,
                    contentDescription = null,
                )
            }
        )

        val expandTransition = remember {
            expandVertically(
                expandFrom = Alignment.Top,
                animationSpec = tween(300)
            ) + fadeIn(
                animationSpec = tween(300)
            )
        }

        val collapseTransition = remember {
            shrinkVertically(
                shrinkTowards = Alignment.Top,
                animationSpec = tween(300)
            ) + fadeOut(
                animationSpec = tween(300)
            )
        }

        AnimatedVisibility(
            visible = isExpanded,
            enter = expandTransition,
            exit = collapseTransition
        ) {
            Text(text = item.toString())
        }
    }
}