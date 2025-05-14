package net.sylviameows.kitti.api.commands

import java.lang.Exception

class Result(val output: Int, val reason: String? = null) {
    fun isError(): Boolean {
        return output == 0;
    }

    companion object {
        fun success(): Result {
            return Result(1)
        }

        fun fail(reason: String?): Result {
            return Result(0, reason)
        }

        fun fail(exception: Exception): Result {
            return Result(0, exception.message)
        }
    }

}