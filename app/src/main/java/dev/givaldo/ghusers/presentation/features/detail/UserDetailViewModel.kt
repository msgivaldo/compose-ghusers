package dev.givaldo.ghusers.presentation.features.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import dev.givaldo.ghusers.data.repository.OfflineFirstUserRepository
import dev.givaldo.ghusers.data.repository.UserRepository
import dev.givaldo.ghusers.domain.model.GitHubUserDetailed
import dev.givaldo.ghusers.presentation.navigation.UsernameArgs
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class UserDetailViewModel(
    handle: SavedStateHandle,
    private val repository: UserRepository
) : ViewModel() {

    private val args = UsernameArgs(handle)

    val uiState = repository.fetchUserDetail(args.username)
        .map {
            UserDetailUiState.Success(it) as UserDetailUiState
        }
        .catch {
            emit(UserDetailUiState.Error(it))
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = UserDetailUiState.Loading
        )

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val repository: UserRepository = OfflineFirstUserRepository()
                UserDetailViewModel(
                    handle = createSavedStateHandle(),
                    repository = repository
                )
            }
        }
    }
}

sealed interface UserDetailUiState {
    data object Loading : UserDetailUiState
    data class Success(val user: GitHubUserDetailed) : UserDetailUiState
    data class Error(val throwable: Throwable) : UserDetailUiState
}