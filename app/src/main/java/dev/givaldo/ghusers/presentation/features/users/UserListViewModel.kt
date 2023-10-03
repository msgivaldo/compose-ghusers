package dev.givaldo.ghusers.presentation.features.users

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import dev.givaldo.ghusers.data.repository.OfflineFirstUserRepository
import dev.givaldo.ghusers.data.repository.UserRepository
import dev.givaldo.ghusers.domain.model.GithubUser
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class UserListViewModel(
    private val repository: UserRepository
) : ViewModel() {

    val usersUiState = repository.fetchUsers()
        .map {
            UserListUiState.Success(it) as UserListUiState
        }.catch {
            emit(UserListUiState.Error(it) as UserListUiState)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = UserListUiState.Loading
        )

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val repository: UserRepository = OfflineFirstUserRepository()
                UserListViewModel(repository = repository)
            }
        }
    }
}

sealed interface UserListUiState {
    data object Loading : UserListUiState
    data class Success(val users: List<GithubUser>) : UserListUiState
    data class Error(val throwable: Throwable) : UserListUiState
}