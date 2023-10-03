package dev.givaldo.ghusers.presentation.features.repos

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import dev.givaldo.ghusers.data.repository.OfflineFirstUserRepository
import dev.givaldo.ghusers.data.repository.UserRepository
import dev.givaldo.ghusers.domain.model.GithubRepo
import dev.givaldo.ghusers.presentation.navigation.UsernameArgs
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class ReposViewModel(
    handle: SavedStateHandle,
    private val repository: UserRepository
) : ViewModel() {

    private val args = UsernameArgs(handle)

    val uiState = repository.fetchUserRepos(args.username)
        .map {
            ReposUiState.Success(it) as ReposUiState
        }.catch {
            emit(ReposUiState.Error(it))
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = ReposUiState.Loading
        )

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val repository: UserRepository = OfflineFirstUserRepository()
                ReposViewModel(
                    handle = createSavedStateHandle(),
                    repository = repository
                )
            }
        }
    }
}

sealed interface ReposUiState {
    data object Loading : ReposUiState
    data class Success(val repos: List<GithubRepo>) : ReposUiState
    data class Error(val throwable: Throwable) : ReposUiState
}