package dev.givaldo.ghusers.presentation.features.users

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import dev.givaldo.ghusers.R
import dev.givaldo.ghusers.domain.model.GithubUser
import dev.givaldo.ghusers.ui.ErrorContent
import dev.givaldo.ghusers.ui.LoadingContent
import dev.givaldo.ghusers.ui.theme.ComposegithubreposTheme

@Composable
fun UserListScreen(
    viewModel: UserListViewModel = viewModel(factory = UserListViewModel.Factory),
    navigateToUserDetail: (String) -> Unit,
) {
    val uiState by viewModel.usersUiState.collectAsState()
    UserListContent(uiState = uiState, onItemClick = navigateToUserDetail)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun UserListContent(
    uiState: UserListUiState,
    onItemClick: (String) -> Unit = {},
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.app_name))
                }
            )
        },
        content = {
            when (uiState) {
                is UserListUiState.Success -> UserList(
                    modifier = Modifier.padding(it),
                    users = uiState.users,
                    onItemClick = onItemClick,
                )

                is UserListUiState.Loading -> LoadingContent()
                is UserListUiState.Error -> ErrorContent(uiState.throwable)

            }
        }
    )
}

@Composable
private fun UserList(
    modifier: Modifier = Modifier,
    users: List<GithubUser>,
    onItemClick: (String) -> Unit,
) {
    LazyColumn(
        modifier = modifier,
        content = {
            items(users) {
                UserItem(user = it) {
                    onItemClick(it.login)
                }
            }
        }
    )
}

@Composable
private fun UserItem(
    modifier: Modifier = Modifier,
    user: GithubUser,
    onItemClick: () -> Unit,
) {
    ListItem(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onItemClick() },
        leadingContent = {
            AsyncImage(
                modifier = Modifier
                    .clip(CircleShape)
                    .size(48.dp),
                model = user.avatarUrl,
                contentDescription = null
            )
        },
        overlineContent = {
            Text(user.url)
        },
        headlineContent = {
            Text(text = user.login)
        }
    )
}

@Preview
@Composable
fun UserListPreview() {
    ComposegithubreposTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            UserListContent(uiState = UserListUiState.Loading)
        }
    }
}