package com.example.projectskripsi_kasir.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.projectskripsi_kasir.Order
import com.example.projectskripsi_kasir.R
import com.example.projectskripsi_kasir.RiwayatDetail
import com.example.projectskripsi_kasir.formatToRupiah

class AdapterRiwayat(private var list: List<Order>, private val context: Context): RecyclerView.Adapter<AdapterRiwayat.JadwalViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JadwalViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.itemrecyleriwayat, parent, false)
        return JadwalViewHolder(view)
    }

    override fun onBindViewHolder(holder: JadwalViewHolder, position: Int) {
        val jadwal = list[position]
        holder.bind(jadwal,context)
    }

    override fun getItemCount(): Int = list.size

    fun updateJadwalList(newJadwalList:  List<Order>) {
        list = newJadwalList
        notifyDataSetChanged()
    }

    class JadwalViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val idOrderTextView: TextView = itemView.findViewById(R.id.id_order2)
        private val tanggalTextView: TextView = itemView.findViewById(R.id.tanggal)
        private val totalTextView: TextView = itemView.findViewById(R.id.total2)
        private val metodeTextView: TextView = itemView.findViewById(R.id.pembayaran2)
        private val detailriwayat: TextView = itemView.findViewById(R.id.detail)

        fun bind(jadwal:  Order,context: Context) {
            idOrderTextView.text = jadwal.orderId
            tanggalTextView.text = jadwal.time
            totalTextView.text = formatToRupiah(jadwal.totalPrice)

            if (jadwal.payment != null) {
                metodeTextView.text = jadwal.payment.paymentMethod
            } else {
                metodeTextView.text = "Unknown"
            }

            detailriwayat.setOnClickListener {
                val intent = Intent(context,RiwayatDetail::class.java).apply {
                    putExtra("order_id", jadwal.orderId)
                    putExtra("time", jadwal.time)
                    putExtra("total_price", jadwal.totalPrice)
                    putExtra("payment_method", jadwal.payment?.paymentMethod)
                    putParcelableArrayListExtra("item", ArrayList(jadwal.items))
                }
                context.startActivity(intent)
            }
        }
    }
}