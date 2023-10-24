package com.example.spacegaze.notifications

import android.content.Context
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.example.spacegaze.data.room.LaunchDatabase
import com.example.spacegaze.util.getNotificationMinutes
import com.example.spacegaze.workers.NotificationWorker
import kotlinx.coroutines.*
import java.util.concurrent.TimeUnit

private const val TAG = "NotificationManager"
val viewModelJob = Job()
val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

fun launchNotificationManager(ctx: Context) {
    scheduleNewLaunchNotification(ctx)
}

fun scheduleNewLaunchNotification(ctx: Context) {
    /*
    uiScope.launch {
        withContext(Dispatchers.IO) {
            val launchDao = LaunchDatabase.getDatabase(ctx).launchDao()
            val launch = launchDao.getNextLaunch()
            val minutes = getNotificationMinutes(launch.net)
            if (minutes > 0) {
                val data = Data.Builder().putString("name", launch.name).build()
                val workManager = WorkManager.getInstance(ctx)
                val request = OneTimeWorkRequest.Builder(NotificationWorker::class.java).setInputData(data).setInitialDelay(minutes, TimeUnit.MINUTES).build()
                workManager.enqueue(request)
            }
        }
    }

     */
}


