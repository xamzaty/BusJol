package kz.busjol.error


class ServerErrorApiModel(
    val code: ErrorCode,
    val description: String,
    val timestamp: String,
    val data: Map<String, Any>?
)

enum class ErrorCode{
    not_enough_money,
    account_blocked,
}