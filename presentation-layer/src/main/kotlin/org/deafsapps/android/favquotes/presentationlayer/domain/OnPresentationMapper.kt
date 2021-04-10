package org.deafsapps.android.favquotes.presentationlayer.domain

import org.deafsapps.android.favquotes.domainlayer.domain.FailureBo

/**
 * Extension function which maps a failure Business Object to a failure Visual Object
 *
 * @return the [FailureVo] type equivalent datum
 */
fun FailureBo.boToVoFailure(): FailureVo =
    when (this) {
        is FailureBo.InputParamsError -> FailureVo.Error(msg = msg)
        is FailureBo.RequestError -> FailureVo.Error(msg = msg)
        is FailureBo.ServerError -> FailureVo.Error(msg = msg)
        FailureBo.NoData -> FailureVo.NoData
        FailureBo.NoConnection -> FailureVo.NoConnection
        FailureBo.Unknown -> FailureVo.Unknown
        is FailureBo.Error -> FailureVo.Error(msg = msg)
        else -> FailureVo.Unknown
    }
