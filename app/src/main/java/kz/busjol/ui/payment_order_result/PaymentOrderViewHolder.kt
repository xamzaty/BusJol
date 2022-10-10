package kz.busjol.ui.payment_order_result

import androidx.recyclerview.widget.RecyclerView
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.journeyapps.barcodescanner.BarcodeEncoder
import kz.busjol.R
import kz.busjol.databinding.ItemPaymentOrderQrBinding

class PaymentOrderViewHolder(
    private val binding: ItemPaymentOrderQrBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(qr: Qr) {
        try {
            val size = itemView.context.resources.getDimension(R.dimen._130sdp).toInt()
            val matrix = MultiFormatWriter().encode(qr.url, BarcodeFormat.QR_CODE, size, size)
            val bitmap = BarcodeEncoder().createBitmap(matrix)

            binding.qr.setImageBitmap(bitmap)
        } catch (e: WriterException) {
            e.printStackTrace()
        }
    }
}