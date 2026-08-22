package com.finance.lumora.notifications

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class BudgetAlertWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val budgetAlertCoordinator: BudgetAlertCoordinator
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        return try {
            budgetAlertCoordinator.evaluate()
            Result.success()
        } catch (e: SecurityException) {
            // Permission missing, return failure cleanly
            Result.failure()
        } catch (exception: Exception) {
            exception.printStackTrace()
            if (runAttemptCount < 3) Result.retry() else Result.failure()
        }
    }

    companion object {
        const val WORK_NAME = "lumora_budget_alert_worker"
    }
}

