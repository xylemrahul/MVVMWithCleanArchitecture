package com.photon.findmyip.repository

import com.photon.findmyip.data.RemoteDataSource
import com.photon.findmyip.models.IpLocation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import com.photon.findmyip.models.Result
import javax.inject.Inject

class IpRepository @Inject constructor(private val dataSource : RemoteDataSource){

    suspend fun getIpDetails() : Flow<Result<IpLocation>?> {
        return flow {
            emit(Result.loading())
            val result = dataSource.fetchIPDetails()
            emit(result)
        }.flowOn(Dispatchers.IO)
    }
}