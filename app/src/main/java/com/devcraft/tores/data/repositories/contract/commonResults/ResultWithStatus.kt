package com.devcraft.tores.data.repositories.contract.commonResults

class ResultWithStatus<T>(val data: T?, val status: ResultStatus) {
    fun changeStatusToErrorIf(
        errorString: String,
        checkStatement: (T?) -> Boolean
    ): ResultWithStatus<T> {
        return if (checkStatement.invoke(data)) {
            ResultWithStatus(data, ResultStatus.failure(errorString))
        } else {
            this
        }
    }
}