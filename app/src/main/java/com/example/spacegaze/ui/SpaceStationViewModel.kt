package com.example.spacegaze.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.spacegaze.SpaceGazeApplication
import com.example.spacegaze.data.LaunchLibraryRepository
import com.example.spacegaze.data.room.LaunchDao
import com.example.spacegaze.model.Launch
import com.example.spacegaze.model.SpaceStation
import com.example.spacegaze.model.SpaceStationList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

sealed interface SpaceStationUiState {
    data class SpaceStations(val SpaceStationList: SpaceStationList) : SpaceStationUiState
    object Loading : SpaceStationUiState
}

class SpaceStationViewModel(private val launchLibraryRepository: LaunchLibraryRepository, private val launchDao: LaunchDao) : ViewModel() {
    var spaceStationUiState: SpaceStationUiState by mutableStateOf(SpaceStationUiState.Loading)
        private set


    init {
        getSpaceStations()
    }

    fun getSpaceStations() {
       viewModelScope.launch() {
            spaceStationUiState = SpaceStationUiState.SpaceStations(launchLibraryRepository.getSpaceStations())
       }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as SpaceGazeApplication)
                val launchLibraryRepository = application.container.launchLibraryRepository
                SpaceStationViewModel(launchLibraryRepository, application.database.launchDao())
            }
        }
    }
}


