package dev.givaldo.ghusers.presentation.features.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.givaldo.ghusers.R
import dev.givaldo.ghusers.domain.model.GitHubUserDetailed
import dev.givaldo.ghusers.ui.ErrorContent
import dev.givaldo.ghusers.ui.LoadingContent

@Composable
fun UserDetailScreen(
    modifier: Modifier = Modifier,
    viewModel: UserDetailViewModel = viewModel(factory = UserDetailViewModel.Factory),
    navigateToRepos: (String) -> Unit,
    navigateBack: () -> Unit,
) {

    val uiState by viewModel.uiState.collectAsState()
    UserListContent(
        modifier = modifier,
        uiState = uiState,
        onReposClick = navigateToRepos,
        onBackClick = navigateBack
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun UserListContent(
    modifier: Modifier = Modifier,
    uiState: UserDetailUiState,
    onReposClick: (String) -> Unit,
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
                is UserDetailUiState.Success -> UserDetailContent(
                    modifier = Modifier.padding(it),
                    userDetail = uiState.user,
                    onReposClick = onReposClick,
                )

                is UserDetailUiState.Loading -> LoadingContent()
                is UserDetailUiState.Error -> ErrorContent(uiState.throwable)
            }
        }
    )
}

@Composable
fun UserDetailContent(
    modifier: Modifier,
    userDetail: GitHubUserDetailed,
    onReposClick: (String) -> Unit
) {
    Column(
        modifier = modifier,
    ) {
        LazyColumn(
            modifier = Modifier.weight(1f),
            content = {

                items(userDetail.properties) {
                    ListItem(
                        headlineContent = {
                            Text(text = it.second.toString())
                        },
                        overlineContent = {
                            Text(text = it.first)
                        }
                    )
                }
            }
        )
        Button(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            onClick = {
                onReposClick(userDetail.login)
            }
        ) {
            Text(text = stringResource(id = R.string.repos))
        }
    }
}