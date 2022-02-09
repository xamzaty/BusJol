package kz.busjol.repository

import com.google.gson.Gson
import kz.busjol.error.ApiException
import org.koin.java.KoinJavaComponent.inject
import retrofit2.Response

abstract class NetworkRepository {

    protected val gson by inject(Gson::class.java)

    protected suspend fun <T> request(block: suspend () -> Response<T>): T {
        return requestNullable(block)!!
    }

    //Nullable request for 204 - No content
    protected suspend fun <T> requestNullable(block: suspend () -> Response<T>): T? {
        val response: Response<T> = block()
        return if (response.isSuccessful) {
            response.body()
        } else {
            val requestUrl = response.raw()?.request?.url?.toUri()?.toString()
            val errorBody = response.errorBody()?.string()
            when {
                response.code() >= 500 -> throw Exception(requestUrl)
                else -> {
                    val tempException = gson.fromJson(errorBody, ApiException::class.java)
                    throw ApiException(tempException, requestUrl)
                }
            }
        }
    }
}