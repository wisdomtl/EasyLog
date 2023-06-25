package com.taylor.demo.call_adapter

import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response

/**
 * Turn Call<T> into Call<Result<T>>
 */
class ResultCall<T>(private val call: Call<T>) : Call<Result<T>> {
    override fun clone(): Call<Result<T>> {
        return ResultCall(call.clone())
    }

    override fun execute(): Response<Result<T>> {
        throw UnsupportedOperationException("execute() is not supported in ResultCall")
    }

    override fun isExecuted(): Boolean {
        return call.isExecuted
    }

    override fun cancel() {
        call.cancel()
    }

    override fun isCanceled(): Boolean {
        return call.isCanceled
    }

    override fun request(): Request {
        return call.request()
    }

    override fun timeout(): Timeout {
        return call.timeout()
    }

    override fun enqueue(callback: Callback<Result<T>>) {
        //always invoke onResponse(Response.success) but with different Result
        call.enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) callback.onResponse(this@ResultCall, Response.success(response.code(), Result.success(body)))
                    else callback.onResponse(this@ResultCall, Response.success(Result.failure(NullPointerException())))
                } else {
                    callback.onResponse(this@ResultCall, Response.success(Result.failure(HttpException(response))))
                }
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                callback.onResponse(this@ResultCall, Response.success(Result.failure(RuntimeException(t))))
            }
        })
    }
}