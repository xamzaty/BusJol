package kz.busjol.ui.booking

data class Payment(
    val type: PaymentType,
    val url: String,
    var isChosen: Boolean = false
) {
    enum class PaymentType {
        BANK_CARDS, KASPI
    }
}
