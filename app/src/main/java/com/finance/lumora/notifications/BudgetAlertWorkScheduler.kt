package com.finance.lumora.notifications

import android.content.Context
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton
import dagger.hilt.android.qualifiers.ApplicationContext
/**
 * Schedules the periodic budget-alert evaluation.
 *
 * Responsibilities:
 *
 * - Schedule the budget alert worker.
 * - Prevent duplicate workers.
 * - Allow cancellation when required.
 *
 * The actual budget evaluation is handled by
 * BudgetAlertWorker -> BudgetAlertCoordinator.
 */
@Singleton
class BudgetAlertWorkScheduler @Inject constructor(

    @ApplicationContext
    private val context: Context
) {

    private val workManager =
        WorkManager.getInstance(context)

    /**
     * Schedules periodic budget evaluation.
     * The worker runs approximately every 6 hours.
     * Existing work with the same unique name is kept,
     * preventing duplicate workers from being scheduled.
     */
    fun scheduleBudgetAlertChecks() {

        val constraints =
            Constraints.Builder()
                .setRequiredNetworkType(
                    NetworkType.NOT_REQUIRED
                )
                .build()

        val workRequest =
            PeriodicWorkRequestBuilder<BudgetAlertWorker>(
                6,
                TimeUnit.HOURS
            )
                .setConstraints(constraints)
                .build()

        workManager.enqueueUniquePeriodicWork(

            BudgetAlertWorker.WORK_NAME,

            ExistingPeriodicWorkPolicy.KEEP,

            workRequest
        )
    }

    /**
     * Cancels the periodic budget-alert worker.
     *
     * Useful when budget-alert functionality needs to be
     * completely disabled.
     */
    fun cancelBudgetAlertChecks() {

        workManager.cancelUniqueWork(
            BudgetAlertWorker.WORK_NAME
        )
    }

    companion object {

        /**
         * Convenience method for scheduling budget alerts
         * without manually obtaining the scheduler.
         */
        fun schedule(context: Context) {

            WorkManager
                .getInstance(context)
                .enqueueUniquePeriodicWork(

                    BudgetAlertWorker.WORK_NAME,

                    ExistingPeriodicWorkPolicy.KEEP,

                    PeriodicWorkRequestBuilder<BudgetAlertWorker>(
                        6,
                        TimeUnit.HOURS
                    )
                        .setConstraints(
                            Constraints.Builder()
                                .setRequiredNetworkType(
                                    NetworkType.NOT_REQUIRED
                                )
                                .build()
                        )
                        .build()
                )
        }
    }

    // In BudgetAlertWorkScheduler.kt
    fun triggerImmediateBudgetCheck() {
        val immediateWorkRequest = OneTimeWorkRequestBuilder<BudgetAlertWorker>()
            .build()

        workManager.enqueueUniqueWork(
            "lumora_budget_alert_test",
            ExistingWorkPolicy.REPLACE,
            immediateWorkRequest
        )
    }
}

