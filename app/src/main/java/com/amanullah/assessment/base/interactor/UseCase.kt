package com.amanullah.assessment.base.interactor

import com.amanullah.assessment.base.appresult.AppResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Use-case is design to have a use-case per business logic.
 *
 * It's also help to execute the request under [Dispatchers.IO] Context.
 */
abstract class UseCase<Type, in Params> where Type : Any {
    abstract suspend fun run(params: Params): AppResult<Type>
    suspend operator fun invoke(
        params: Params
    ): AppResult<Type> = withContext(Dispatchers.IO) { run(params) }

    class None
}