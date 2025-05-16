package com.example.poputka.core.data.networking

import com.example.poputka.core.domain.Result
import com.google.gson.JsonSyntaxException
import retrofit2.Response
import java.nio.channels.UnresolvedAddressException

inline fun <T> responseToResult(
    execute: () -> Response<T>
): Result<T, DataError.Network> {
    return try {
        val response = execute()
        val body = response.body()
        if (response.isSuccessful && body != null) {
            Result.Success(body)
        } else {
            when (response.code()) {
                408 -> Result.Error(DataError.Network.REQUEST_TIMEOUT)
                429 -> Result.Error(DataError.Network.TOO_MANY_REQUESTS)
                in 500..599 -> Result.Error(DataError.Network.SERVER_ERROR)
                else -> Result.Error(DataError.Network.UNKNOWN)
            }
        }
    } catch (e: UnresolvedAddressException) {
        Result.Error(DataError.Network.NO_INTERNET)
    } catch (e: JsonSyntaxException) {
        Result.Error(DataError.Network.SERIALIZATION)
    } catch (e: Exception) {
        Result.Error(DataError.Network.UNKNOWN)
    }
}

