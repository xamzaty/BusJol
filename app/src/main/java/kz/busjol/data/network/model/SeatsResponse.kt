package kz.busjol.data.network.model

class SeatsResponse(
    val id: String,
    val created: String,
    val status: Int,
    val name: String,
    val row: Int,
    val column: Int,
    val seatNumber: String,
    val isEmpty: Boolean
)