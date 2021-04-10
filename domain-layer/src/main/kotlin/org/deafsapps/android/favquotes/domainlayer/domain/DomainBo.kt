package org.deafsapps.android.favquotes.domainlayer.domain

private const val DEFAULT_STRING_RESOURCE = "none"

/**
 * A class which models any failure coming from the 'domain-layer'
 */
sealed class FailureBo(val msg: String = DEFAULT_STRING_RESOURCE) {
    object NoConnection : FailureBo(msg = ErrorMessage.ERROR_NO_CONNECTION)
    class InputParamsError(msg: String) : FailureBo(msg = msg)
    class RequestError(msg: String) : FailureBo(msg = msg)
    class ServerError(msg: String) : FailureBo(msg = msg)
    object NoData : FailureBo(msg = ErrorMessage.ERROR_NO_DATA)
    class Error(msg: String) : FailureBo(msg = msg)
    object Unknown : FailureBo(msg = ErrorMessage.ERROR_UNKNOWN)
}

/**
 * This object gathers all error messages available throughout the app
 */
object ErrorMessage {
    const val ERROR_NO_CONNECTION = "No Connection"
    const val ERROR_PARAMS_CANNOT_BE_EMPTY = "Params cannot be empty"
    const val ERROR_PARAMS_BOTH_EMAIL_PASSWORD_REQUIRED = "Both e-mail and password are required"
    const val ERROR_BAD_REQUEST = "Bad Request"
    const val ERROR_LOGIN_REQUEST = "Login: wrong e-mail or password"
    const val ERROR_LOGIN_RESPONSE = "Login Request Error"
    const val ERROR_REGISTER_REQUEST = "Register Request Error"
    const val ERROR_SOCKET_TIMEOUT_EXCEPTION = "Socket Timeout"
    const val ERROR_NO_DATA = "No Data"
    const val ERROR_SERVER = "Server Error"
    const val ERROR_UNKNOWN = "Unknown Error"
}
