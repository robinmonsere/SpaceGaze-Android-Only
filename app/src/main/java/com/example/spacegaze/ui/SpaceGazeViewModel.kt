package com.example.spacegaze.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spacegaze.data.LaunchLibraryRepository
import com.example.spacegaze.model.Launch
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface SpaceGazeUiState {
    data class NextLaunch(val nextLaunch: Launch) : SpaceGazeUiState
    object Error : SpaceGazeUiState
    object Loading : SpaceGazeUiState
}

class SpaceGazeViewModel(private val launchLibraryRepository: LaunchLibraryRepository) : ViewModel() {
    var spaceGazeUiState: SpaceGazeUiState by mutableStateOf(SpaceGazeUiState.Loading)
        private set

    init {
        getNextLaunch()
    }

    fun getNextLaunch() {
        viewModelScope.launch {
            spaceGazeUiState = try {
                SpaceGazeUiState.NextLaunch(launchLibraryRepository.getNextLaunch())
            } catch (e: IOException) {
                SpaceGazeUiState.Error
            } catch (e: HttpException) {
                SpaceGazeUiState.Error
            }
        }
    }

}
