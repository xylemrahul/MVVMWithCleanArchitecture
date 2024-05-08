package com.photon.findmyip.data

import android.util.Log
import com.photon.findmyip.api.ApiInterface
import com.photon.findmyip.models.IpLocation
import com.photon.findmyip.util.ErrorUtils
import com.photon.findmyip.models.Result
import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val retrofit : Retrofit) {

    suspend fun fetchIPDetails(): Result<IpLocation> {

        return getResponse(
            request = { retrofit.create(ApiInterface::class.java).getIpJson(
                "Y8wMKgHsRKEahmkDZSh1qjfOPfNXkKvvmLRYL6IX4bKYyIxG7I") },
            defaultErrorMessage = "Error fetching Movie list")
    }

    private suspend fun getResponse(request: suspend () -> Response<IpLocation>, defaultErrorMessage: String):
            Result<IpLocation> {
        return try {
            println("I'm working in thread ${Thread.currentThread().name}")
            val result = request.invoke()
            if (result.isSuccessful) {
                return Result.success(result.body()!!)
            } else {
                val errorResponse = ErrorUtils.parseError(result, retrofit)
                Result.error(errorResponse?.status_message ?: defaultErrorMessage, errorResponse)
            }
        } catch (e: Throwable) {
            Result.error("Unknown Error", null)
        }
    }
}