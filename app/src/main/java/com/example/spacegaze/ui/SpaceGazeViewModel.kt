package com.example.spacegaze.ui

import android.util.Log
import androidx.compose.runtime.collectAsState
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
import com.example.spacegaze.data.room.LaunchDao
import com.example.spacegaze.model.Launch
import com.example.spacegaze.model.LaunchList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

private const val TAG = "ViewModel"
sealed interface SpaceGazeUiState {
    data class NextLaunch(val nextLaunch: Launch) : SpaceGazeUiState
    data class UpcomingLaunches(val launchList: Flow<List<Launch>>) : SpaceGazeUiState
    object Success : SpaceGazeUiState
    object Error : SpaceGazeUiState
    object Loading : SpaceGazeUiState
}

class SpaceGazeViewModel(private val launchLibraryRepository: LaunchLibraryRepository, private val launchDao: LaunchDao) : ViewModel() {
    var spaceGazeUiState: SpaceGazeUiState by mutableStateOf(SpaceGazeUiState.Loading)
        private set

    init {
        try {
            val launches = launchDao.getUpcomingLaunches()
            spaceGazeUiState = SpaceGazeUiState.UpcomingLaunches(launches)
            Log.d(TAG, "Launches are saved in UI state")
            Log.d(TAG, spaceGazeUiState.toString() )
        } catch (e: Exception) {
            SpaceGazeUiState.Error
        }
    }
    fun getUpcomingLaunchesLocal() {
        SpaceGazeUiState.UpcomingLaunches(launchDao.getUpcomingLaunches())
    }

    private fun saveToDb(launchList: LaunchList) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                launchList.launches.forEach { launch ->
                    launchDao.insertLaunch(launch)
                }
            }
        }
    }

    private fun getUpcomingLaunches() {
        viewModelScope.launch {
            try {
                Log.d(TAG, "Getting launches")
                val launches = launchLibraryRepository.getUpcomingLaunches()
                saveToDb(launches)
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
                val launchLibraryRepository = application.container.launchLibraryRepository
                SpaceGazeViewModel(launchLibraryRepository, application.database.launchDao())
            }
        }
    }

}
