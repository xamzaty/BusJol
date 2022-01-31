package kz.busjol.error

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
class ApiException(
    code: String?,
    description: String? = null,
    cause: Throwable?,
    data: Map<String, Any>? = null,
    message: String? = null,
) : Exception(message, cause) {
    constructor(apiException: ApiException?, message: String? = null) : this(
        apiException?.code,
        apiException?.description,
        apiException?.cause,
        apiException?.data,
        message,
    )

    @SerializedName("code", alternate = ["error"])
    val code: String? = code

    @SerializedName("description", alternate = ["error_description"])
    val description: String? = description

    var origin: String? = null
    val timestamp: String? = null
    var data: Map<String, Any>? = data

    companion object {
        const val BAD_REQUEST = "bad_request"
        const val INVALID_CODE = "invalid_code"
        const val INVALID_GRANT = "invalid_grant"
        const val DEVICE_TOKEN_INVALID = "device_token_invalid"
        const val AUTHORIZATION_ERROR = "authorization_error"
        const val IDENTICAL_PASSWORDS = "identical_passwords"
        const val FORBIDDEN = "forbidden"
        const val RESOURCE_EXISTS = "resource_exists"
        const val INVALID_PHONE_FORMAT = "invalid_phone_format"
        const val LIMIT_EXEEDED = "limit_exeeded"
        const val TOKEN_EXPIRED = "token_expired"
        const val INVALID_TOKEN = "invalid_token"
        const val INVALID_DATA = "invalid_data"
        const val PHONE_TOKEN_EXPIRED = "phone_token_expired"
        const val HEADER_MISSING = "header_missing"
        const val INVALID_SIGNATURE = "invalid_signature"
        const val RESOURCE_BLOCKED = "resource_blocked"
        const val RESOURCE_NOT_FOUND = "resource_not_found"
        const val BAD_CREDENTIALS = "bad_credentials"
        const val USER_BLOCKED = "user_blocked"
        const val RATE_CHANGED = "rate_changed"
        const val RATE_NOT_FOUND = "rate_not_found"
    }

    fun getErrorText(): String? {
        return when {
            !data.isNullOrEmpty() -> data?.values?.joinToString("\n")
            !description.isNullOrEmpty() -> description
            else -> null
        }
    }

}