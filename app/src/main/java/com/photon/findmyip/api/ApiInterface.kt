package com.photon.findmyip.api

import com.photon.findmyip.models.IpLocation
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("json/")
    suspend fun getIpJson(@Query("key") key: String) : Response<IpLocation>
}