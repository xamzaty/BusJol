package kz.busjol.data

class UserScope {
    var phone: String? = null
    var iin: String? = null
    var token: String? = null
    var email: String? = null
    var password: String? = null
    var accessToken: String? = null

    fun clear() {
        phone = null
        iin = null
        token = null
        email = null
        password = null
        accessToken = null
    }
}