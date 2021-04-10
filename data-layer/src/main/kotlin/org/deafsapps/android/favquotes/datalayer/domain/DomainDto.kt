package org.deafsapps.android.favquotes.datalayer.domain

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import okhttp3.ResponseBody
import org.deafsapps.android.favquotes.domainlayer.domain.ErrorMessage

/**
 *
 */
@JsonClass(generateAdapter = true)
data class QotdDto(
    @Json(name = "qotd_date") val qotdDate: String?,
    @Json(name = "quote") val quote: QuoteDto?
)

/**
 *
 */
@JsonClass(generateAdapter = true)
data class QuoteDto(
    @Json(name = "author") val author: String?,
    @Json(name = "author_permalink") val authorPermalink: String?,
    @Json(name = "body") val body: String?,
    @Json(name = "dialogue") val dialogue: Boolean?,
    @Json(name = "downvotes_count") val downvotesCount: Int?,
    @Json(name = "favorites_count") val favoritesCount: Int?,
    @Json(name = "id") val id: Int?,
    @Json(name = "private") val isPrivate: Boolean?,
    @Json(name = "tags") val tags: List<String>?,
    @Json(name = "upvotes_count") val upvotesCount: Int?,
    @Json(name = "url") val url: String?
)

/**
 * A class which models any failure coming from the 'data-layer'
 */
sealed class FailureDto(val msg: String?) {

    companion object {
        private const val DEFAULT_ERROR_CODE = -1
    }

    object NoConnection : FailureDto(msg = ErrorMessage.ERROR_NO_CONNECTION)
    class RequestError(
        val code: Int = DEFAULT_ERROR_CODE,
        msg: String?,
        val errorBody: ResponseBody? = null
    ) : FailureDto(msg = msg)

    object LoginError : FailureDto(msg = ErrorMessage.ERROR_LOGIN_REQUEST)
    class RegisterError(msg: String?) : FailureDto(msg = msg)
    object NoData : FailureDto(msg = ErrorMessage.ERROR_NO_DATA)
    class Error(msg: String?) : FailureDto(msg = msg)
    object Unknown : FailureDto(msg = ErrorMessage.ERROR_UNKNOWN)
}

/**
 * An enum class which represents all the 'FavQs' error data returned by the API
 */
enum class FavqsApiErrorCode(val code: Int, val msg: String) {
    INVALID_REQUEST(10, "Invalid request"),
    PERMISSIOIN_DENIED(11, "Permission denied"),
    USER_SESSION_NOT_FOUND(20, "User session not found"),
    INVALID_LOGIN(21, "Invalid login or password"),
    LOGIN_INACTIVE(22, "Login is not active. Contact support@favqs.com"),
    LOGIN_MISSING(23, "User login or password is missing"),
    PRO_USER_REQUIRED(24, "Pro user required"),
    USER_NOT_FOUND(30, "User not found"),
    USER_SESSION_ALREADY_PRESENT(31, "User session already present"),
    QUOTE_NOT_FOUND(40, "Quote not found"),
    PRIVATE_QUOTES_CANNOT_UNFAV(41, "Private quotes cannot be unfav'd")
}
