package dev.givaldo.ghusers.data.repository

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

internal interface Syncable {
    fun <T> Flow<T>.onStartAsync(
        syncRequest: suspend () -> Unit,
    ) = onStart {
        val scope = CoroutineScope(Dispatchers.Default)
        try {
            scope.launch {
                try {
                    syncRequest()
                } catch (e: Throwable) {
                    scope.cancel()
                    if (this@onStartAsync.firstOrNull() == null) throw e
                }
            }
        } catch (e: Throwable) {
            scope.cancel()
            if (this@onStartAsync.firstOrNull() == null) throw e
        }
    }
}