package org.deafsapps.android.favquotes.datalayer.domain

import org.deafsapps.android.favquotes.domainlayer.domain.FailureBo

private const val DEFAULT_STRING_VALUE = ""

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
