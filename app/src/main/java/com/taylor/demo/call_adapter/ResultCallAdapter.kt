package com.taylor.demo.call_adapter

import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.Type

/**
 * Turn Call<R> into Call<Result<R>>, wrap anything returned from okhttp with [Result]
 */
class ResultCallAdapter<R>(private val responseType: Type) : CallAdapter<R, Call<Result<R>>> {
    override fun responseType(): Type {
        return responseType
    }

    override fun adapt(call: Call<R>): Call<Result<R>> {
        return ResultCall(call)
    }
}