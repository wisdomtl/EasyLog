package com.taylor.demo.call_adapter

import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class ResultCallAdapterFactory : CallAdapter.Factory() {
    override fun get(returnType: Type, annotations: Array<out Annotation>, retrofit: Retrofit): CallAdapter<*, *>? {
        if (getRawType(returnType) != Call::class.java || returnType !is ParameterizedType) return null
        val resultType = getParameterUpperBound(0, returnType)
        if (resultType !is ParameterizedType || getRawType(resultType) != Result::class.java) return null
        val responseType = getParameterUpperBound(0, resultType)// the type wrapped by Result
        return ResultCallAdapter<Any>(responseType)
    }
}