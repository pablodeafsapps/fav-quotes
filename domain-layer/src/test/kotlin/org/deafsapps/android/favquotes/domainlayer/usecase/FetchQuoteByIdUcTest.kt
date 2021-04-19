package org.deafsapps.android.favquotes.domainlayer.usecase

import arrow.core.Either
import arrow.core.right
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.deafsapps.android.favquotes.domainlayer.DomainlayerContract
import org.deafsapps.android.favquotes.domainlayer.domain.FailureBo
import org.deafsapps.android.favquotes.domainlayer.domain.QuoteBo
import org.deafsapps.android.favquotes.domainlayer.org.deafsapps.android.favquotes.domainlayer.usecase.FetchQuoteByIdUc
import org.junit.Assert
import org.junit.Before
import org.junit.Test

private const val DEFAULT_INT_VALUE = 0
private const val DEFAULT_STRING_VALUE = "none"
private const val DEFAULT_BOOLEAN_VALUE = false

@ExperimentalCoroutinesApi
class FetchQuoteByIdUcTest {

    private lateinit var fetchQuoteByIdUc: DomainlayerContract.Presentationlayer.UseCase<Int, QuoteBo>
    private lateinit var mockRepository: DomainlayerContract.Datalayer.QuoteDataRepository<QuoteBo>

    @Before
    fun setUp() {
        mockRepository = mock()
        fetchQuoteByIdUc = FetchQuoteByIdUc(dataRepository = mockRepository)
    }

    @Test
    fun `check that if the input parameters are null, an 'InputParamsError' error is returned`() =
        runBlockingTest {
            // given
            val nullParams: Int? = null
            // when
            val response = fetchQuoteByIdUc.run(params = nullParams)
            // then
            Assert.assertTrue(response.isLeft() && (response as Either.Left<FailureBo>).a is FailureBo.InputParamsError)
        }

    @Test
    fun `check that if params are right, a 'QuoteBo' is returned`() = runBlockingTest {
        // given
        val rightParams = DEFAULT_INT_VALUE
        whenever(mockRepository.fetchQuoteById(id = any())).doReturn(getDummyQuoteBo().right())
        // when
        val response = fetchQuoteByIdUc.run(params = rightParams)
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
