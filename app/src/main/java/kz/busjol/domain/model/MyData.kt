package kz.busjol.domain.model

import java.io.Serializable

data class MyData(
    val lastname: String,
    val firstname: String,
    val birthDate: String,
    val phoneNumber: String
): Serializable