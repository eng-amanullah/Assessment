package com.amanullah.assessment.base.extensions

import android.util.AndroidRuntimeException
import androidx.activity.ComponentActivity
import androidx.lifecycle.lifecycleScope
import com.amanullah.assessment.base.logger.Logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Extension function to launch a new coroutine scope that is
 * tied to the **activity** and execute the given body under Main Scope.
 *
 * @param block Body to the executed.
 */
fun ComponentActivity.mainScope(block: suspend CoroutineScope.() -> Unit) =
    lifecycleScope.launch { withContext(Dispatchers.Main) { executeBody(block) } }

/**
 * Execute a given coroutine body and manage all exceptions such as
 * [Exception], [RuntimeException] and [AndroidRuntimeException] and
 * print the error log.
 *
 * @param block Body to be executed.
 */
suspend fun CoroutineScope.executeBody(block: suspend CoroutineScope.() -> Unit) {
    try {
        block.invoke(this)
    } catch (e: Exception) {
        Logger.d("ERROR", e.stackTraceToString())
        e.printStackTrace()
    } catch (e: RuntimeException) {
        Logger.d("ERROR", e.stackTraceToString())
        e.printStackTrace()
    } catch (e: AndroidRuntimeException) {
        Logger.d("ERROR", e.stackTraceToString())
        e.printStackTrace()
    }
}