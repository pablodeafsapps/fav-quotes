package org.deafsapps.android.favquotes.presentationlayer.feature.main.viewmodel

import arrow.core.Either
import arrow.core.right
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.deafsapps.android.favquotes.domainlayer.domain.FailureBo
import org.deafsapps.android.favquotes.domainlayer.domain.QuoteBo
import org.deafsapps.android.favquotes.domainlayer.org.deafsapps.android.favquotes.domainlayer.feature.main.MainDomainLayerBridge
import org.deafsapps.android.favquotes.presentationlayer.base.ScreenState
import org.deafsapps.android.favquotes.presentationlayer.feature.main.view.state.MainState
import org.junit.Assert
import org.junit.Before
import org.junit.Test

private const val DEFAULT_INT_VALUE = 0
private const val DEFAULT_STRING_VALUE = "none"
private const val DEFAULT_BOOLEAN_VALUE = false

/**
 *
 */
@ExperimentalCoroutinesApi
class MainViewModelTest {

    private val viewModel: MainViewModel by lazy { MainViewModel(bridge = mockBridge) }
    private lateinit var mockBridge: MainDomainLayerBridge<QuoteBo>

    /**
     *
     */
    @Before
    fun setUp() {
        mockBridge = mock()
    }

    /**
     *
     */
    @Test
    fun `check that state is 'LoadQuoteList' when quotes are fetched`() {
        // given
        val captor = argumentCaptor<(Either<FailureBo, List<QuoteBo>>) -> Unit>()
        // when
        viewModel.onViewResumed()
        // then
        verify(mockBridge).fetchQuoteList(any(), captor.capture())
        verifyNoMoreInteractions(mockBridge)
        captor.firstValue.invoke(getDummyQuoteBoList().right())

        Assert.assertTrue(getRenderState() is MainState.LoadQuoteList)
    }

    /**
     *
     */
    @Test
    fun `check that state is 'LogInfo' when quotes are queried`() {
        // given
        val captor = argumentCaptor<(Either<FailureBo, Boolean>) -> Unit>()
        // when
        viewModel.onViewCreated()
        // then
        verify(mockBridge).queryQuoteList(any(), captor.capture())
        verifyNoMoreInteractions(mockBridge)
        captor.firstValue.invoke(DEFAULT_BOOLEAN_VALUE.right())

        Assert.assertTrue(getRenderState() is MainState.LogInfo)
    }

    private fun getRenderState() =
        (viewModel.screenState.value as? ScreenState.Render<MainState>)?.renderState

    private fun getDummyQuoteBoList() = listOf(getDummyQuoteBo())

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
