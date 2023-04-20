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
import com.example.spacegaze.model.SpaceStationList
import com.jakewharton.threetenabp.AndroidThreeTen.init
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

private const val TAG = "ViewModel"
sealed interface SpaceGazeUiState {
    data class UpcomingLaunches(val launchList: Flow<List<Launch>>) : SpaceGazeUiState
    object Success : SpaceGazeUiState
    object Error : SpaceGazeUiState
    object Loading : SpaceGazeUiState
}

class SpaceGazeViewModel(private val launchLibraryRepository: LaunchLibraryRepository, private val launchDao: LaunchDao) : ViewModel() {
    var spaceGazeUiState: SpaceGazeUiState by mutableStateOf(SpaceGazeUiState.Loading)
        private set


    /*
    init {
        getUpcomingLaunches()
    }

     */

    init {
        Log.d(TAG, "Init")
        viewModelScope.launch {
            getUpcomingLaunchesApi()
            getUpcomingLaunchesLocal()
        }
    }


    private fun getUpcomingLaunchesLocal() {
        spaceGazeUiState = SpaceGazeUiState.UpcomingLaunches(launchDao.getUpcomingLaunches())
    }

    private fun saveToDb(launchList: LaunchList) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                launchDao.clearLaunches()
                launchList.launches.forEach { launch ->
                    launchDao.insertLaunch(launch)
                }
            }
        }
        Log.d(TAG, "Updated the Database")
    }

    private fun getUpcomingLaunchesApi() {
        viewModelScope.launch {
            try {
                val launches = launchLibraryRepository.getUpcomingLaunches()
                saveToDb(launches)
            } catch (e: IOException) {
                Log.e(TAG, "There was an IOexception with retrieving the launches")
                SpaceGazeUiState.Error
            } catch (e: HttpException) {
                Log.e(TAG, "There was an HttpException with retrieving the launches")
                SpaceGazeUiState.Error
            }

        }
    }

    fun getPreviousLaunches() {

    }

    fun getLaunchById(id: String) : Flow<List<Launch>> = launchDao.getLaunchById(id)


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
