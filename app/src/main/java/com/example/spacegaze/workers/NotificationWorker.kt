package com.example.spacegaze.workers

import android.app.Notification
import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.spacegaze.notifications.showLaunchNotification

private const val TAG = "NotificationWorker"

class NotificationWorker(ctx: Context, params: WorkerParameters) : CoroutineWorker(ctx, params) {

    override suspend fun doWork(): Result {
        val inputString = inputData.getString("name")
        if (inputString != null) {
            showLaunchNotification(applicationContext, inputString, "Launching in 10 minutes!")
        }
        return Result.success()
    }


}