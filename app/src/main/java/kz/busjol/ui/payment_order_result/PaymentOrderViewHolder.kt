package kz.busjol.ui.payment_order_result

import android.widget.Toast
import androidmads.library.qrgenearator.QRGContents
import androidmads.library.qrgenearator.QRGEncoder
import androidx.recyclerview.widget.RecyclerView
import kz.busjol.databinding.ItemPaymentOrderQrBinding

class PaymentOrderViewHolder(
    private val binding: ItemPaymentOrderQrBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(qr: Qr) {
//        if (qr.url.isEmpty()) {
//            Toast.makeText(itemView.context, "No QR", Toast.LENGTH_SHORT).show()
//        } else {
//            val dimen = 400
//            val qrEncoder = QRGEncoder(qr.url, null, QRGContents.URL_KEY, dimen)
//
//            try {
//                val bitmap = qrEncoder.encodeAsBitmap()
//                binding.qr.setImageBitmap(bitmap)
//            } catch (e: Exception) {
//                e.printStackTrace()
//            }
//        }
    }
}