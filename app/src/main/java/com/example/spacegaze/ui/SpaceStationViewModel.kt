package com.example.spacegaze.ui

import android.util.Log
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
import com.example.spacegaze.model.LaunchList
import com.example.spacegaze.model.SpaceStation
import com.example.spacegaze.model.SpaceStationList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

private const val TAG = "ViewModel"

sealed interface SpaceStationUiState {
    data class SpaceStations(val ActiveSpaceStationList: Flow<List<SpaceStation>>, val InActiveSpaceStationList: Flow<List<SpaceStation>>) : SpaceStationUiState
    object Loading : SpaceStationUiState
    object Error : SpaceStationUiState
}

class SpaceStationViewModel(private val launchLibraryRepository: LaunchLibraryRepository, private val launchDao: LaunchDao) : ViewModel() {
    var spaceStationUiState: SpaceStationUiState by mutableStateOf(SpaceStationUiState.Loading)
        private set


    init {
        viewModelScope.launch {
            getSpaceStationsApi()
            getSpaceStationsLocal()
        }
    }

    private fun getSpaceStationsApi() {
        viewModelScope.launch {
            try {
                val stations = launchLibraryRepository.getSpaceStations()
                saveToDb(stations)
            } catch (e: IOException) {
                Log.e(TAG, "There was an IOexception with retrieving the stations")
                SpaceStationUiState.Error
            } catch (e: HttpException) {
                Log.e(TAG, "There was an HttpException with retrieving the stations")
                SpaceStationUiState.Error
            }
        }
    }

    fun getStationById(id: Int): Flow<SpaceStation> {
        return launchDao.getStationFromId(id)
    }
    private fun getSpaceStationsLocal() {
       val activeStations = launchDao.getActiveStations()
       val inActiveStations = launchDao.getInActiveStations()
       spaceStationUiState = SpaceStationUiState.SpaceStations(activeStations, inActiveStations)
    }

    private fun saveToDb(spaceStationList: SpaceStationList) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                launchDao.clearStations()
                spaceStationList.spaceStations.forEach { station ->
                    launchDao.insertSpaceStation(station)
                }
            }
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


