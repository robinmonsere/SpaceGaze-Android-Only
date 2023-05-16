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
import com.example.spacegaze.model.Launch
import com.example.spacegaze.model.LaunchList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface PreviousLaunchesUiState {
    data class PreviousLaunches(val launchList: LaunchList) : PreviousLaunchesUiState
    object Error : PreviousLaunchesUiState
    object Loading : PreviousLaunchesUiState
    data class PreviousLaunch(val launch: Launch) : PreviousLaunchesUiState
}

private const val TAG = "PreviousViewModel"

class PreviousLaunchesViewModel(private val launchLibraryRepository: LaunchLibraryRepository) : ViewModel() {
    var previousLaunchesUiState: PreviousLaunchesUiState by mutableStateOf(PreviousLaunchesUiState.Loading)
        private set

    init {
        previousLaunchesUiState = PreviousLaunchesUiState.Loading
        viewModelScope.launch {
            getPreviousLaunchesApi()
        }
    }


    fun getLaunchById(id: String) {
        viewModelScope.launch {
            Log.e(TAG, "getting launch")
            Log.e(TAG, previousLaunchesUiState.toString())
            previousLaunchesUiState = try {
                val launch = launchLibraryRepository.getPreviousLaunchById(id)
                PreviousLaunchesUiState.PreviousLaunch(launch)
            } catch (e: IOException) {
                Log.e(TAG, "There was an IOexception with retrieving the launches")
                PreviousLaunchesUiState.Error
            } catch (e: HttpException) {
                Log.e(TAG, "There was an HttpException with retrieving the launches")
                PreviousLaunchesUiState.Error
            }
        }
    }
/*
    fun getLaunchById(id: String) {
        viewModelScope.launch {
            PreviousLaunchesUiState.PreviousLaunch(launchLibraryRepository.getPreviousLaunchById(id))
        }
    }


 */
    private fun getPreviousLaunchesApi() {
        viewModelScope.launch {
            previousLaunchesUiState = try {
                val launches = launchLibraryRepository.getPreviousLaunches()
                PreviousLaunchesUiState.PreviousLaunches(launches)
            } catch (e: IOException) {
                Log.e(TAG, "There was an IOexception with retrieving the launches")
                PreviousLaunchesUiState.Error
            } catch (e: HttpException) {
                Log.e(TAG, "There was an HttpException with retrieving the launches")
                PreviousLaunchesUiState.Error
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as SpaceGazeApplication)
                val launchLibraryRepository = application.container.launchLibraryRepository
                PreviousLaunchesViewModel(launchLibraryRepository)
            }
        }
    }
}