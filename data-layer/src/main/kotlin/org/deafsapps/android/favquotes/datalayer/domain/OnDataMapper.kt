package org.deafsapps.android.favquotes.datalayer.domain

import org.deafsapps.android.favquotes.domainlayer.domain.FailureBo
import org.deafsapps.android.favquotes.domainlayer.domain.QuoteBo
import org.deafsapps.android.favquotes.domainlayer.domain.QuoteListWrapperBo

private const val DEFAULT_STRING_VALUE = ""
private const val DEFAULT_BOOLEAN_VALUE = false
private const val DEFAULT_INTEGER_VALUE = -1

/**
 *
 */
fun QuoteListWrapperDto.toListWrapperBo() = QuoteListWrapperBo(
    page = page ?: DEFAULT_INTEGER_VALUE,
    isLastPage = isLastPage ?: DEFAULT_BOOLEAN_VALUE,
    quoteList = quoteList?.toBoList() ?: emptyList()
)

/**
 *
 */
fun QotdDto.toQuoteBo(): QuoteBo = with(quote) {
    QuoteBo(
        author = this?.author ?: DEFAULT_STRING_VALUE,
        authorPermalink = this?.authorPermalink ?: DEFAULT_STRING_VALUE,
        body = this?.body ?: DEFAULT_STRING_VALUE,
        dialogue = this?.dialogue ?: DEFAULT_BOOLEAN_VALUE,
        downvotesCount = this?.downvotesCount ?: DEFAULT_INTEGER_VALUE,
        favoritesCount = this?.favoritesCount ?: DEFAULT_INTEGER_VALUE,
        id = this?.id ?: DEFAULT_INTEGER_VALUE,
        isPrivate = this?.isPrivate ?: DEFAULT_BOOLEAN_VALUE,
        tags = this?.tags ?: emptyList(),
        upvotesCount = this?.upvotesCount ?: DEFAULT_INTEGER_VALUE,
        url = this?.url ?: DEFAULT_STRING_VALUE
    )
}

/**
 *
 */
fun List<QuoteDto>.toBoList(): List<QuoteBo> = map { it.toBo() }

/**
 *
 */
fun QuoteDto.toBo() = QuoteBo(
    author = author ?: DEFAULT_STRING_VALUE,
    authorPermalink = authorPermalink ?: DEFAULT_STRING_VALUE,
    body = body ?: DEFAULT_STRING_VALUE,
    dialogue = dialogue ?: DEFAULT_BOOLEAN_VALUE,
    downvotesCount = downvotesCount ?: DEFAULT_INTEGER_VALUE,
    favoritesCount = favoritesCount ?: DEFAULT_INTEGER_VALUE,
    id = id ?: DEFAULT_INTEGER_VALUE,
    isPrivate = isPrivate ?: DEFAULT_BOOLEAN_VALUE,
    tags = tags ?: emptyList(),
    upvotesCount = upvotesCount ?: DEFAULT_INTEGER_VALUE,
    url = url ?: DEFAULT_STRING_VALUE
)

/**
 * Maps a [FailureDto] into a [FailureBo]
 */
fun FailureDto.dtoToBoFailure(): FailureBo = when (this) {
    FailureDto.NoConnection -> FailureBo.NoConnection
    is FailureDto.RequestError -> FailureBo.RequestError(msg = msg ?: DEFAULT_STRING_VALUE)
    FailureDto.LoginError -> FailureBo.ServerError(msg = msg ?: DEFAULT_STRING_VALUE)
    is FailureDto.RegisterError -> FailureBo.ServerError(msg = msg ?: DEFAULT_STRING_VALUE)
    FailureDto.NoData -> FailureBo.NoData
    is FailureDto.Error -> FailureBo.ServerError(msg = msg ?: DEFAULT_STRING_VALUE)
    FailureDto.Unknown -> FailureBo.Unknown
}
