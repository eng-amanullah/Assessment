package com.amanullah.assessment.base.failure

/**
 * Class that represent any kind of managed error that occurs during the application runtime.
 * Here we can define the HTTP Errors, Caching Error, Feature Error and so on.
 */
sealed class Failure {
    data class ActualException(val exception: Throwable) : Failure()

    /**
     * Define all HTTP error here.
     */
    object HTTP {
        data object Forbidden : Failure()
        data object NotFound : Failure()
        data object MethodNotAllowed : Failure()
        data object NetworkConnection : Failure()
        data object UnauthorizedError : Failure()
        data object BadRequest : Failure()
        data object CanNotConnectToTheServer : Failure()
        data object TooManyRequest : Failure()
        data object InternalServerError : Failure()
    }

    object LocalCache {
        data object NotExistInCache : Failure()
        data class FailedToCache(val message: String) : Failure()
    }

    /*
    We can also manage feature specific errors here.
    The main goal is to have a central error channel where all manged errors are defined.
     */
    /*object FeatureAFailure{
        data object FeatureAFailure1 : Failure()
        data object FeatureAFailure2 : Failure()
    }

    object FeatureBFailure{
        data object FeatureBFailure1 : Failure()
        data object FeatureBFailure2 : Failure()
    }*/
}