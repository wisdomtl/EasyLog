package com.taylor.demo.api

import com.taylor.demo.protobuf.gen.AdLog.EventBatch
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface TrackApi {
    @POST("v1/track")
    @Headers("Content-Type: application/protobuf")
    suspend fun track(@Body eventBatch: EventBatch)
}