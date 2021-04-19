package org.deafsapps.android.favquotes.datalayer.repository

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.doThrow
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.deafsapps.android.favquotes.datalayer.datasource.ConnectivityDataSource
import org.deafsapps.android.favquotes.datalayer.datasource.QuotesDataSource
import org.deafsapps.android.favquotes.domainlayer.DomainlayerContract
import org.deafsapps.android.favquotes.domainlayer.domain.FailureBo
import org.deafsapps.android.favquotes.domainlayer.domain.QuoteBo
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.lang.IllegalStateException

private const val DEFAULT_INT_VALUE = 0
private const val DEFAULT_STRING_VALUE = "none"
private const val DEFAULT_BOOLEAN_VALUE = false

@ExperimentalCoroutinesApi
class RepositoryTest {

    private lateinit var mockConnectivityDataSource: ConnectivityDataSource
    private lateinit var mockQuoteDataSource: QuotesDataSource
    private lateinit var repository: DomainlayerContract.Datalayer.QuoteDataRepository<QuoteBo>

    @Before
    fun setUp() {
        mockConnectivityDataSource = mock()
        mockQuoteDataSource = mock()

        repository = Repository.apply {
            connectivityDataSource = mockConnectivityDataSource
            quotesDataSource = mockQuoteDataSource
        }
    }

    // "Quote Of The Day"
    @Test
    fun `When there's no connection, a 'NoConnection' error is returned`() = runBlockingTest {
        // given
        whenever(mockConnectivityDataSource.checkNetworkConnectionAvailability()).doReturn(false)
        // when
        val response = repository.fetchRandomQuote()
        // then
        Assert.assertTrue(response.isLeft() && (response as Either.Left<FailureBo>).a is FailureBo.NoConnection)
    }

    @Test
    fun `When there's connection, but the data-source fails, a 'NoData' error is returned`() =
        runBlockingTest {
            // given
            whenever(mockConnectivityDataSource.checkNetworkConnectionAvailability()).doReturn(true)
            whenever(mockQuoteDataSource.fetchRandomQuote()).doReturn(FailureBo.NoData.left())
            // when
            val response = repository.fetchRandomQuote()
            // then
            Assert.assertTrue(response.isLeft() && (response as Either.Left<FailureBo>).a is FailureBo.NoData)
        }

    @Test
    fun `When there's connection, but the data-source triggers an exception, an 'Unknown' error is returned`() =
        runBlockingTest {
            // given
            whenever(mockConnectivityDataSource.checkNetworkConnectionAvailability()).doReturn(true)
            whenever(mockQuoteDataSource.fetchRandomQuote()).doThrow(IllegalStateException())
            // when
            val response = repository.fetchRandomQuote()
            // then
            Assert.assertTrue(response.isLeft() && (response as Either.Left<FailureBo>).a is FailureBo.Unknown)
        }

    @Test
    fun `When there's connection, and the data-source succeeds, a list of data is returned`() =
        runBlockingTest {
            // given
            whenever(mockConnectivityDataSource.checkNetworkConnectionAvailability()).doReturn(true)
            whenever(mockQuoteDataSource.fetchRandomQuote()).doReturn(getDummyQuoteBo().right())
            // when
            val response = repository.fetchRandomQuote()
            // then
            Assert.assertTrue(response.isRight() && response as? Either.Right<QuoteBo> != null)
        }

    private fun getDummyQuoteBo() = QuoteBo(
        author = DEFAULT_STRING_VALUE,
        authorPermalink = DEFAULT_STRING_VALUE,
        body = DEFAULT_STRING_VALUE,
        dialogue = DEFAULT_BOOLEAN_VALUE,
        downvotesCount = DEFAULT_INT_VALUE,
        favoritesCount = DEFAULT_INT_VALUE,
        id = DEFAULT_INT_VALUE,
        isPrivate = DEFAULT_BOOLEAN_VALUE,
        tags = emptyList(),
        upvotesCount = DEFAULT_INT_VALUE,
        url = DEFAULT_STRING_VALUE
    )

}
