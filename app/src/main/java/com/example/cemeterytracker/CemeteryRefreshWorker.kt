package com.example.cemeterytracker

import android.content.Context
import androidx.hilt.Assisted
import androidx.hilt.work.WorkerInject
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.cemeterytracker.data.dto.asNetworkCemetery
import com.example.cemeterytracker.data.dto.responses.asCemeterResponses
import com.example.cemeterytracker.data.dto.responses.asDataBaseModel
import com.example.cemeterytracker.data.dto.update.asCemeteryUpdates
import com.example.cemeterytracker.data.repository.Repository
import timber.log.Timber
import java.lang.Exception

class CemeteryRefreshWorker @WorkerInject constructor(
    @Assisted context: Context,
    @Assisted workerParameters: WorkerParameters,
    private val repository: Repository
) : CoroutineWorker(context, workerParameters) {


    /*
        Each cemetery is inserted with isSynced set to false.

        Any cemetery that has graves added to it will be synced becauase it already exist server side and locallly for graves to be added

        An existing cemetery will have a newCem flag set to false but a isSynced flag set to false when a grave is added.

        Worker will

            1. get all cemeteries that have a newCem flag set to true along with its graves then send them to server which inserts to server database.

            2. get all cemeteries whose isSynced flag is false along with its graves. Then send to server to be inserted.

            3. clears databse

            4. network call for all cems then inserts all cems into local db
     */


    companion object{
        const val CEM_WORKER = "CEM_WORKER"
    }

    override suspend fun doWork(): Result {

        try {

            val newCemeteries = repository.getNewCemeteries()

            repository.sendNewCems(newCemeteries.asNetworkCemetery())


            val unSyncedCemeteries = repository.getUnsyncedCems()


            repository.updateCemeteries(unSyncedCemeteries.asCemeteryUpdates())

            repository.clearDb()

            val syncedCemeteries = repository.getNetworkCems()


            repository.insertSyncedCems(syncedCemeteries.asDataBaseModel())



        }catch (e : Exception){
            Timber.i(e.message)
            return Result.retry()
        }

        return Result.success()


    }


}