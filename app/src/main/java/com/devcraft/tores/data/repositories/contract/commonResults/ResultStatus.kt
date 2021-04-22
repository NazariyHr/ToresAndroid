package com.devcraft.tores.data.repositories.contract.commonResults

class ResultStatus(val status: StateList, val error: Error? = null) {
    enum class StateList {
        SUCCESS,
        FAILURE
    }

    companion object {
        fun success(): ResultStatus {
            return ResultStatus(StateList.SUCCESS)
        }

        fun failure(error: Error): ResultStatus {
            return ResultStatus(StateList.FAILURE, error)
        }

        fun failure(throwable: Throwable): ResultStatus {
            return ResultStatus(StateList.FAILURE, Error(throwable))
        }

        fun failure(errorString: String): ResultStatus {
            return ResultStatus(StateList.FAILURE, Error(errorString))
        }

        fun failure(): ResultStatus {
            return ResultStatus(StateList.FAILURE, Error("Result is failure"))
        }
    }
}