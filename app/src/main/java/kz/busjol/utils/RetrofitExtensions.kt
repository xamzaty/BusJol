package kz.busjol.utils

import com.google.gson.Gson
import kz.busjol.data.network.model.Response
import kz.busjol.error.ApiException
import retrofit2.HttpException
import java.lang.Exception
import retrofit2.Response as RetrofitResponse

fun <Api : Any, Domain : Any> RetrofitResponse<Api>.map(
    mapSuccess: (Api) -> Response<Domain>,
    mapFailure: ((RetrofitResponse<Api>) -> Response<Domain>)? = null
): Response<Domain> {
    val body = body()

    return if (body != null)
        mapSuccess(body)
    else
        mapFailure?.invoke(this) ?: Response.Failure(toBaseError())
}

fun <Api : Any> RetrofitResponse<Api>.toBaseError(): Exception = when (code()) {
    401 -> ApiException(null)
    in 500..599 -> HttpException(this)
    else -> {
        val body = errorBody()
        if (body != null) {
            try {
                Gson().fromJson(body.string(), ApiException::class.java)
            } catch (e: Exception) {
                ApiException(null)
            }
        } else {
            ApiException(null)
        }
    }
}