package com.example.cemeterytracker

import android.content.Context
import androidx.hilt.Assisted
import androidx.hilt.work.WorkerInject
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.cemeterytracker.data.repository.Repository

//class CemeteryRefreshWorker @WorkerInject constructor(
//    @Assisted context: Context,
//    @Assisted workerParameters: WorkerParameters,
//    private val repository: Repository
//) : CoroutineWorker(context, workerParameters) {
//
//
//
//    override suspend fun doWork(): Result {
//
//    }
//
//
//}