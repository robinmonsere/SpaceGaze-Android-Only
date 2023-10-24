package com.example.spacegaze.ui

import android.util.Log
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
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

private const val TAG = "ViewModel"
sealed interface SpaceGazeUiState {
    data class UpcomingLaunches(val launchList: List<Launch>) : SpaceGazeUiState
    object Error : SpaceGazeUiState
    object Loading : SpaceGazeUiState
}

class SpaceGazeViewModel(private val launchLibraryRepository: LaunchLibraryRepository, private val launchDao: LaunchDao) : ViewModel() {
    var spaceGazeUiState: SpaceGazeUiState by mutableStateOf(SpaceGazeUiState.Loading)
        private set

    private val _launchListFlow = MutableStateFlow<List<Launch>>(emptyList())
    val launchListFlow: Flow<List<Launch>> = _launchListFlow.asStateFlow()

    var loaded = 10

    init {
        Log.d("TAG", "logging the init")
        viewModelScope.launch {
            getUpcomingLaunchesApi()
        }
    }




    private fun getLoadThreshold() : Int {
        return loaded;
    }
    fun loadMoreUpcomingLaunches() {
        viewModelScope.launch {
            addLaunchesToLaunchList(launchLibraryRepository.getUpcomingLaunches(loaded + 10, loaded).launches)

            //spaceGazeUiState = spaceGazeUiState.UpcomingLaunches
        }
    }


    fun addLaunchesToLaunchList(newLaunches: List<Launch>) {
        val currentList = _launchListFlow.value.toMutableList()
        currentList.addAll(newLaunches)
        _launchListFlow.value = currentList
    }

    private fun observeLaunchListFlow() {
        viewModelScope.launch {
            launchListFlow.collect { newLaunchList ->
                spaceGazeUiState = SpaceGazeUiState.UpcomingLaunches(newLaunchList)
            }
        }
    }





    private fun getUpcomingLaunchesApi() {
        viewModelScope.launch {
            try {
                addLaunchesToLaunchList(launchLibraryRepository.getUpcomingLaunches(10, 0).launches)
                observeLaunchListFlow()
            } catch (e: IOException) {
                Log.e(TAG, "There was an IOexception with retrieving the launches")
                spaceGazeUiState = SpaceGazeUiState.Error
            } catch (e: HttpException) {
                Log.e(TAG, "There was an HttpException with retrieving the launches")
                spaceGazeUiState =  SpaceGazeUiState.Error
            }
        }
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
