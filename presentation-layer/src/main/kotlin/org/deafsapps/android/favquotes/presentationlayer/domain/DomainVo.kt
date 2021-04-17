package org.deafsapps.android.favquotes.presentationlayer.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import org.deafsapps.android.favquotes.domainlayer.domain.ErrorMessage

/**
 *
 */
@Parcelize
data class QuoteVo(
    val author: String,
    val body: String,
    val downvotesCount: Int,
    val favoritesCount: Int,
    val id: Int,
    val tags: List<String>,
    val upvotesCount: Int,
    val url: String
) : Parcelable

/**
 * A class which models any failure coming from the 'domain-layer' module
 */
sealed class FailureVo(var msg: String?) {

    companion object {
        private const val DEFAULT_STRING_RESOURCE = "none"
    }

    /**
     * Allows to get the message associated to an error
     */
    fun getErrorMessage(): String = msg ?: DEFAULT_STRING_RESOURCE

    object Idle : FailureVo(msg = DEFAULT_STRING_RESOURCE)
    object NoConnection : FailureVo(msg = ErrorMessage.ERROR_NO_CONNECTION)
    object NoData : FailureVo(msg = ErrorMessage.ERROR_NO_DATA)
    object Unknown : FailureVo(msg = ErrorMessage.ERROR_UNKNOWN)
    class Error(msg: String) : FailureVo(msg = msg)

}
