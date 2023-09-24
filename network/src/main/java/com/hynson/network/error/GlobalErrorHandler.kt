package com.hynson.network.error

import com.hynson.network.apiresponse.NetworkResponseAdapterFactory
import com.hynson.network.error.BusinessException

class GlobalErrorHandler : NetworkResponseAdapterFactory.FailureHandler{
    override fun onFailure(throwable: BusinessException) {
        when (throwable.code) {
            2096 -> {
            }
            3099 -> {
            }
        }
    }
}