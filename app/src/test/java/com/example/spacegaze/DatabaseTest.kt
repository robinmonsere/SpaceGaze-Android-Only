package com.example.spacegaze

import android.content.Context
import android.provider.ContactsContract.Data
import androidx.room.Room
import com.example.spacegaze.data.room.LaunchDao
import com.example.spacegaze.data.room.LaunchDatabase
import androidx.test.core.app.ApplicationProvider

import com.example.spacegaze.data.preview.DataSource


import org.junit.Assert.*
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.hasItem
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test

class DatabaseTest {

    private lateinit var launchDao: LaunchDao
    private lateinit var db: LaunchDatabase

    private val launch = DataSource().getMockLaunchData()

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, LaunchDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        launchDao = db.launchDao()
    }

    @After
    fun cleanup() {
        db.close()
    }

    @Test
    fun insertLaunch() = runBlocking {
        launchDao.insertLaunch(launch)

        val result = launchDao.getNextLaunch()
        assertEquals(launch, result)
    }

    @Test
    fun getLaunchById() = runBlocking {
        launchDao.insertLaunch(launch)

        val launch2 = launchDao.getLaunchById("bb327f29-b9c0-4b07-98a5-0493ab351875")

        assertEquals(launch2, launch)
    }
}
