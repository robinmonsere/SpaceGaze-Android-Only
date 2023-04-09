package com.example.spacegaze.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.spacegaze.SpaceGazeApplication
import com.example.spacegaze.data.LaunchLibraryRepository
import com.example.spacegaze.model.Launch
import com.example.spacegaze.model.LaunchList
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface SpaceGazeUiState {
    data class NextLaunch(val nextLaunch: LaunchList) : SpaceGazeUiState
    object Error : SpaceGazeUiState
    object Loading : SpaceGazeUiState
}

class SpaceGazeViewModel(private val launchLibraryRepository: LaunchLibraryRepository) : ViewModel() {
    var spaceGazeUiState: SpaceGazeUiState by mutableStateOf(SpaceGazeUiState.Loading)
        private set

    init {
        getUpcomingLaunches()
    }

    fun getUpcomingLaunches() {
        viewModelScope.launch {
            spaceGazeUiState = try {
                SpaceGazeUiState.NextLaunch(launchLibraryRepository.getUpcomingLaunches())
            } catch (e: IOException) {
                SpaceGazeUiState.Error
            } catch (e: HttpException) {
                SpaceGazeUiState.Error
            }
        }
    }

    fun getPreviousLaunches() {

    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as SpaceGazeApplication)
                val launchLibraryRepository = application.container.LaunchLibraryRepository
                SpaceGazeViewModel(launchLibraryRepository = launchLibraryRepository)
            }
        }
    }

}
