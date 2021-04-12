package org.deafsapps.android.favquotes.domainlayer

import arrow.core.Either
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.deafsapps.android.favquotes.domainlayer.domain.FailureBo

/**
 * Gathers all protocols to interact with the 'domain-layer'.
 *
 * Includes several interfaces which define the contracts to comply, both on the 'presentation' and
 * the 'data' side.
 */
interface DomainlayerContract {

    /**
     * Defines the requirements for an entity to become the 'presentation-layer' of the app
     */
    interface Presentationlayer {

        /**
         * Defines baseline use-case using Kotlin Coroutines
         */
        interface UseCase<in T, out S> {
            /**
             * Initiates the use-case using certain arguments related to Kotlin Coroutines
             *
             * @param [scope] the [CoroutineScope] used, which will cancel and terminate this entity when required
             * @param [params] arguments used in the query. It is null by default.
             * @param [onResult] a callback to return a value at some point
             * @param [dispatcherWorker] the dispatcher used to perform the use-case (Dispatchers.IO by default)
             */
            fun invoke(
                scope: CoroutineScope,
                params: T? = null,
                onResult: (Either<FailureBo, S>) -> Unit,
                dispatcherWorker: CoroutineDispatcher = Dispatchers.IO
            ) {
                // Task undertaken in a dispatcher worker and once completed, result sent in the scope thread
                scope.launch { withContext(dispatcherWorker) { onResult(run(params)) } }
            }

            /**
             * Executes the previously defined use-cause
             *
             * @param [params] arguments used in the query
             * @return An [S] value if successful or a [FailureBo] otherwise
             */
            suspend fun run(params: T? = null): Either<FailureBo, S>
        }

        /**
         * Defines baseline use-case using Kotlin Flow
         */
        interface FlowUseCase<in T, out S> {
            /**
             * Initiates the use-case using certain arguments related to Kotlin Coroutines
             *
             * @param [scope] the [CoroutineScope] used, which will cancel and terminate this entity when required
             * @param [params] arguments used in the query. It is null by default.
             * @param [onResult] a callback to return a value at some point
             * @param [dispatcherWorker] the dispatcher used to perform the use-case (Dispatchers.IO by default)
             */
            fun invoke(
                scope: CoroutineScope,
                params: T? = null,
                onResult: (Either<FailureBo, S>) -> Unit,
                dispatcherWorker: CoroutineDispatcher = Dispatchers.IO
            ) {
                scope.launch {
                    withContext(dispatcherWorker) { run(params) }
                        .collect { result -> onResult(result) }
                }
            }

            /**
             * Executes the previously defined use-cause
             *
             * @param [params] arguments used in the query
             * @return A stream of [S] value if successful or [FailureBo] otherwise
             */
            suspend fun run(params: T? = null): Flow<Either<FailureBo, S>>
        }

    }

    /**
     * Defines the requirements for an entity to become the 'data-layer' of the app
     */
    interface Datalayer {

        companion object {
            const val AUTHENTICATION_REPOSITORY_TAG = "authenticationRepository"
            const val QUOTE_DATA_REPOSITORY_TAG = "quoteDataRepository"
        }

        /**
         * Represents the requirements to comply for an authentication repository
         */
        interface AuthenticationRepository<in T, out S> {
            /**
             * Logs-in a user according to certain info parameters
             *
             * @param [params] user info for login
             * @return A [S] data if it is successful or a [FailureBo] otherwise
             */
            suspend fun loginUser(params: T): Either<FailureBo, S>
        }

        /**
         * Represents the requirements to comply for a data repository
         */
        interface QuoteDataRepository<out T> {
            /**
             * Fetches a random quote according to [T] data type
             *
             * @return A [T] data if it is successful or a [FailureBo] otherwise
             */
            suspend fun fetchRandomQuote(): Either<FailureBo, T>

            /**
             * Fetches a list of public quotes according to [T] data type
             *
             * @return A list of [T] data if it is successful or a [FailureBo] otherwise
             */
            suspend fun fetchPublicQuotes(): Either<FailureBo, List<T>>

            /**
             * Marks a specific quote as favourite (fav)
             *
             * @param quoteId the quote identifier
             * @return A list of [T] data if it is successful or a [FailureBo] otherwise
             */
            suspend fun checkQuoteAsFav(quoteId: Int): Either<FailureBo, T>

            /**
             * Marks a specific quote as non-favourite (unfav)
             *
             * @param quoteId the quote identifier
             * @return A list of [T] data if it is successful or a [FailureBo] otherwise
             */
            suspend fun uncheckQuoteAsFav(quoteId: Int): Either<FailureBo, T>

            /**
             * Fetches the list of a user's favourite quotes
             *
             * @return A list of [T] data if it is successful or a [FailureBo] otherwise
             */
            suspend fun fetchFavouriteQuotes(): Either<FailureBo, T>
        }

    }

}
