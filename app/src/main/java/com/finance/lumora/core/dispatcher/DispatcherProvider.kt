package com.finance.lumora.core.dispatcher

import kotlinx.coroutines.CoroutineDispatcher

/**
 * Abstraction for Coroutine Dispatchers.
 *
 * Makes testing easier.
 */
interface DispatcherProvider {

    val main: CoroutineDispatcher

    val io: CoroutineDispatcher

    val default: CoroutineDispatcher

    val unconfined: CoroutineDispatcher
}