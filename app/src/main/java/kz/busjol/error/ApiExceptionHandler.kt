package kz.busjol.error

import com.google.gson.Gson
import kz.busjol.R
import kz.busjol.resource.ResourceManager
import retrofit2.HttpException
import retrofit2.Response
import java.lang.Exception

class ApiExceptionHandler(
    val gson: Gson,
    private val resourceManager: ResourceManager
) {
    inline fun <reified E> parseHttpExceptionOrNull(throwable: Throwable): E? {

        if (throwable is HttpException) {
            val body = throwable.response()?.errorBody() ?: return null

            return try {
                gson.fromJson(body.string(), E::class.java)
            } catch (e: Exception) {
                null
            }
        }

        return null
    }

    fun <E> handleError(response: Response<E>): ServerErrorApiModel? {
        if (response.isSuccessful) return null

        val errorBody = response.errorBody()
            ?: return null

        return try {
            gson.fromJson(errorBody.charStream(), ServerErrorApiModel::class.java)
        } catch (e: Exception) {
            null
        }
    }

    fun getErrorOrDefault(
        response: Response<*>,
        default: String = getDefaultError()
    ): String {
        return handleError(response)?.description
            ?: default
    }

    fun getValidationErrorOrDefault(
        response: Response<*>
    ): String {
        val apiErrorBody = handleError(response)
        val errorMap = apiErrorBody?.data as? Map<*, *>
        if (!errorMap.isNullOrEmpty()) {
            var message = ""
            errorMap.values.onEachIndexed { index, item ->
                message += if (index != 0) "\n${item.toString()}" else item.toString()
            }

            return message
        }

        return apiErrorBody?.description ?: getDefaultError()
    }

    fun getErrorByCode(
        response: Response<*>,
        default: String = getDefaultError()
    ): String {

        val errorCode = handleError(response)?.code ?: return default

        return when(errorCode) {
            ErrorCode.not_enough_money -> resourceManager.getString(R.string.tariff_error_not_enough_money)
            ErrorCode.account_blocked -> resourceManager.getString(R.string.tariff_error_account_blocked)
        }
    }

    private fun getDefaultError() = resourceManager.getString(R.string.server_error)
}