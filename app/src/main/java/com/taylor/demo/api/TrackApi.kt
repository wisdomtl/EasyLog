package com.taylor.demo.api

import com.zenmen.easylog_su.proto.gen.LogOuterClass
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface TrackApi {
    @POST("v1/track")
    @Headers("Content-Type: application/protobuf")
    suspend fun track(@Body LogBatch: LogOuterClass.LogBatch)
}